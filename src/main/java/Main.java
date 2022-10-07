public class Main {

    public final static int VALUE_VISITOR = 5; //гости
    public final static int VALUE_WAITER = 3; //официант

    public static void main(String[] args) {

        Restaurant restaurant = new Restaurant();

        for (int i = 1; i <= VALUE_WAITER; i++) {
            Thread waiter = new Thread(new Waiter(restaurant));
            waiter.setName("Официант " + i);
            waiter.start();
        }
        for (int i = 1; i < VALUE_VISITOR; i++) {
            Thread visitor = new Thread(new Visitor(restaurant));
            visitor.setName("Посетитель " + i);
            visitor.start();
        }

        Thread sheff = new Thread(new Sheff(restaurant));
        sheff.setName("Повар");
        sheff.start();
    }
}
