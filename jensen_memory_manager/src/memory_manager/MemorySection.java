package memory_manager;

/**
 * The MemorySection class represents either a process's address space, or a
 * free section. (Please do not modify this class.) MemorySections are directly
 * comparable based on the Comparable or Comparator interfaces. MemorySection
 * objects are compared by the allocation unit at the start of their section,
 * e.g. where in memory they start. When using this class, MAKE SURE each
 * MemorySection has a unique starting unit, so each memory section "begins" in
 * a different place.
 *
 * @author awhitley
 */
public class MemorySection implements Comparable<MemorySection>, java.util.Comparator<MemorySection> {

    // -----data fields-----
    private int ownerPid; // Process Pid that owns this address space. 0 for free section.
    private int size; // number of allocation units in this section
    private int start; // lowest-numbered (i.e. "starting") allocation unit of this section

    // -----constructors-----
    /**
     * Create a new free section
     *
     * @param start lowest-numbered (i.e. "starting") allocation unit of this
     * free section. Use an argument 0 or greater.
     * @param size number of allocation units in this free section. Use an
     * argument 1 or greater.
     */
    public MemorySection(int start, int size) {
        if (start < 0 || size < 1) {
            System.err.println("Invalid construction of free section MemorySection. "
                    + "start = " + start + ", size = " + size + ".");
        }
        ownerPid = 0;
        this.size = size;
        this.start = start;
    }

    /**
     * Create a new address space.
     *
     * @param start lowest-numbered (i.e. "starting") allocation unit of this
     * free section. Use an argument 0 or greater.
     * @param size number of allocation units in this free section. Use an
     * argument 1 or greater.
     * @param owner process PID that owns this address space. Use an argument 1
     * or greater.
     */
    public MemorySection(int start, int size, int owner) {
        if (start < 0 || size < 1 || owner < 1) {
            System.err.println("Invalid construction of address space MemorySection. "
                    + "start = " + start + ", size = " + size + ", ownerPid = " + owner + ".");
        }
        ownerPid = owner;
        this.size = size;
        this.start = start;
    }

    // -----setter methods-----
    /**
     * size setter method
     *
     * @param size New size for the section. Must be 1 or greater.
     */
    public void setSize(int size) {
        if (size < 1) {
            System.err.println("Invalid setSize size: " + size + ". size was not set.");
            return;
        }
        this.size = size;
    }

    /**
     * start setter method
     *
     * @param start New starting allocation unit for this section. Must be 0 or
     * greater.
     */
    public void setStart(int start) {
        if (start < 0) {
            System.err.println("Invalid setStart start: " + start + ". start was not set.");
            return;
        }
        this.start = start;
    }

    /**
     * ownerPid setter method
     *
     * @param owner New owner PID for this section. Must be 0 or greater. Use
     * argument 0 to make this a free section. Use an argument > 0 to make this
     * an address space of the provided owner PID.
     */
    public void setOwnerPid(int owner) {
        if (owner < 0) {
            System.err.println("Invalid setOwnerPid owner: " + owner + ". ownerPid was not set.");
            return;
        }
        this.ownerPid = owner;
    }

    // -----getter methods-----
    /**
     * size getter method
     *
     * @return Number of allocation units in this section, i.e. its size.
     */
    public int getSize() {
        return size;
    }

    /**
     * start getter method
     *
     * @return lowest-numbered (i.e. "starting") allocation unit of this
     * section.
     */
    public int getStart() {
        return start;
    }

    /**
     * ownerPid getter method
     *
     * @return The process PID of the owner of this section. 0 if this section
     * is a free section. Nonzero if this is the address space of a process.
     */
    public int getOwnerPid() {
        return ownerPid;
    }

    // -----interface methods-----
    // Don't even worry about these. They're necessary for 
    // the Java Standard Library to work property with MemorySection class.
    // Have a look at toString if you want, to see what a MemorySection would
    // "look like" when converted to a String for printing.
    @Override
    public int compareTo(MemorySection other) {
        return Integer.compare(this.start, other.start); // compare their keys
    }

    @Override
    public int compare(MemorySection o1, MemorySection o2) {
        return o1.compareTo(o2);
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof MemorySection && ((MemorySection) obj).start == this.start);
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 89 * hash + this.start;
        return hash;
    }

    /* The toString method allows you to print a MemorySection object directly.
     * It will generate an informative String useful as a print statement argument.
     * For example, if you had a MemorySection object named f, you could simply say:
     * System.out.println(f);
     * and you can expect a nicely formatted output of the status of the MemorySection f.
     */
    @Override
    public String toString() {
        String s = "";
        if (ownerPid == 0) { // free section
            s += "Free Section, ";
        } else { // address space
            s += "Address Space of PID " + ownerPid + ", ";
        }

        s += "starting at unit " + start + ", size " + size + ".";

        return s;
    }
}
