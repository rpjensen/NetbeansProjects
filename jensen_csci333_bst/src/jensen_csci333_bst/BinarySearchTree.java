

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
    
    /**
     * Create a new empty Binary Search Tree
     */
    public BinarySearchTree(){
        this.root = null;
        this.size = 0;
    }
    
    /**
     * @return the number of elements stored in the Binary Search Tree
     */
    public int getSize(){
        return this.size;
    }
    
    /**
     * Search the bst for a node that contains the given value
     * @param value the value to search for
     * @return a handle to the node that contains value, else null
     */
    public BstNode search(int value){
        return search(value, root);
    }
    
    /**
     * Private helper method used for recursive searching
     * @param value the value to search for
     * @param start the node to start at (recursion goes down the tree)
     * @return a handle to the node that contains value, else null
     */
    private BstNode search(int value, BstNode start){
        if (start == null){return null;}
        if (value == start.getValue()){return start;}
        if (value < start.getValue()){return search(value, start.getLeftChild());}
        else {return search(value, start.getRightChild());}
    }
    
    /**
     * Find the maximum value stored in the tree and return its handle
     * @return the handle to the maximum value, else null for empty tree
     */
    public BstNode maximum(){
        return maximum(root);
    }
    
    /**
     * Private helper method to find the maximum of a subtree rooted at start
     * @param start the root of the subtree to find the max of
     * @return the handle to the maximum value, else null for empty subtree
     */
    private BstNode maximum(BstNode start){
        if (start == null){return null;}
        while (start.getRightChild() != null){
            start = start.getRightChild();
        }
        return start;
    }
    
    /**
     * Find the minimum value stored in the tree and return its handle
     * @return the handle to the minimum value, else null for empty tree
     */
    public BstNode minimum(){
        return maximum(root);
    }
    
    /**
     * Private helper method to find the minimum of a subtree rooted at start
     * @param start the root of the subtree to find the min of
     * @return the handle to the minimum value, else null for empty subtree
     */
    private BstNode minimum(BstNode start){
        if (start == null){return null;}
        while (start.getLeftChild() != null){
            start = start.getLeftChild();
        }
        return start;
    }
    
    /**
     * Find the next largest value after current
     * @param current the node to start at
     * @return the next largest value after current, null if current is the max
     */
    public BstNode successor(BstNode current){
        if (current == null){return null;}
        
        if (current.getRightChild() != null){return minimum(current.getRightChild());}
        
        while (current.getParent() != null && current.getParent().getLeftChild() != current){
            current = current.getParent();
        }
        
        return current.getParent();
    }
    
    /**
     * Find the next smallest value after current
     * @param current the node to start at
     * @return the next smallest value after current, null if current is the min
     */
    public BstNode predesucessor(BstNode current){
        if (current == null){return null;}
        
        if (current.getLeftChild() != null){return maximum(current.getLeftChild());}
        
        while (current.getParent() != null && current.getParent().getRightChild() != current){
            current = current.getParent();
        }
        
        return current.getParent();
    }
    
    /**
     * Insert a new integer value into the tree (this method creates a new node by default)
     * @param value the value to insert
     */
    public void insert(int value){
        insert(new BstNode(value));
    }
    
    /**
     * Insert a new node into the tree (this method is destructive to the nodes references)
     * @param node the new node to insert
     */
    public void insert(BstNode node){
        BstNode parent = null;
        BstNode x = root;
        while (x != null){
            parent = x;
            if (node.getValue() < x.getValue()){
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
        else if (node.getValue() < parent.getValue()){
            parent.setLeftChild(node);
        }
        else {
            parent.setRightChild(node);
        }
        size++;
    }
    
    /**
     * Replace current with replacement in the tree, replacement's subtree is retained
     * and current's subtree is lost
     * @param current the node currently in the tree to be transplanted
     * @param replacement the subtree rooted at replacement to put in current's place
     */
    private void transplant(BstNode current, BstNode replacement){
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
     * Remove the first occurrence of a node that contains key as a value, returns
     * its handle as a convenience
     * @param key the value of the node to be deleted
     * @return a handle to the deleted node, else null
     */
    public BstNode delete(int key){
        BstNode deleted = search(key, root);
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
    private void delete(BstNode node){
        if (node.getLeftChild() == null){
            transplant(node, node.getRightChild());
        }
        else if (node.getRightChild() == null){
            transplant(node, node.getLeftChild());
        }
        else {
            BstNode min = minimum(node.getRightChild());
            if (min.getParent() != node){
                transplant(min, min.getRightChild());
                min.setRightChild(node.getRightChild());
                min.getRightChild().setParent(min);
            }
            transplant(node, min);
            min.setLeftChild(node.getLeftChild());
            min.getLeftChild().setParent(min);
        }
        size--;
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
    private boolean preOrderTraversal(BstNode current, StringBuilder builder, boolean first){
        if (current == null){return first;}
        
        if (!first){
            builder.append(", ");
        }
        builder.append(current.getValue());
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
    private boolean postOrderTraversal(BstNode current, StringBuilder builder, boolean first){
        if (current == null){return first;}
        
        first = postOrderTraversal(current.getLeftChild(), builder, first);
        first = postOrderTraversal(current.getRightChild(), builder, first);         
        if (!first){
            builder.append(", ");
        }
        builder.append(current.getValue());
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
    private boolean inOrderTraversal(BstNode current, StringBuilder builder, boolean first){
        if (current == null){return first;}
        
        first = inOrderTraversal(current.getLeftChild(), builder, first);
        if (!first){
            builder.append(", ");
        }
        builder.append(current.getValue());
        first = false;
        first = inOrderTraversal(current.getRightChild(), builder, first);
        return first;
    }
    
    /**
     * Method to return a new array containing the sorted values of this Binary
     * Search Tree
     * @return the array of sorted values
     */
    public int[] sort(){
        BstNode current = this.minimum();
        int[] array = new int[this.size];
        for (int i = 0; i < this.size; i++){
            array[i] = current.getValue();
            current = successor(current);
        }
        return array;
    }
    
    
    
    
}
