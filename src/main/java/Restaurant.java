import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Restaurant {

    ReentrantLock lock;
    Condition conditionWaiter;
    Condition conditionSheff;
    List<String> listOrder;
    List<String> listCookEat;
    private boolean waiter = false;
    private boolean free = true;


    public Restaurant() {
        this.lock = new ReentrantLock(true);
        this.conditionWaiter = lock.newCondition();
        this.conditionSheff = lock.newCondition();
        this.listOrder = new LinkedList<>();
        this.listCookEat = new LinkedList<>();
    }

    public void order() {
        lock.lock();
        try {
            //сделать ожидание свободного официанта в цикле
            while (waiter) {
                conditionWaiter.wait();
            }
            //сигнал, что сделан заказ
            listOrder.add("Блюдо");
            conditionWaiter.signal();
            //ждем блюдо
            while (listCookEat.isEmpty())
                conditionWaiter.wait();

            System.out.printf("%s приступил к еде", Thread.currentThread().getName());
            listCookEat.remove(0);
            Thread.sleep(3000);
            System.out.printf("%s вышел из ресторана", Thread.currentThread().getName());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void acceptOrder() throws InterruptedException {
        lock.lock();
        try {
            Thread.sleep(2000);
            System.out.println("Даем сигнал об выходе на работу");
            //вышел на работу и сообщает об этом
            waiter = true;
            conditionWaiter.signal();
            //должен уснуть, пока не поступит заказ
            while (listOrder.isEmpty())
                conditionWaiter.wait();
            System.out.printf("%s принял заказ", Thread.currentThread().getName());
            //говорим повару приготовить еду
            free = false;
            conditionSheff.signal();
            //ждём ответа от повара
            while (listCookEat.isEmpty())
                conditionSheff.wait();
//            free = false;
//            waiter = true;
            //несём блюдо
            System.out.printf("%s несёт заказ\n", Thread.currentThread().getName());
            conditionWaiter.signal();
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
            Thread.sleep(3000);
            System.out.println("Повар сделал заказ");
            listOrder.remove(0);
            free = true;
            conditionSheff.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}
