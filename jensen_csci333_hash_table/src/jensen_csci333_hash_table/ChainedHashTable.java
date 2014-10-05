
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
public class ChainedHashTable implements HashTable<Integer>{
    private final LinkedList<Integer>[] table;
    private final UniversalHashFunction hash;
    
    public ChainedHashTable(int n){
        int bits = Integer.toBinaryString(n).length();
        
        this.table =  (LinkedList<Integer>[]) new LinkedList[bits+1];
        for (int i = 0; i < this.table.length; i++){
            this.table[i] = new LinkedList<>();
        }
        this.hash = new UniversalHashFunction(this.table.length);
    }
    
    @Override
    public void insert(Integer key){
        int bucket = hash.hash(key);
        this.table[bucket].add(key);
    }
    
    @Override
    public Integer delete(Integer key){
        int bucket = hash.hash(key);
        for (Iterator<Integer> i = this.table[bucket].iterator(); i.hasNext(); ){
            int current = i.next();
            if (current == key){
                i.remove();
                return current;
            }
        }
        return null;
    }
    
    @Override
    public Integer search(Integer key){
        int bucket = hash.hash(key);
        for (Iterator<Integer> i = this.table[bucket].iterator(); i.hasNext(); ){
            int current = i.next();
            if (current == key){
                return key;
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
    }
}
