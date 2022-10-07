public class Visitor implements Runnable {

    Restaurant restaurant;

    public Visitor(Restaurant restaurant) {
        this.restaurant = restaurant;
    }


    @Override
    public void run() {
        System.out.printf("%s зашёл в ресторан\n", Thread.currentThread().getName());
        restaurant.order();
    }
}


