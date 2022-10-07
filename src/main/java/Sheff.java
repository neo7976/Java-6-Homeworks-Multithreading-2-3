import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Sheff implements Runnable {

    Restaurant restaurant;

    public Sheff(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    @Override
    public void run() {
        System.out.printf("%s на работе\n", Thread.currentThread().getName());
        restaurant.cooking();
    }
}
