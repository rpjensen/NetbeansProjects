

package encryption_utility;

/**
 * Stores a mutable Triple 
 * @author Ryan Jensen
 * @version Sep 27, 2014
 */
public class Triple<A, B, C> {
    private A a;
    private B b;
    private C c;
    
    public Triple(A a, B b, C c){
        this.a = a;
        this.b = b;
        this.c = c;
    }
    
    public A getA(){
        return this.a;
    }
    
    public void setA(A a){
        this.a = a;
    }
    
    public B getB(){
        return this.b;
    }
    
    public void setB(B b){
        this.b = b;
    }
    
    public C getC(){
        return this.c;
    }
    
    public void setC(C c){
        this.c = c;
    }
}
