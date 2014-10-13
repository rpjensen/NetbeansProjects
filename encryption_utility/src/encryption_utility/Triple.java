

package encryption_utility;

/**
 * Stores t mutable Triple 
 * @author Ryan Jensen
 * @version Sep 27, 2014
 */
public class Triple<T, U, V> {
    private T t;
    private U u;
    private V v;
    
    public Triple(T t, U u, V v){
        this.t = t;
        this.u = u;
        this.v = v;
    }
    
    public T getA(){
        return this.t;
    }
    
    public void setA(T t){
        this.t = t;
    }
    
    public U getB(){
        return this.u;
    }
    
    public void setB(U u){
        this.u = u;
    }
    
    public V getC(){
        return this.v;
    }
    
    public void setC(V v){
        this.v = v;
    }
}
