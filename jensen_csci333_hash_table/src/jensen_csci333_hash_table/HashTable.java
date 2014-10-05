
package jensen_csci333_hash_table;

/**
 *
 * @author Ryan
 */
public interface HashTable<E> {
    public void insert(E key);
    public E search(E key);
    public E delete(E key);
}
