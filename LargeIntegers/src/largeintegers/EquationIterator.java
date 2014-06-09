/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package largeintegers;

/**
 *
 * @author Ryan Jensen
 * @version May 31, 2014
 */
public class EquationIterator {
    private EquationNode current;
    public static final boolean FIRST_CHILD = true;
    
    /**
     * Return a new Equation Iterator pointing to the node current
     * @param current the node to point at
     */
    public EquationIterator(EquationNode current){
        this.current = current;
    }
    
    /**
     * @return the node being pointed at currently
     */
    public EquationNode getCurrent(){
        return this.current;
    }
    
    /**
     * Test if there is a parent node to move to
     * @return true if parent is non-null, else false
     */
    public boolean hasParent(){
        return this.current.getParent() != null;
    }
    
    /**
     * Test if there is a first child node to move to
     * @return true if first child is non-null, else false
     */
    public boolean hasFirstChild(){
        return this.current.getFirstChild() != null;
    }
    
    /**
     * Test if there is a second child node to move to
     * @return true if second child is non-null, else false
     */
    public boolean hasSecondChild(){
        return this.current.getSecondChild() != null;
    }
    
    /**
     * @return current node's parent or null if it doesn't exist 
     */
    public EquationNode parent(){
        return this.current.getParent();
    }
    
    /**
     * @return current node's first child or null if it doesn't 
     */
    public EquationNode firstChild(){
        return this.current.getFirstChild();
    }
    
    public EquationNode secondChild(){
        return this.current.getSecondChild();
    }
    
    public EquationNode goToParent(){
        this.current = this.current.getParent();
        return this.current;
    }
    
    public EquationNode goToFirstChild(){
        this.current = this.current.getFirstChild();
        return this.current;
    }
    
    public EquationNode goToSecondChild(){
        this.current = this.current.getSecondChild();
        return this.current;
    }
    
    public void setParent(EquationNode parent){
        if (parent.getOperator().isUnary() || parent.getFirstChild() == null){
            this.forceSetParent(parent, FIRST_CHILD);
        }
        else {
            this.forceSetParent(parent, !FIRST_CHILD);
        }
    }
    
    public void forceSetParent(EquationNode parent, boolean asFirstChild){
        this.current.setParent(parent);
        if (asFirstChild){
            parent.setFirstChild(this.current);
        }else {
            parent.setSecondChild(this.current);
        }  
    }
    
    public void setFirstChild(EquationNode firstChild){
        this.current.setFirstChild(firstChild);
        firstChild.setParent(this.current);
    }
        
    public void setSecondChild(EquationNode secondChild){
        this.current.setSecondChild(secondChild);
        secondChild.setParent(this.current);
    }
}
