
package largeintegers;

/**
 *
 * @author Ryan Jensen
 * @version May 31, 2014
 */
public class EquationTree {
    private EquationNode top;
    private EquationNode lastPlaced;
    private EquationIterator iterator;
    
    public EquationTree(EquationNode top){
        this.top = new EquationNode(top.getOperator(), null, top.getValue());
        this.lastPlaced = this.top;
        this.iterator = new EquationIterator(this.top);
    }
    
    public EquationIterator getIterator(){
        return new EquationIterator(this.top);
    }
        
}
