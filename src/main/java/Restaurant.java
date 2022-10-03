import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Restaurant {

    ReentrantLock lock;
    Condition condition;

    public void order() {
        lock.lock();
        try {

        } finally {
            lock.unlock();
        }
    }

    public void acceptOrder() {
        lock.lock();
        try {

        } finally {
            lock.unlock();
        }
    }
}
