
package jensen_scheduler;

import java.util.Arrays;
import java.util.Random;

/**
 * CSCI 331 Scheduler homework using algorithm LOTTERY
 * @author Ryan Jensen
 * @version April 19, 2015
 */
public class Scheduler {
    /** Max number of processes this scheduler can manage */
    public static final int maxProcessCount = 100000;
    /** Max number of tickets a single process may receive */
    public static final int maxTickets = 15;
    
    private final int[] pid;
    private int nextPidSpot;
    private final int[] tickets;
    private int nextTicketSpot;
    
    /**
     * Create a new Scheduler with no special initialization parameters
     */
    public Scheduler() {
        // Maintain an array of the scheduled processes
        pid = new int[maxProcessCount];
        // Maintain an array of all the tickets for the processes
        tickets = new int[maxProcessCount * maxTickets];
        nextPidSpot = 0;// Points to the next insert spot for the pid array
        nextTicketSpot = 0;// Points to the next insert spot for the ticket array
    }
    
    /**
     * @return the number of processes the scheduler is managing
     */
    public int getN() {
        return nextPidSpot;
    }
    
    /**
     * Check whether a given pid is taken by the scheduler
     * @param pid the pid to check for
     * @return true if it is being used, false otherwise
     */
    public boolean isScheduled(int pid) {
        for (int i = 0; i < nextPidSpot; i++) {
            if (this.pid[i] == pid) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Terminate a given process by removing it from the scheduler
     * @param pid the process pid to remove
     */
    public void terminateProcess(int pid) {
        boolean found = false;
        // for each pid in the pid array
        for (int i = 0; i < nextPidSpot; i++) {
            if (this.pid[i] == pid) {
                // If the pid matches zero it out
                this.pid[i] = 0;
                found = true;
            }
            else if (found) {
                // If we alread found it shift the array down
                this.pid[i-1] = this.pid[i];
            }
        }
        nextPidSpot--;
        
        // for each ticket in the ticket array
        for (int i = 0; i < nextTicketSpot; i++) {
            if (this.tickets[i] == pid) {
                // if the pid matches zero it out
                this.tickets[i] = 0;
            }
        }
        // call the helper function to fill in the zeros up to an index
        int removed = fillZeros(tickets, nextTicketSpot);
        nextTicketSpot -= removed;// the ticket array is shorter by that many zeros
    }
    
    /**
     * Remove any zeros from a given array up to a given index by shifting the array
     * contents over.
     * The index is not modified by this function (pass by value)
     * @param array the array to modify
     * @param index the max index to fill zeros in
     * @return the number of zeros removed (the shift amount of the last element)
     * @throws IllegalArgumentException if index is out of the array bounds
     */
    private static int fillZeros(int[] array, int index) {
        if (index > array.length) { throw new IllegalArgumentException("Index out of array bounds"); }
        int zeroCount = 0;// the number of zeros found
        for (int i = 0; i < index; i++) {
            if (array[i] == 0) {
                // if we found a zero add it to the count
                zeroCount++;
            }
            else if (zeroCount > 0) {
                // else we didn't find a zero but have found one previously
                // so shift the values over by that much (the else prevents shifting
                // zeros down ontop of valid data)
                array[i-zeroCount] = array[i];
            }
        }
        // return the number removed so the caller can update their internal array size
        return zeroCount;
    }
    
    /**
     * Create a new process with the given pid and ticket amount.
     * @param pid the pid to identify the new process by
     * @param tickets the number of lottery tickets to give this pid
     * @throws IllegalArgumentException if the pid is less than or equal to zero
     * @throws IllegalArgumentException if the pid is already scheduled
     * @throws IllegalArgumentException if the tickets given are larger than the max tickets
     * @throws IllegalStateException if the process table is already full
     */
    public void createProcess(int pid, int tickets) {
        if (pid <= 0) { throw new IllegalArgumentException("PID should be greater than zero"); }
        if (isScheduled(pid)) { throw new IllegalArgumentException("Process alread scheduled"); }
        if (tickets > maxTickets) { throw new IllegalArgumentException("Tickets exceeds the max number allowed"); }
        if (nextPidSpot >= this.pid.length) { throw new IllegalStateException("Process table full"); }
        // insert pid
        this.pid[nextPidSpot] = pid;
        nextPidSpot++;
        // insert tickets
        for (int i = 0; i < tickets; i++) {
            this.tickets[nextTicketSpot] = pid;
            nextTicketSpot++;
        }
    }
    
    /**
     * Pick a next process randomly based on drawing a random lottery ticket.
     * @return the process pid that was chosen
     */
    public int pickNextProcess() {
        Random rand = new Random();
        // Pick a process using the ticket array
        return this.tickets[rand.nextInt(nextTicketSpot)];
    }
    
    @Override
    public String toString() {
        String returnString = "";
        returnString += String.format("Process: %s\n", Arrays.toString(Arrays.copyOf(pid, nextPidSpot)));
        returnString += String.format("Tickets: %s\n", Arrays.toString(Arrays.copyOf(tickets, nextTicketSpot)));
        return returnString;
    }
    
    
}
