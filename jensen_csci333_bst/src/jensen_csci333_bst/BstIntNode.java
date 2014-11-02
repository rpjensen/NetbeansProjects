

package jensen_csci333_bst;

/**
 * A class that represents a single node in a binary search tree.  This object is
 * mutable which should be taken into consideration when using it.
 * @author Ryan Jensen
 * @version October 17, 2014
 */
public class BstIntNode {
    private Interval key;
    private int max;
    private BstIntNode parent;
    private BstIntNode leftChild;
    private BstIntNode rightChild;
    
    /**
     * Create a new BstNode with the given value, the parent and children references
     * are initialized to null
     * @param key the Interval this node will hold
     */
    public BstIntNode(Interval key){
        this.key = key;
        this.parent = null;
        this.leftChild = null;
        this.rightChild = null;
        this.max = 1;
    }
    
    /**
     * @return the value contained in this node 
     */
    public Interval getInterval(){
        return this.key;
    }
    
    /**
     * @param key the new Interval this node will hold 
     */
    public void setInterval(Interval key){
        this.key = key;
    }
    
    /**
     * @return a handle to this node's parent
     */
    public BstIntNode getParent(){
        return this.parent;
    }
    
    /**
     * @param parent the new parent of this node
     */
    public void setParent(BstIntNode parent){
        this.parent = parent;
    }
    
    /**
     * @return the handle to this node's left child
     */
    public BstIntNode getLeftChild(){
        return this.leftChild;
    }
    
    /**
     * @param leftChild the new left child of this node
     */
    public void setLeftChild(BstIntNode leftChild){
        this.leftChild = leftChild;
    }
    
    /**
     * @return a handle to the right child of this node
     */
    public BstIntNode getRightChild(){
        return this.rightChild;
    }
    
    /**
     * @param rightChild the new right child of this node
     */
    public void setRightChild(BstIntNode rightChild){
        this.rightChild = rightChild;
    }
    
    public void updateMax(){
        int leftMax = 0;
        int rightMax = 0;
        
        if (this.getLeftChild() != null){
            leftMax = getLeftChild().max;
        }
        if (this.getRightChild() != null){
            rightMax = getRightChild().max;
        }
        
        this.max = Math.max(this.max, Math.max(leftMax, rightMax));
    }
    
    public int getMax(){
        return this.max;
    }
}
