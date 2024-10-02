package ProducerAndConsumer;


import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Worker{

    private Lock lock = new ReentrantLock();

    private Condition condition = lock.newCondition();

    public void producer() throws InterruptedException {
        lock.lock();
        System.out.println("In producer....");
        condition.await();
        System.out.println("In producer again....");
        lock.unlock();
    }

    public void consumer() throws InterruptedException {
        Thread.sleep(2000);
        lock.lock();
        System.out.println("In consumer ...");
        Thread.sleep(3000);
        condition.signal();
        Thread.sleep(2000);
        System.out.println("Leaving consumer");
        lock.unlock();

    }


}
public class App {

    public static void main(String args[]){

        Worker worker = new Worker();
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    worker.producer();
                }catch(Exception e){

                }
            }
        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    worker.consumer();
                }catch(Exception e){

                }
            }
        });

        t1.start();
        t2.start();

    }

}
