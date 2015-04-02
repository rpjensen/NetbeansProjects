package monitorhwpackage;

/**
 * @author Ryan Jensen
 * @version April 1, 2015
 * A shared buffer that holds 100 produced values in a FILO stack for the consumer
 * to consume from.  Manages synchronization and thread sleeping.
 */
public class ProdConsMonitor {

    private int[] buffer = new int[HWMain.BUFFER_SIZE];
    private int current = -1;
    private int prodBlock = 0;
    private int consBlock = 0;
    
    /**
     * Insert a new integer to the shared buffer.
     * Wake a consumer if the buffer was empty
     * @param value the new value to add
     */
    public synchronized void insert(int value) {
        
        if (current+1 == buffer.length) { // buffer is full; can't insert.
            // note that we are blocking during insert
            prodBlock++;
            goToSleep();
        }
        //critical section code here. insert into the stack and adjust the index
        current++;
        buffer[current] = value;

        if (current == 0) { // we just inserted the only item into a previously empty buffer
            notify(); // wake a sleeping thread that waited from inside this monitor
        }
    }

    /**
     * Remove an integer from the shared buffer.
     * If the buffer was full wake the producer
     * @return the top item on the buffer
     */
    public synchronized int remove() {
        // current == 0 is one thing in the buffer
        if (current == -1) { // buffer is empty -- nothing to remove
            // note that we are blocking during remove
            consBlock++;
            goToSleep();
        }

        // critical section code here. remove item from buffer and adjust the index
        int removed = buffer[current];
        current--;
        // current+1 points to the removed object current+2 will be the count at that index
        if (current+2 == buffer.length) { // we just removed an item from a previously full buffer
            notify(); // wake a sleeping thread that waited from inside this monitor
        }

        // finally, return the item that was taken from the buffer
        return removed;
    }

    // Jacketing for thread wait. DO NOT MODIFY
    private synchronized void goToSleep() {
        try {
            wait();
        } catch (InterruptedException e) {
            // do nothing
        }
    }

    // Make public synchronized getter methods for the number of times insert has blocked,
    // and the number of times remove has blocked.
    /**
     * @return the number of times the producer thread blocked
     */
    public synchronized int getProducerBlockCount() {
        return this.prodBlock;
    }
    
    /**
     * @return the number of times the consumer thread blocked
     */
    public synchronized int getConsumerBlockCount() {
        return this.consBlock;
    }
}
