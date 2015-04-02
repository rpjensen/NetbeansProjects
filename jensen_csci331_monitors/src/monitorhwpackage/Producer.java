package monitorhwpackage;

/**
 * @author Ryan Jensen
 * @version April 1, 2015
 * A producer thread that produces random numbers between [0,10] and adds it to the buffer
 */
public class Producer extends Thread {
    private ProdConsMonitor buffer;
    
    /**
     * Initialize a producer with a shared buffer injected
     * @param buffer the shared resource to add to
     */
    public Producer(ProdConsMonitor buffer) {
        super();
        this.buffer = buffer;
    }
    
    @Override
    public void run() {
        for (int i = 0; i < HWMain.NUM_ITEMS; i++) {
            // insert a random number between 0 and 10 inclusive into the monitor
            // You should use Math.random() to get a random number, then adjust the range
            int current = (int)(11 * Math.random());// Floor([0,10.99999999]) = [0,10]
            buffer.insert(current);
        }
    }

}
