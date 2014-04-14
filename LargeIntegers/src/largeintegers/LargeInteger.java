
package largeintegers;

/**
 *
 * @author Ryan Jensen
 * @version April 28, 2014
 */
public interface LargeInteger {
    public int compareTo(LinkedLargeInteger otherNum);
    @Override
    public boolean equals(Object object);
    @Override
    public String toString();
    public boolean isNegative();
    public LargeInteger negate();
    public LinkedLargeInteger add(LinkedLargeInteger otherNum);
    public LinkedLargeInteger subtract(LinkedLargeInteger otherNum);
    public LinkedLargeInteger multiply(LinkedLargeInteger otherNum);
    public LinkedLargeInteger divide(LinkedLargeInteger otherNum);
    public LinkedLargeInteger pow(LinkedLargeInteger otherNum);
    public LinkedLargeInteger remainder(LinkedLargeInteger otherNum);
    public LinkedLargeInteger mod(LinkedLargeInteger otherNum);
    
}
