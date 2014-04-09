
package largeintegers;

/**
 *
 * @author Ryan Jensen
 * @version April 7, 2014
 */
public interface LargeInteger {
    public int compareTo(Object object);
    @Override
    public boolean equals(Object object);
    @Override
    public String toString();
    public LinkedLargeInteger negate();
    public LinkedLargeInteger add(LinkedLargeInteger otherNum);
    public LinkedLargeInteger subract(LinkedLargeInteger otherNum);
    public LinkedLargeInteger multiply(LinkedLargeInteger otherNum);
    public LinkedLargeInteger divide(LinkedLargeInteger otherNum);
    public LinkedLargeInteger pow(LinkedLargeInteger otherNum);
    public LinkedLargeInteger remainder(LinkedLargeInteger otherNum);
    public LinkedLargeInteger mod(LinkedLargeInteger otherNum);
    
}
