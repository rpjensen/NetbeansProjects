

package jensen_csci333_bst;

/**
 * A class that represents a single node in a binary search tree.  This object is
 * mutable which should be taken into consideration when using it.
 * @author Ryan Jensen
 * @version October 17, 2014
 */
public class BstSizeNode {
    private int value;
    private int size;
    private BstSizeNode parent;
    private BstSizeNode leftChild;
    private BstSizeNode rightChild;
    
    /**
     * Create a new BstNode with the given value, the parent and children references
     * are initialized to null
     * @param value the value this node will hold
     */
    public BstSizeNode(int value){
        this.value = value;
        this.size = 0;
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
     * @return the size of the subtree rooted at this node (min size = 1)
     */
    public int getSize(){
        return this.size;
    }
    
    /**
     * @param size the new size of this subtree
     */
    public void setSize(int size){
        if (size < 1){ throw new IllegalArgumentException("Initialized node cannot have size less than 1");}
        this.size = size;
    }
    
    /**
     * Updates the size of this node to the size of its left subtree plus the size
     * of its right sub tree plus one for itself.  This method is safe to use on
     * a node with no children
     */
    public void updateSize(){
        this.size = getLeftSize() + getRightSize() + 1;
    }
    
    /**
     * Return the size of the left subtree (safe to use if leftChild is null)
     * @return the size of the left subtree
     */
    public int getLeftSize(){
        if (leftChild == null){return 0;}
        return leftChild.size;
    }
    
    /**
     * Return the size of the right subtree (safe to use if rightChild is null)
     * @return the size of the right subtree
     */
    public int getRightSize(){
        if (rightChild == null){return 0;}
        return rightChild.size;
    }
    
    /**
     * @return a handle to this node's parent
     */
    public BstSizeNode getParent(){
        return this.parent;
    }
    
    /**
     * @param parent the new parent of this node
     */
    public void setParent(BstSizeNode parent){
        this.parent = parent;
    }
    
    /**
     * @return the handle to this node's left child
     */
    public BstSizeNode getLeftChild(){
        return this.leftChild;
    }
    
    /**
     * @param leftChild the new left child of this node
     */
    public void setLeftChild(BstSizeNode leftChild){
        this.leftChild = leftChild;
    }
    
    /**
     * @return a handle to the right child of this node
     */
    public BstSizeNode getRightChild(){
        return this.rightChild;
    }
    
    /**
     * @param rightChild the new right child of this node
     */
    public void setRightChild(BstSizeNode rightChild){
        this.rightChild = rightChild;
    }
}
