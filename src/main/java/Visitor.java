public class Visitor implements Runnable {

    Restaurant restaurant;
    private int number;


    public Visitor(Restaurant restaurant, int value) {
        this.restaurant = restaurant;
        this.number = value;
    }

    public int getNumber() {
        return number;
    }

    @Override
    public void run() {
        for (int i = 0; i < getNumber(); i++) {
            restaurant.order();
        }
    }
}


