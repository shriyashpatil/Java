public class App {

    public static int counter = 0;

    // we have to make sure this method is executed onl y by the single thread at given time
    public static synchronized void increment(){
        counter++;
    }

    public static void process() throws InterruptedException {

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i = 0; i<100; ++i){
                    increment();
                }
            }
        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i = 0; i<100; ++i){
                    increment();
                }
            }
        });

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        System.out.println("Counter value : "+counter);
    }


    public static void main(String args[]){

        try{
            process();
        }catch(InterruptedException e){

        }

    }

}
