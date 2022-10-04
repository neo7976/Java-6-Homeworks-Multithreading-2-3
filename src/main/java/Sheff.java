import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Sheff implements Runnable {

    ReentrantLock lock = new ReentrantLock();
    Condition condition = lock.newCondition();
    private boolean free = false;

    public boolean isFree() {
        return free;
    }

    public void setFree(boolean free) {
        this.free = free;
    }

    @Override
    public void run() {
        System.out.printf("%s на работе\n", Thread.currentThread().getName());
        lock.lock();
        try {
            while (!free)
                condition.wait();

            System.out.println("Повар готовит заказ");
            Thread.sleep(1000);

            System.out.println("Повар сделал заказ");
            free = true;
            lock.unlock();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
