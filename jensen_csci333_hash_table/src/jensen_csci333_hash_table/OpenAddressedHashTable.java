
package jensen_csci333_hash_table;

import java.math.BigInteger;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;

/**
 *
 * @author Ryan Jensen
 * @version Oct 4, 2014
 */
public class OpenAddressedHashTable implements HashTable<Integer>{

    private  final Integer[] table;
    private  final UniversalHashFunction hash;
    
    public static final Integer DELETED = Integer.MIN_VALUE;
    
    public OpenAddressedHashTable(int n){
        int bits = Integer.toBinaryString(n).length();
        
        this.table = new Integer[bits+1];  
        this.hash = new UniversalHashFunction(this.table.length);
    }
    
   
    @Override
    public void insert(Integer key){
        for (int i = 0; i < this.table.length; i++){
            int bucket = hash.probe(key, i);

            if (this.table[bucket] == null || this.table[bucket].equals(DELETED)){
                this.table[bucket] = key;
                return;
            }
        }
        throw new HashTableOverflow("HashTable is full");
    }
    
    @Override
    public Integer delete(Integer key){
        for (int i = 0; i < this.table.length; i++){
            int bucket = hash.probe(key, i);
            
            if (this.table[bucket] == null){
                return null;
            }
            if (this.table[bucket].equals(key)){
                Integer returnVal = this.table[bucket];
                this.table[bucket] = DELETED;
                return returnVal;
            }
        }
        return null;
    }
    
    @Override
    public Integer search(Integer key){
        for (int i = 0; i < this.table.length; i++){
            int bucket = this.hash.probe(key, i);
            if (this.table[bucket] == null){
                return null;
            }
            if (this.table[bucket].equals(key)){
                return this.table[bucket];
            }
        }
        return null;
    }
    
    private class UniversalHashFunction {
        private final int a;
        private final int b;
        private final int p;
        private final int mod;
        
        private UniversalHashFunction(int mod){
            Random gen = new Random();
            this.a = gen.nextInt(mod - 1) + 1;
            this.b = gen.nextInt(mod);
            this.mod = mod;

            
            BigInteger prime = new BigInteger(Integer.toString(mod)).nextProbablePrime();
            this.p = prime.intValue();
        }
        
        private int hash(Integer key){
            return ((a*key + b) % p) % mod;
        }
        
        private int probe(Integer key, int index){
            return (this.hash(key) + index) % mod;
        }
    }
}
