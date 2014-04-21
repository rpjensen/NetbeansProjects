
package largeintegers;

/**
 *
 * @author Ryan Jensen
 * @version April 28, 2014
 */
public interface LargeInteger {
    public int compareTo(LargeInteger largeInt);
    @Override
    public boolean equals(Object object);
    @Override
    public String toString();
    public boolean isNegative();
    public LargeInteger negate();
    public LargeInteger add(LargeInteger largeInt);
    public LargeInteger add(long largeInt);
    public LargeInteger subtract(LargeInteger largeInt);
    public LargeInteger subtract(long largeInt);
    public LargeInteger multiply(LargeInteger largeInt);
    public LargeInteger multiply(long largeInt);
    public LargeInteger dividedBy(LargeInteger largeInt);
    public LargeInteger dividedBy(long largeInt);
    public LargeInteger pow(LargeInteger largeInt);
    public LargeInteger pow(long largeInt);
    public LargeInteger remainder(LargeInteger largeInt);
    public LargeInteger remainder(long largeInt);
    public LargeInteger modulo(LargeInteger largeInt);
    public LargeInteger modulo(long largeInt);
    
}
