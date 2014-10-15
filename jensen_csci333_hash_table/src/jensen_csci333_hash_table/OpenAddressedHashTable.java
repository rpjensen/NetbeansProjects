
package jensen_csci333_hash_table;

import java.math.BigInteger;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;

/**
 * An Open Addressed Hash Table that uses linear probing to place values in the table
 * @author Ryan Jensen
 * @version Oct 4, 2014
 */
public class OpenAddressedHashTable {
    private  final Integer[] table;
    private  final UniversalHashFunction hash;
    
    /** Creates a flag for Deleted Objects */
    public static final Integer DELETED = Integer.MIN_VALUE;
    
    /**
     * Create an open addressed hash table that can store up to n Integer objects
     * @param n the max number of integers it can store
     */
    public OpenAddressedHashTable(int n){
        int bits = Integer.toBinaryString(n).length();
        
        this.table = new Integer[1 << (bits)];  
        this.hash = new UniversalHashFunction(this.table.length);
    }
    
    /**
     * Insert an Integer object into the hash table
     * @param object the object to insert
     */
    public void insert(Integer object){
        for (int i = 0; i < this.table.length; i++){
            int bucket = hash.probe(object.intValue(), i);

            if (this.table[bucket] == null || this.table[bucket].equals(DELETED)){
                this.table[bucket] = object;
                return;
            }
        }
        throw new IllegalStateException("HashTable is full");
    }
    
    /**
     * Attempt to delete the object with the given key. Returns an object handle
     * if deleted else null if it wasn't in the table.
     * @param key the key of the object to delete
     * @return the handle to the deleted object, else null
     */
    public Integer delete(int key){
        for (int i = 0; i < this.table.length; i++){
            int bucket = hash.probe(key, i);
            
            if (this.table[bucket] == null){
                return null;
            }
            if (this.table[bucket].intValue() == key){
                Integer returnVal = this.table[bucket];
                this.table[bucket] = DELETED;
                return returnVal;
            }
        }
        return null;
    }
    
    /**
     * Search for an Integer object whose key is the given key
     * @param key the key of the object to search
     * @return the objects handle if found, else null
     */
    public Integer search(int key){
        for (int i = 0; i < this.table.length; i++){
            int bucket = this.hash.probe(key, i);
            if (this.table[bucket] == null){
                return null;
            }
            if (this.table[bucket].intValue() == key){
                return this.table[bucket];
            }
        }
        return null;
    }
    
    /**
     * Print a table view to the console for debugging
     */
    public void printTable(){
        StringBuilder string = new StringBuilder("[");
        boolean first = true;
        for (Integer value : this.table){
            if (value != null){
                if (!first){
                    string.append(", ");
                }
                if (value.equals(DELETED)){
                    string.append("DELETED");
                }
                else {
                    string.append(value);
                }
                first = false;                
            }
        }
        string.append("]");
        System.out.print(string.toString());
    }
    
    /**
     * Create a string representation of the hash table in the format
     * [val,val,...,val] where none of the values are null
     * @return 
     */
    @Override
    public String toString(){
        StringBuilder string = new StringBuilder("[");
        boolean first = true;
        for (Integer value : this.table){
            if (value != null){
                if (!first){
                    string.append(", ");
                }
                if (value.equals(DELETED)){
                    string.append("DELETED");
                }
                else {
                    string.append(value);
                }
                first = false;                
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
        
        /**
         * Get the index'th value in the probe sequence for this key
         * @param key
         * @param index
         * @return 
         */
        private int probe(int key, int index){
            return (this.hash(key) + index) % mod;
        }
    }
}
