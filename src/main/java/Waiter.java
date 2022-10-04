public class Waiter implements Runnable {

    Restaurant restaurant;


    public Waiter(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    @Override
    public void run() {
        try {
            restaurant.acceptOrder();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
