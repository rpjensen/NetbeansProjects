

package encryption_utility;

/**
 * Stores t mutable Triple 
 * @author Ryan Jensen
 * @version Sep 27, 2014
 */
public class Pair<T, U> {
    private T t;
    private U u;
    
    public Pair(T t, U u){
        this.t = t;
        this.u = u;
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
}
