package memory_manager;

import java.util.Random;

/**
 * A mock memory manager test driver CSCI 331 HW 6
 * @author Ryan Jensen
 * @version April 25, 2015
 */
public class MemoryManagerDriver {

    public static void main(String[] args) {
        MemoryManager man = new MemoryManager(64);
        System.out.println("Initial view");
        man.printMap();
        
        int allocAmount = 0;
        int allocIndex = 0;
        Random ran = new Random();
        for(int i = 0; i < 14; i++) {
            if(i < 7) {
                allocAmount = ran.nextInt(5) + 6; // between 6 and 10 inclusive
            }
            else {
                allocAmount = ran.nextInt(4) + 1; // between 1 and 4 inclusive
            }
            allocIndex = man.allocate(i, allocAmount); // allocate to i
            if(allocIndex == -1) {
                System.out.printf("No memory allocated to pid %d.\n", i);
            } else { // memory was allocated to pid i
                System.out.printf("%d units allocated to pid %d starting at unit %d.\n", allocAmount, i, allocIndex);
            }


            if(i == 6) { // test deallocate after allocating the first 7
                System.out.printf("Memory map after allocating pid 0 through %d:\n", i);
                man.printMap();

                int deallocAmount;
                for(int j = 0; j <= 8; j+=2) { // iterations for 0 2 4 6 8
                    // 8 has not been allocated, and pid 0 doesn't exist
                    deallocAmount = man.deallocate(j); // deallocate pid j
                    System.out.printf("Deallocated %d units from pid %d.\n", deallocAmount, j);
                }

                System.out.printf("Memory map after deallocations:\n");
                man.printMap();
            } // end of deallocation tests, after allocating to pid 6
        }
        
        System.out.printf("Memory map at the end:\n");
	man.printMap();
    }

}
