public class Main {

    public final static int VALUE = 6;

    public static void main(String[] args) {

        Restaurant restaurant = new Restaurant();

        for (int i = 0; i < 3; i++) {
            Thread waiter = new Thread(new Waiter(restaurant));
            waiter.start();
            Thread visitor = new Thread(new Visitor(restaurant, VALUE));
            visitor.start();
        }
    }
}