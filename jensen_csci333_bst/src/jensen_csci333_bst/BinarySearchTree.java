

package jensen_csci333_bst;

/**
 * A class representing a binary search tree. In the case that two node's value
 * are equal the new node will be placed in the right sub tree.
 * @author Ryan Jensen
 * @version October 17, 2014
 */
public class BinarySearchTree {
    private BstNode root;
    private int size;
    
    public class Iterator {
        private BstNode current;
        
        private Iterator(BstNode current){
            this.current = current;
        }
        
        public boolean hasNext(){
            return true;
        }
        
    }
    public BinarySearchTree(){
        this.root = null;
        this.size = 0;
    }
    
    public Iterator getIterator(){
        return new Iterator(this.root);
    }
    
    
}
