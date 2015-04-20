
package jensen_scheduler;

import java.util.Random;

/**
 * A test driver for the Scheduler class / hw using LOTTERY algorithm
 * @author Ryan Jensen
 * @version April 19, 2015
 */
public class SchedulerDriver {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Scheduler sch = new Scheduler();
        int procNum = 20;// number of initial processes
        Random ran = new Random();// random number generator
        
        // Initialize the processes with a random number of lottery tickets
        for (int i = 1; i <= procNum; i++) {
            sch.createProcess(i, ran.nextInt(15)+1);
        }
        // Print the current state of the scheduler
        System.out.println(sch);
        
        // For 5 * 4 runs
        for (int i = 0; i < 5; i++) {
            int newProcNum = procNum+i+1;
            int ticketsNum = ran.nextInt(15) + 1;
            // Create a new process
            sch.createProcess(newProcNum, ticketsNum);
            System.out.printf("Created process %d, with tickets %d\n", newProcNum, ticketsNum);
            for (int j = 0; j < 4; j++) {
                // Pick 4 new processes
                int picked = sch.pickNextProcess();
                System.out.printf("Picked process %d\n", picked);
            }
            // Terminate a process
            sch.terminateProcess(4*i + 1);
            System.out.printf("Terminated process %d\n", 4*i+1);
        }
        
        // Print the current state of the scheduler
        System.out.println(sch);
    }
    
}
