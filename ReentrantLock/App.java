import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class App {

    private static int counter = 0;
    private static final Lock lock = new ReentrantLock();

    private static void increment(){

        if(lock.tryLock()) {
            try {
                System.out.println(Thread.currentThread().getName()+" : I have acquired the lock hurray");
                for (int itr = 0; itr < 10000; itr++) counter++;
            } finally {
                lock.unlock();
            }
        }else{
            System.out.println("No chance to acquire lock so i am leaving...");
            System.out.println(Thread.currentThread().getName()+" : signing Off");
        }

    }

    public static void main(String args[]) throws InterruptedException {

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                increment();
            }
        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                increment();
            }
        });

        t1.start();

        t2.start();

        t1.join();
        t2.join();

        System.out.println("Counter : "+counter);

    }
}
