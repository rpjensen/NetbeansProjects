
package jensen_csci333_bst;

/**
 * A class representing a binary search tree. In the case that two node's interval
 * are equal the new node will be placed in the right sub tree.
 * @author Ryan Jensen
 * @version October 17, 2014
 */
public class BinaryIntervalTree {
    private BstIntNode root;
    private int size;
    
    /**
     * Create a new empty Binary Search Tree
     */
    public BinaryIntervalTree(){
        this.root = null;
        this.size = 0;
    }
    
    /**
     * @return the number of elements stored in the Binary Search Tree
     */
    public int getSize(){
        return this.size;
    }
    
    public BstIntNode intervalIntersects(Interval interval){
        BstIntNode temp = root;
        //while temp is non-null and the interval doesn't intersect the current interval
        while (temp != null && !temp.getInterval().intersects(interval)){
            if (temp.getLeftChild() != null && temp.getLeftChild().getMax() >= interval.lower()){
                temp = temp.getLeftChild();
            }
            else {
                temp = temp.getRightChild();
            }
        }
        return temp;
    }
    
    /**
     * Search the bst for a node that contains the given interval
     * @param interval the interval to search for
     * @return a handle to the node that contains interval, else null
     */
    public BstIntNode search(Interval interval){
        return search(interval, root);
    }
    
    /**
     * Private helper method used for recursive searching
     * @param interval the interval to search for
     * @param start the node to start at (recursion goes down the tree)
     * @return a handle to the node that contains interval, else null
     */
    private BstIntNode search(Interval interval, BstIntNode start){
        if (start == null){return null;}
        if (interval.lower() < start.getInterval().lower()){
            //if lower is less than current definitely go left
            return search(interval, start.getLeftChild());
        }        
        if (interval.lower() == start.getInterval().lower() && interval.upper() == start.getInterval().upper()){
            return start;
        }
        else {
            //includes the case that lower equaled current's lower but the upper value didn't match
            return search(interval, start.getRightChild());
        }
    }
    
    /**
     * Find the maximum interval stored in the tree and return its handle
     * @return the handle to the maximum interval, else null for empty tree
     */
    public BstIntNode maximum(){
        return maximum(root);
    }
    
    /**
     * Private helper method to find the maximum of a subtree rooted at start
     * @param start the root of the subtree to find the max of
     * @return the handle to the maximum interval, else null for empty subtree
     */
    private BstIntNode maximum(BstIntNode start){
        if (start == null){return null;}
        while (start.getRightChild() != null){
            start = start.getRightChild();
        }
        return start;
    }
    
    /**
     * Find the minimum interval stored in the tree and return its handle
     * @return the handle to the minimum interval, else null for empty tree
     */
    public BstIntNode minimum(){
        return maximum(root);
    }
    
    /**
     * Private helper method to find the minimum of a subtree rooted at start
     * @param start the root of the subtree to find the min of
     * @return the handle to the minimum interval, else null for empty subtree
     */
    private BstIntNode minimum(BstIntNode start){
        if (start == null){return null;}
        while (start.getLeftChild() != null){
            start = start.getLeftChild();
        }
        return start;
    }
    
    /**
     * Find the next largest interval after current
     * @param current the node to start at
     * @return the next largest interval after current, null if current is the max
     */
    public BstIntNode successor(BstIntNode current){
        if (current == null){return null;}
        
        if (current.getRightChild() != null){return minimum(current.getRightChild());}
        
        while (current.getParent() != null && current.getParent().getLeftChild() != current){
            current = current.getParent();
        }
        
        return current.getParent();
    }
    
    /**
     * Find the next smallest interval after current
     * @param current the node to start at
     * @return the next smallest interval after current, null if current is the min
     */
    public BstIntNode predesucessor(BstIntNode current){
        if (current == null){return null;}
        
        if (current.getLeftChild() != null){return maximum(current.getLeftChild());}
        
        while (current.getParent() != null && current.getParent().getRightChild() != current){
            current = current.getParent();
        }
        
        return current.getParent();
    }
    
    /**
     * Insert a new integer interval into the tree (this method creates a new node by default)
     * @param interval the interval to insert
     */
    public void insert(Interval interval){
        insert(new BstIntNode(interval));
    }
    
    /**
     * Insert a new node into the tree (this method is destructive to the nodes references)
     * @param node the new node to insert
     */
    public void insert(BstIntNode node){
        BstIntNode parent = null;
        BstIntNode x = root;
        while (x != null){
            parent = x;
            if (node.getInterval().lower() < x.getInterval().lower()){
                x = x.getLeftChild();
            }
            else {
                x = x.getRightChild();
            }
        }
        node.setParent(parent);
        if (parent == null){
            root = node;
        }
        else if (node.getInterval().lower() < parent.getInterval().lower()){
            parent.setLeftChild(node);
        }
        else {
            parent.setRightChild(node);
        }
        size++;
        x = node;//reuse x as our temp variable
        while (x != null){
            //while x not null
            x.updateMax();//update max
            x = x.getParent();//move up the tree
        }
    }
    
    /**
     * Replace current with replacement in the tree, replacement's subtree is retained
     * and current's subtree is lost
     * @param current the node currently in the tree to be transplanted
     * @param replacement the subtree rooted at replacement to put in current's place
     */
    private void transplant(BstIntNode current, BstIntNode replacement){
        if (current.getParent() == null){
            root = replacement;
        }
        else if (current == current.getParent().getLeftChild()){
            current.getParent().setLeftChild(replacement);
        }
        else {
            current.getParent().setRightChild(replacement);
        }
        if (replacement != null){
            replacement.setParent(current.getParent());
        }
    }
    
    /**
     * Remove the first occurrence of a node that contains key as a interval, returns
     * its handle as a convenience
     * @param key the interval of the node to be deleted
     * @return a handle to the deleted node, else null
     */
    public BstIntNode delete(Interval key){
        BstIntNode deleted = search(key, root);
        if (deleted != null){
            delete(deleted);
        }
        return deleted;
    }
    
    /**
     * Convenience method to deleted a given node from the tree (this method preserves
     * the deleted node's subtree)
     * @param node the node to remove
     */
    private void delete(BstIntNode node){
        BstIntNode replacement;
        if (node.getLeftChild() == null){
            transplant(node, node.getRightChild());
            replacement = node.getRightChild();
        }
        else if (node.getRightChild() == null){
            transplant(node, node.getLeftChild());
            replacement = node.getLeftChild();
        }
        else {
            BstIntNode min = minimum(node.getRightChild());
            replacement = min;
            if (min.getParent() != node){
                //case 3b - we move min up to where node was so its parent is the
                //lowest node affected and eventually min will be corrected further up the tree
                replacement = min.getParent();
                transplant(min, min.getRightChild());
                min.setRightChild(node.getRightChild());
                min.getRightChild().setParent(min);
            }
            transplant(node, min);
            min.setLeftChild(node.getLeftChild());
            min.getLeftChild().setParent(min);
        }
        size--;
        //protect against the special case where node was a leaf and it was replaced
        //by null --> start at nodes old parent
        if (replacement == null){
            replacement = node.getParent();
        }
        //while we are still in the tree update the max and go up one more
        while (replacement != null){
            replacement.updateMax();
            replacement = replacement.getParent();
        }
    }
    
    /**
     * Return a string containing a representation of the Pre-Order Traversal
     * @return the string representation (form: "[val, val, ..., val]")
     */
    public String preOrderTraversal(){
        StringBuilder builder = new StringBuilder("[");
        preOrderTraversal(root, builder, true);
        return builder.append("]").toString();
    }
    
    /**
     * Private helper method to build up the pre order traversal string
     * @param current the current node
     * @param builder the string builder containing the result so far
     * @param first true if the first element hasn't been printed else false
     * @return first so the next iteration can use it
     */
    private boolean preOrderTraversal(BstIntNode current, StringBuilder builder, boolean first){
        if (current == null){return first;}
        
        if (!first){
            builder.append(", ");
        }
        builder.append(current.getInterval());
        first = false;
        
        first = preOrderTraversal(current.getLeftChild(), builder, first);
        first = preOrderTraversal(current.getRightChild(), builder, first);
        return first;
    }
    
    /**
     * Return a string containing a representation of the Post-Order Traversal
     * @return the string representation (form: "[val, val, ..., val]")
     */    
    public String postOrderTraversal(){
        StringBuilder builder = new StringBuilder("[");
        postOrderTraversal(root, builder, true);
        return builder.append("]").toString();
    }
    
    /**
     * Private helper method to build up the post order traversal string
     * @param current the current node
     * @param builder the string builder containing the result so far
     * @param first true if the first element hasn't been printed else false
     * @return first so the next iteration can use it
     */    
    private boolean postOrderTraversal(BstIntNode current, StringBuilder builder, boolean first){
        if (current == null){return first;}
        
        first = postOrderTraversal(current.getLeftChild(), builder, first);
        first = postOrderTraversal(current.getRightChild(), builder, first);         
        if (!first){
            builder.append(", ");
        }
        builder.append(current.getInterval());
        first = false;
        return first;
    }
    
    /**
     * Return a string containing a representation of the In-Order Traversal
     * @return the string representation (form: "[val, val, ..., val]")
     */    
    public String inOrderTraversal(){
        StringBuilder builder = new StringBuilder("[");
        inOrderTraversal(root, builder, true);
        return builder.append("]").toString();
    }

    /**
     * Private helper method to build up the in order traversal string
     * @param current the current node
     * @param builder the string builder containing the result so far
     * @param first true if the first element hasn't been printed else false
     * @return first so the next iteration can use it
     */    
    private boolean inOrderTraversal(BstIntNode current, StringBuilder builder, boolean first){
        if (current == null){return first;}
        
        first = inOrderTraversal(current.getLeftChild(), builder, first);
        if (!first){
            builder.append(", ");
        }
        builder.append(current.getInterval());
        first = false;
        first = inOrderTraversal(current.getRightChild(), builder, first);
        return first;
    }
    
    /**
     * Method to return a new array containing the sorted intervals of this Binary
     * Search Tree
     * @return the array of sorted intervals
     */
    public Interval[] sort(){
        BstIntNode current = this.minimum();
        Interval[] array = new Interval[this.size];
        for (int i = 0; i < this.size; i++){
            array[i] = current.getInterval();
            current = successor(current);
        }
        return array;
    }
    
    
    
    
}
