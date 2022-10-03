import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Restaurant {

    ReentrantLock lock;
    Condition condition;
    private volatile boolean waiter = false;


    public Restaurant() {
        this.lock = new ReentrantLock();
        this.condition = lock.newCondition();
    }

    public void order() {
        lock.lock();
        try {
            System.out.printf("%s зашёл в ресторан\n", Thread.currentThread().getName());
            while (!waiter) {
            condition.wait();
            }
//            condition.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void acceptOrder() {
        lock.lock();
        try {
            System.out.printf("%s вышел на работу\n", Thread.currentThread().getName());
            condition.signal();
        } finally {
            lock.unlock();
        }
    }
}
