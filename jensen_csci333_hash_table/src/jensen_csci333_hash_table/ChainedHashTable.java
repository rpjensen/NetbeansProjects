
package jensen_csci333_hash_table;

import java.math.BigInteger;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;

/**
 * Hash Table implementation using linked lists as bucket values.
 * @author Ryan Jensen
 * @version Oct 4, 2014
 */
public class ChainedHashTable {
    private final LinkedList<Integer>[] table;
    private final UniversalHashFunction hash;
    
    /**
     * Construct a newly initialized Chained Hash table that has the next power 
     * of 2 greater than n number of buckets 
     * @param n the number of buckets (LinkedLists)
     */
    public ChainedHashTable(int n){
        //Easy way to get the bit length (given that n is positive
        int bits = Integer.toBinaryString(n).length();
        
        //bit shift to get next power of two and cast so our code is type safe
        this.table =  (LinkedList<Integer>[]) new LinkedList[1 << (bits)];
        for (int i = 0; i < this.table.length; i++){
            this.table[i] = new LinkedList<>();//initialize the buckets
        }
        this.hash = new UniversalHashFunction(this.table.length);//nested inner class to handle the universal hash
    }
    
    /**
     * Insert the given Integer object into the hash table
     * @param object the object to insert
     */
    public void insert(Integer object){
        int bucket = hash.hash(object.intValue());
        this.table[bucket].add(object);
    }
    
    /**
     * Attempt to remove the Object with the given key from the table and return
     * a handle to that object after it is deleted
     * @param key the objects key
     * @return a handle to the deleted object or null if it wasn't found
     */
    public Integer delete(int key){
        int bucket = hash.hash(key);
        for (Iterator<Integer> i = this.table[bucket].iterator(); i.hasNext(); ){
            Integer current = i.next();
            if (current.intValue() == key){
                i.remove();
                return current;
            }
        }
        return null;
    }
    
    /**
     * Search for the Integer object whose key is its int value
     * @param key the key to use to look for the object
     * @return a handle to the Integer object or null if it isn't in the table
     */
    public Integer search(int key){
        int bucket = hash.hash(key);
        for (Iterator<Integer> i = this.table[bucket].iterator(); i.hasNext(); ){
            Integer current = i.next();
            if (current.intValue() == key){
                return current;
            }
        }
        return null;
    }
    
    /**
     * Print the Array of linked list to console to test the distribution of the
     * hashing function across the buckets.
     */
    public void printTable(){
        int i = 0;
        for(LinkedList<Integer> list : this.table){
            System.out.printf("Bucket: %d => %s\n", i, list.toString());
            i++;
        }
    }
    
    /**
     * Prints a string representation of the Hash where the values are of the 
     * format [val,val,...,val]. Note val will never be null.
     * @return a string representation of the chained hash table.
     */
    @Override
    public String toString(){
        StringBuilder string = new StringBuilder("[");
        boolean first = true;
        for (LinkedList<Integer> list : this.table){
            if (list != null){
                for (Integer value : list){
                   if (!first){
                       string.append(", ");
                   }
                   string.append(value);
                   first = false;
               }               
            }

        }
        string.append("]");
        return string.toString();
    }
    
    /**
     * Private immutable inner class to handle the random generation of the universal
     * hash constants and prime number.
     */
    private class UniversalHashFunction {
        private final int a;
        private final int b;
        private final int p;
        private final int mod;
        
        /**
         * Create a new Universal Hash normalized for a table with mod number of
         * buckets.
         * Pre-Condition: mod has a power of two that is larger than it and a valid
         * positive integer.
         * @param mod the number of buckets for the hash table 
         */
        private UniversalHashFunction(int mod){
            Random gen = new Random();
            this.a = gen.nextInt(mod - 1) + 1;
            this.b = gen.nextInt(mod);
            this.mod = mod;

            //I could have included the Rabin-Miller Primality testing code from
            //my senior seminar on encryption but I figured I would use the normal
            //function
            BigInteger prime = new BigInteger(Integer.toString(mod)).nextProbablePrime();
            this.p = prime.intValue();
        }
        
        /**
         * Computes the hash for a given key
         * @param key the key to compute the hash of
         * @return the integer hash value for this key
         */
        private int hash(int key){
            int val =  (a*key + b) % p;
            if (val < 0){
                val = val + p;//resolve the difference between mod and remainder
            }
            val = val % mod;
            return val;
        }
    }
}
