import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Restaurant {
    private final int TIME_COOKING = 3000;
    private final int TIME_EAT = 3000;

    ReentrantLock lock;
    Condition conditionWaiter;
    Condition conditionVisitor;
    Condition conditionSheff;
    List<String> listOrder;
    List<String> listCookEat;
    List<String> listWaiter;
    private boolean free = true;


    public Restaurant() {
        this.lock = new ReentrantLock(false);
        this.conditionWaiter = lock.newCondition();
        this.conditionSheff = lock.newCondition();
        this.conditionVisitor = lock.newCondition();
        this.listOrder = new LinkedList<>();
        this.listCookEat = new LinkedList<>();
        this.listWaiter = new LinkedList<>();
    }

    public void order() {
        lock.lock();
        try {
            //сделать ожидание свободного официанта в цикле
            while (listWaiter.isEmpty()) {
                conditionVisitor.wait();
            }
            //сигнал, что сделан заказ
            listOrder.add("Блюдо");
            String waiter = listWaiter.remove(0);
            //todo проверка
            System.out.println("Проверка на удаление и запись в переменную:" + waiter);

            conditionWaiter.signal();
            //ждем блюдо
            while (listCookEat.isEmpty())
                conditionVisitor.wait();

            System.out.printf("%s приступил к еде\n", Thread.currentThread().getName());
            Thread.sleep(TIME_EAT);
            listCookEat.remove(0);
            System.out.printf("%s вышел из ресторана\n", Thread.currentThread().getName());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void acceptOrder() throws InterruptedException {
        lock.lock();
        try {
            //вышел на работу и сообщает об этом
            listWaiter.add(Thread.currentThread().getName());
            conditionVisitor.signal();
            //должен уснуть, пока не поступит заказ
            while (listOrder.isEmpty())
                conditionWaiter.wait();
            //todo нужно подумать о возможности использования Map
            System.out.printf("%s принял заказ\n", Thread.currentThread().getName());
            //говорим повару приготовить еду и проверяем свободен ли он
            while (!free)
                conditionWaiter.wait();
            free = false;
            conditionSheff.signal();
            //ждём ответа от повара
            while (listCookEat.isEmpty())
                conditionWaiter.wait();
//            free = false;
//            waiter = true;
            //несём блюдо
            System.out.printf("%s несёт заказ\n", Thread.currentThread().getName());
            conditionVisitor.signal();
        } finally {
            lock.unlock();
        }
    }

    public void cooking() {
        lock.lock();
        try {
            //добавить условия цикла, когда мы ждем
            while (free)
                conditionSheff.wait();

            System.out.println("Повар начинает готовить заказ");
            Thread.sleep(TIME_COOKING);
            listCookEat.add(listOrder.remove(0));
            System.out.println("Повар сделал заказ");
            free = true;
            conditionWaiter.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}
