

package jensen_csci333_bst;

/**
 * A class representing a binary search tree. In the case that two node's value
 * are equal the new node will be placed in the right sub tree.
 * @author Ryan Jensen
 * @version October 17, 2014
 */
public class OrderStatTree {
    private BstSizeNode root;
    private int size;
    
    /**
     * Create a new empty Binary Search Tree
     */
    public OrderStatTree(){
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
     * Return the value of the given orderStat
     * @param orderStat the orderStat to return should be between 1 and size (inclusive)
     * @return the value of the given order statistic
     */
    public int select(int orderStat){
        if (orderStat < 1 || orderStat > this.size){ throw new IllegalArgumentException("Order stat out of range: " + orderStat);}
        return select(root, orderStat).getValue();
    }
    
    /**
     * Return a node with the given orderStat
     * @param orderStat the orderStat should be between 1 and size (inclusive)
     * @return the node that contains the value matching the requested order
     */
    public BstSizeNode selectNode(int orderStat){
        if (orderStat < 1 || orderStat > this.size){ throw new IllegalArgumentException("Order stat out of range: " + orderStat);}
        return select(root, orderStat);
    }
    
    /**
     * Private helper method to get the order statistic of a subtree
     * @param current the root of the subtree
     * @param orderStat the orderStat
     * @return the value of the given orderStat in this subtree
     */
    private BstSizeNode select(BstSizeNode current, int orderStat){
        int currentStat = current.getLeftSize() + 1;
        if (orderStat == currentStat){
            return current;
        }
        if (orderStat < currentStat){
            return select(current.getLeftChild(), orderStat);
        }
        return select(current.getRightChild(), orderStat - currentStat);
    }
    
    /**
     * Compute the rank of the given node in 
     * @param node the node to compute the rank of
     * @return the rank of the given node
     */
    public int rank(BstSizeNode node){
        int rank = node.getLeftSize() + 1;//rank of node's subtree
        BstSizeNode temp = node;//temp variable that will walk up the tree
        while (temp.getParent() != null){
            //if temp is a right child add the parents rank since everything to
            //the left is smaller than temp
            if (temp == temp.getParent().getRightChild()){
                rank = rank + temp.getParent().getLeftSize() + 1;
            }
            temp = temp.getParent();
        }
        return rank;
    }
    
    /**
     * Search the bst for a node that contains the given value
     * @param value the value to search for
     * @return a handle to the node that contains value, else null
     */
    public BstSizeNode search(int value){
        return search(value, root);
    }
    
    /**
     * Private helper method used for recursive searching
     * @param value the value to search for
     * @param start the node to start at (recursion goes down the tree)
     * @return a handle to the node that contains value, else null
     */
    private BstSizeNode search(int value, BstSizeNode start){
        if (start == null){return null;}
        if (value == start.getValue()){return start;}
        if (value < start.getValue()){return search(value, start.getLeftChild());}
        else {return search(value, start.getRightChild());}
    }
    
    /**
     * Find the maximum value stored in the tree and return its handle
     * @return the handle to the maximum value, else null for empty tree
     */
    public BstSizeNode maximum(){
        return maximum(root);
    }
    
    /**
     * Private helper method to find the maximum of a subtree rooted at start
     * @param start the root of the subtree to find the max of
     * @return the handle to the maximum value, else null for empty subtree
     */
    private BstSizeNode maximum(BstSizeNode start){
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
    public BstSizeNode minimum(){
        return maximum(root);
    }
    
    /**
     * Private helper method to find the minimum of a subtree rooted at start
     * @param start the root of the subtree to find the min of
     * @return the handle to the minimum value, else null for empty subtree
     */
    private BstSizeNode minimum(BstSizeNode start){
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
    public BstSizeNode successor(BstSizeNode current){
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
    public BstSizeNode predesucessor(BstSizeNode current){
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
        insert(new BstSizeNode(value));
    }
    
    /**
     * Insert a new node into the tree (this method is destructive to the nodes references)
     * @param node the new node to insert
     */
    private void insert(BstSizeNode node){
        BstSizeNode parent = null;
        BstSizeNode x = root;
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
        x = node;//reuse x as our temp variable
        while (x != null){
            //while x not null
            x.setSize(x.getSize()+1);//update size
            x = x.getParent();//move up the tree
        }
    }
    
    /**
     * Replace current with replacement in the tree, replacement's subtree is retained
     * and current's subtree is lost
     * @param current the node currently in the tree to be transplanted
     * @param replacement the subtree rooted at replacement to put in current's place
     */
    private void transplant(BstSizeNode current, BstSizeNode replacement){
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
    public BstSizeNode delete(int key){
        BstSizeNode deleted = search(key, root);
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
    private void delete(BstSizeNode node){
        BstSizeNode replacement;
        if (node.getLeftChild() == null){
            transplant(node, node.getRightChild());
            replacement = node.getRightChild();
        }
        else if (node.getRightChild() == null){
            transplant(node, node.getLeftChild());
            replacement = node.getLeftChild();
        }
        else {
            BstSizeNode min = minimum(node.getRightChild());
            replacement = min;//case 3a
            if (min.getParent() != node){
                //case 3b -- min ends up above its parent so we need to start at its
                //current parent since its the first one affected by moving min up to node's position
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
        //while we are still in the tree update the size and go up one more
        while (replacement != null){
            replacement.updateSize();
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
    private boolean preOrderTraversal(BstSizeNode current, StringBuilder builder, boolean first){
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
    private boolean postOrderTraversal(BstSizeNode current, StringBuilder builder, boolean first){
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
    private boolean inOrderTraversal(BstSizeNode current, StringBuilder builder, boolean first){
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
        BstSizeNode current = this.minimum();
        int[] array = new int[this.size];
        for (int i = 0; i < this.size; i++){
            array[i] = current.getValue();
            current = successor(current);
        }
        return array;
    }
    
    
    
    
}
