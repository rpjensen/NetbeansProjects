

package jensen_csci333_bst;

/**
 * A class that represents a single node in a binary search tree.  This object is
 * mutable which should be taken into consideration when using it.
 * @author Ryan Jensen
 * @version October 17, 2014
 */
public class BstNode {
    private int value;
    private BstNode parent;
    private BstNode leftChild;
    private BstNode rightChild;
    
    /**
     * Create a new BstNode with the given value, the parent and children references
     * are initialized to null
     * @param value the value this node will hold
     */
    public BstNode(int value){
        this.value = value;
        this.parent = null;
        this.leftChild = null;
        this.rightChild = null;
    }
    
    /**
     * @return the value contained in this node 
     */
    public int getValue(){
        return this.value;
    }
    
    /**
     * @param value the new value this node will hold 
     */
    public void setValue(int value){
        this.value = value;
    }
    
    /**
     * @return a handle to this node's parent
     */
    public BstNode getParent(){
        return this.parent;
    }
    
    /**
     * @param parent the new parent of this node
     */
    public void setParent(BstNode parent){
        this.parent = parent;
    }
    
    /**
     * @return the handle to this node's left child
     */
    public BstNode getLeftChild(){
        return this.leftChild;
    }
    
    /**
     * @param leftChild the new left child of this node
     */
    public void setLeftChild(BstNode leftChild){
        this.leftChild = leftChild;
    }
    
    /**
     * @return a handle to the right child of this node
     */
    public BstNode getRightChild(){
        return this.rightChild;
    }
    
    /**
     * @param rightChild the new right child of this node
     */
    public void setRightChild(BstNode rightChild){
        this.rightChild = rightChild;
    }
}
