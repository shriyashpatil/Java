import java.util.ArrayList;
import java.util.List;

public class Process {

    List<Integer> list = new ArrayList<>();
    private static final int UPPER_LIMIT = 5;
    private static final int LOWER_LIMIT = 0;

    private final Object lock = new Object();

    int value = 0;

    public void producer() throws InterruptedException{

        synchronized (lock){
            while(true){

                if(list.size()==UPPER_LIMIT){
                    System.out.println("Upper limit found..waiting to consume the data");
                    lock.wait();
                }else{
                    System.out.println("Producing the value : "+value);
                    list.add(value);
                    value+=1;
                    lock.notify();
                }

                Thread.sleep(500);
            }
        }

    }

    public void consumer() throws InterruptedException{

        synchronized (lock){
            while(true){

                if(list.size()==LOWER_LIMIT){
                    System.out.println("Lower limit found..waiting to produce the data");
                    value=0;
                    lock.wait();
                }else{
                    System.out.println("Consuming the value : "+list.remove(list.size()-1));
                    lock.notify();
                }

                Thread.sleep(500);
            }
        }

    }
}

class App{

    public static void main(String args[]){

        Process p = new Process();

        Thread produce = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    p.producer();
                }catch(Exception e){
                    System.out.println(e.getMessage());
                }
            }
        });

        Thread consume = new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    p.consumer();
                }catch(Exception e){
                    System.out.println(e.getMessage());
                }
            }
        });

        produce.start();
        consume.start();

    }

}
