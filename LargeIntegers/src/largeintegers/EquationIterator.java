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
     * @return current nodes parent or null if it doesn't exist 
     */
    public EquationNode parent(){
        return this.current.getParent();
    }
    
    /**
     * @return current nodes 
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
        this.setParent(parent, false);
    }
    
    public void setParent(EquationNode parent, boolean asSecondChild){
        this.current.setParent(parent);
        if (asSecondChild){
            parent.setSecondChild(this.current);
        }else {
            parent.setFirstChild(this.current);
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
