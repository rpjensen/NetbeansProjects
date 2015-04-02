package monitorhwpackage;

/**
 * @author Ryan Jensen
 * @version April 1, 2015
 * Main method to start the producer/consumer thread and print the results
 */
public class HWMain {

    public static final int BUFFER_SIZE = 100; // buffer size
    public static final int NUM_ITEMS = 10000000;
    // The ProdConsMonitor class's object can be a static field in this main class

    public static void main(String[] args) throws InterruptedException {
        // create producer and consumer objects.
        // start their threads, join their threads, and then finally
        // print out how many times the monitor insert blocked
        // and how many times the monitor remove blocked.
        ProdConsMonitor buffer = new ProdConsMonitor();
        Producer prod = new Producer(buffer);
        Consumer cons = new Consumer(buffer);
        prod.start();
        cons.start();
        prod.join();// Wait for producer to complete
        cons.join();// Wait for the consumer to complete
        System.out.printf("The producer blocked %d times\n", buffer.getProducerBlockCount());
        System.out.printf("The consumer blocked %d times\n", buffer.getConsumerBlockCount());
    }

}
