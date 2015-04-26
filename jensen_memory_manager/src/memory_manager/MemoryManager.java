package memory_manager;

import java.util.LinkedList;
import java.util.ListIterator;

/**
 * A mock memory manager for CSCI 331 HW 6
 * @author Ryan Jensen
 * @version April 25, 2015
 */
public class MemoryManager {

    // -----data fields-----
    private final int memorySize; // number of allocation units in "memory"
    private LinkedList<MemorySection> list; // The memory map, as a linked list.
    // Each MemorySection node in the list is a free section or an address space.

    // -----constructors-----
    /**
     * Constructor of MemoryManager class.
     *
     * @param memorySize The number of allocation units in the simulated memory.
     * Must be 1 or greater.
     */
    public MemoryManager(int memorySize) {
        if (memorySize < 1) {
            System.err.println("MemoryManager constructor, invalid memorySize "
                    + memorySize);
        }
        this.memorySize = memorySize;
        list = new LinkedList<>();
        list.add(new MemorySection(0, memorySize));
        // the memory map, upon initial construction, consists of:
        // 1 free section, starting at allocation unit 0, size memorySize
    }

    // -----other class methods-----
    /**
     * Use first fit swapping memory management algorithm to allocate an address
     * space for the given pid, consisting of u allocation units.
     *
     * @param pid PID of process whose address space you wish to allocate.
     * Must be greater than 0. This process must not already have an address
     * space allocated.
     * @param u Number of allocation units for this process's address space.
     * Must be 1 or greater.
     * @return The allocation unit number where the allocated address space
     * begins. Returns -1 if u is 0 or negative, the pid is 0 or negative,
     * memory has already been allocated to that pid, or the memory cannot be
     * allocated (i.e. no free section big enough)
     */
     public int allocate(int pid, int u) {
         // check the size
         if (u < 1 || u > memorySize) { return -1; }
         if (pid < 1) { return -1; }
         // check if the memory has already been allocated to that pid
         for (MemorySection mem : list) {
             if (mem.getOwnerPid() == pid) {
                 return -1; 
             }
         }

        MemorySection match = null;
        // iterate through the memory sections
        for (MemorySection mem : list) {
            
            if (mem.getOwnerPid() == 0 && mem.getSize() >= u) {
                match = mem;
                // avoid modifying the linked list while in the for each loop... Baaaad
                break;
            }
        }
        if (match != null) {
            // if we found a free section large enough, split it into a taken and free section
            int newSize = match.getSize() - u;// size of the remaining free section
            int start = match.getStart();// where the new section starts

            match.setOwnerPid(pid);// replace the current free section with the new pid
            match.setSize(u);
            // If there is still some free space left
            if (newSize > 0) {
                // get an iterater pointing at the location
                ListIterator<MemorySection> iter = list.listIterator(list.indexOf(match));
                iter.next();
                // add the free section
                iter.add(new MemorySection(start+u, newSize));
            }
            return start;
        }
        return -1;
    }
    
    /**
     * Deallocate an address space.
     *
     * @param pid PID of process whose address space you wish to deallocate.
     * Must be greater than 0
     * @return The number of allocation units freed. If the pid is 0 or
     * negative, do nothing and return 0 units freed.
     */
    public int deallocate(int pid) {
        if (pid < 1) { return 0; }
        
        MemorySection match = null;
        int removed = 0;
        // iterate through the memory sections
        for (MemorySection mem : list) {
            // if we found a match
            if (mem.getOwnerPid() == pid) {
                match = mem;
                // avoid modifying the linked list while in the for each loop... Baaaad
                break;
            }
        }
        
        if (match != null) {
            match.setOwnerPid(0);// now its a free section
            removed = match.getSize();
            ListIterator<MemorySection> iter = list.listIterator(list.indexOf(match));
            MemorySection prev = iter.hasPrevious() ? iter.previous() : null;// get the section before match
            if (prev != null) {
                iter.next();// returns prev again but cursor is on its right
            }
            iter.next();// iter is pointing at match
            
            if (prev != null && prev.getOwnerPid() == 0) {
                // if previous is a free section merge it with the one before it
                prev.setSize(prev.getSize() + match.getSize());
                iter.remove();// remove match
                match = prev;// now prev is filling the role of match for the next part
                // iter should be right after prev and before next
            }
            
            MemorySection next = iter.hasNext() ? iter.next() : null;
            if (next != null && next.getOwnerPid() == 0) {
                // if next is also a free section
                match.setSize(match.getSize() + next.getSize());
                iter.remove();// merge next with match
            }
        }
        return removed;
    }
    
    /**
     * Prints out this memory map, one memory section at a time. Iterates
     * through the MemorySection nodes and prints each one.
     */
    public void printMap() {
        System.out.println(this);
    }
    
    @Override
    public String toString() {
        String retVal = "Total Memory " + this.memorySize + "\n";
        for (MemorySection mem : list) {
            retVal += mem + "\n";
        }
        return retVal;
    }
    
    // -----getter methods-----
    /**
     * memorySize getter method.
     *
     * @return The number of allocation units in this memory manager's "memory."
     */
    public int getMemorySize() {
        return this.memorySize;
    }
    
    /**
     * Determine where the address space of the given pid starts.
     *
     * @param pid Process PID you are querying. Must be greater than 0.
     * @return The allocation unit number where this process's address space
     * starts. Returns -1 if this pid has no address space.
     */
    public int getAddressSpaceStart(int pid) {
        if (pid < 1) { throw new IllegalArgumentException("pid should be greater than zero"); }
        
        for (MemorySection mem : list) {
            if (mem.getOwnerPid() == pid) {
                return mem.getStart();
            }
        }
        return -1;
    }
    
    /**
     * Get the size of a process's address space.
     *
     * @param pid Process PID you are querying. Must be greater than 0.
     * @return The number of allocation units in this process's address space.
     * Returns 0 if this pid has no address space.
     */
    public int getAddressSpaceSize(int pid) {
        if (pid < 1) { throw new IllegalArgumentException("pid should be greater than zero"); }
        
        for (MemorySection mem : list) {
            if (mem.getSize() == pid) {
                return mem.getSize();
            }
        }
        return -1;
    }
}
