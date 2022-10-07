public class Waiter implements Runnable {

    Restaurant restaurant;


    public Waiter(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    @Override
    public void run() {
        System.out.printf("%s вышел на работу\n", Thread.currentThread().getName());
        try {
            restaurant.acceptOrder();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
