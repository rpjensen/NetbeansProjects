package jensen_csci333_bfs;

/**
 *
 * @author Adam Whitley Nov 3 2014 CSCI 333 HW 9 Vertex class, provided to
 * students.
 */
public class Vertex {

    // ----- Public Constants -----
    /**
     * The vertex color WHITE.
     */
    public static final int WHITE = 0; // unvisited vertex color

    /**
     * The vertex color GRAY.
     */
    public static final int GRAY = 1; // discovered vertex color

    /**
     * The vertex color BLACK.
     */
    public static final int BLACK = 2; // visited vertex color

    /**
     * Infinity, as an infinite distance to the breadth-first source.
     */
    public static final int INFINITY = -1; // Our standin for infinite distance. distances are never actually negative.

    // ----- Data Fields -----
    private final int label; // immutable data field for the label or "name" of the vertex

    private int color;
    private int distance;
    private Vertex parent;

    // ----- Class Methods -----
    /**
     * Construct a new vertex with Vertex.WHITE color, Vertex.INFINITE distance,
     * and a null parent.
     *
     * @param label The label i.e. name for the constructed vertex. This is
     * immutable after construction.
     */
    public Vertex(int label) {
        color = Vertex.WHITE;
        distance = INFINITY;
        parent = null;
        this.label = label;
    }

    /* This toString method allows you to print a Vertex object directly.
     * It will generate an informative string for the print statement you use it in.
     * For example, if you had a Vertex object named f, you could simply say:
     * System.out.println(f);
     * and you can expect a nicely formatted output of the status of the Vertex f.
     */
    @Override
    public String toString() {
        String s = "";
        s += "Vertex " + label + ".";
        s += " Color: ";
        switch (color) {
            case Vertex.WHITE:
                s += "WHITE.";
                break;
            case Vertex.GRAY:
                s += "GRAY.";
                break;
            case Vertex.BLACK:
                s += "BLACK.";
                break;
            default:
                System.err.println("Invalid vertex color: " + color + " found.");
        }
        s += " Distance: " + distance + ".";
        s += " Parent's label: ";
        if (parent == null) {
            s += "null.";
        } else {
            s += "" + parent.label + ".";
        }

        return s;
    }

    // ----- Getter Methods -----
    /**
     *
     * @return Color of this Vertex: 0 1 or 2. You can compare the return value to Vertex.WHITE, Vertex.GRAY, or Vertex.BLACK.
     */
    public int getColor() {
        return color;
    }

    /**
     *
     * @return Distance from this vertex to the most recent breadth-first tree
     * source.
     */
    public int getDistance() {
        return distance;
    }

    /**
     *
     * @return Parent of this vertex in the most recently computed breadth-first
     * tree.
     */
    public Vertex getParent() { // may return null, beware!
        return parent;
    }

    /**
     *
     * @return Label (the name) of this vertex.
     */
    public int getLabel() {
        return label;
    }

    // ----- Setter Methods -----
    /**
     *
     * @param color the desired color of this vertex. Must be one of the
     * following: Vertex.WHITE, Vertex.GRAY, Vertex.BLACK.
     * @throws IllegalArgumentException Thrown when the parameter is not any of the following:
     * Vertex.WHITE, Vertex.GRAY, Vertex.BLACK.
     */
    public void setColor(int color) {
        if (color != Vertex.WHITE && color != Vertex.GRAY && color != Vertex.BLACK) {
            throw new IllegalArgumentException("Invalid color " + color + " used. Use Vertex.WHITE Vertex.GRAY or Vertex.BLACK.");
        }
        this.color = color;
    }

    /**
     *
     * @param distance Distance from source vertex in the breadth-first tree.
     * Must be nonnegative -- 0 or greater.
     * @throws IllegalArgumentException Thrown when distance is negative. Note: When any Vertex is constructed, it is automatically set with a Vertex.INFINITE distance.
     */
    public void setDistance(int distance) {
        if (distance < 0) {
            throw new IllegalArgumentException("Invalid distance " + distance + " used. Use a nonnegative distance.");
        }
        this.distance = distance;
    }

    /**
     *
     * @param parent Vertex handle to desired parent Vertex for this Vertex. You
     * may provide a null Vertex handle,indicating this vertex has no parent.
     */
    public void setParent(Vertex parent) {
        this.parent = parent;
    }

}
