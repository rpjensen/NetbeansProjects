package monitorhwpackage;

/**
 * @author Ryan Jensen
 * @version April 1, 2015
 * A consumer thread that takes numbers off the buffer and adds it to the total
 */
public class Consumer extends Thread {
    private int sum = 0;
    private ProdConsMonitor buffer;
    
    /**
     * Initialize a consumer with a shared buffer injected
     * @param buffer the shared resource to add to
     */
    public Consumer(ProdConsMonitor buffer) {
        super();
        this.buffer = buffer;
    }
    
    @Override
    public void run() {
        for (int i = 0; i < HWMain.NUM_ITEMS; i++) {
            // remove an item from the buffer using the monitor's remove method
            int current = buffer.remove();
            // then process the item to incoroporate it into your summation
            sum += current;
        }
        // print out the value of the summation of all the consumed items
        System.out.printf("The total sum is %d\n", sum);
    }
}
