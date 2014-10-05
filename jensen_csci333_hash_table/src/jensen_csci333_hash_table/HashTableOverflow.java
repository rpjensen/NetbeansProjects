
package jensen_csci333_hash_table;

/**
 *
 * @author Ryan
 */
public class HashTableOverflow extends RuntimeException {

    /**
     * Creates a new instance of
     * <code>HashTableOverflow</code> without detail message.
     */
    public HashTableOverflow() {
    }

    /**
     * Constructs an instance of
     * <code>HashTableOverflow</code> with the specified detail message.
     *
     * @param msg the detail message.
     */
    public HashTableOverflow(String msg) {
        super(msg);
    }
}
