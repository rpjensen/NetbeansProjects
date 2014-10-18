

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
    
    public BinarySearchTree(){
        this.root = null;
        this.size = 0;
    }
    
    public BstNode search(int value){
        return search(value, root);
    }
    
    private BstNode search(int value, BstNode start){
        if (start == null){return null;}
        if (value == start.getValue()){return start;}
        if (value < start.getValue()){return search(value, start.getLeftChild());}
        else {return search(value, start.getRightChild());}
    }
    
    public BstNode maximum(){
        return maximum(root);
    }
    
    private BstNode maximum(BstNode start){
        if (start == null){return null;}
        while (start.getRightChild() != null){
            start = start.getRightChild();
        }
        return start;
    }
    
    public BstNode minimum(){
        return maximum(root);
    }
    
    private BstNode minimum(BstNode start){
        if (start == null){return null;}
        while (start.getLeftChild() != null){
            start = start.getLeftChild();
        }
        return start;
    }
    
    public BstNode successor(BstNode current){
        if (current == null){return null;}
        
        if (current.getRightChild() != null){return minimum(current.getRightChild());}
        
        while (current.getParent() != null && current.getParent().getLeftChild() != current){
            current = current.getParent();
        }
        
        return current.getParent();
    }
    
    public BstNode predesucessor(BstNode current){
        if (current == null){return null;}
        
        if (current.getLeftChild() != null){return maximum(current.getLeftChild());}
        
        while (current.getParent() != null && current.getParent().getRightChild() != current){
            current = current.getParent();
        }
        
        return current.getParent();
    }
    
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
    
    public BstNode delete(int key){
        BstNode deleted = search(key, root);
        if (deleted != null){
            delete(deleted);
        }
        return deleted;
    }
    
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
    
    public String preOrderTraversal(){
        StringBuilder builder = new StringBuilder("[");
        preOrderTraversal(root, builder, true);
        return builder.append("]").toString();
    }
    
    private void preOrderTraversal(BstNode current, StringBuilder builder, boolean first){
        if (current == null){return;}
        
        if (!first){
            builder.append(", ");
        }
        builder.append(current.getValue());
        first = false;
        
        inOrderTraversal(current.getLeftChild(), builder, first);
        inOrderTraversal(current.getRightChild(), builder, first);         
    }
    
    public String postOrderTraversal(){
        StringBuilder builder = new StringBuilder("[");
        postOrderTraversal(root, builder, true);
        return builder.append("]").toString();
    }
    
    private void postOrderTraversal(BstNode current, StringBuilder builder, boolean first){
        if (current == null){return;}
        
        inOrderTraversal(current.getLeftChild(), builder, first);
        inOrderTraversal(current.getRightChild(), builder, first);         
        if (!first){
            builder.append(", ");
        }
        builder.append(current.getValue());
        first = false; 
    }
    
    public String inOrderTraversal(){
        StringBuilder builder = new StringBuilder("[");
        inOrderTraversal(root, builder, true);
        return builder.append("]").toString();
    }
    
    private void inOrderTraversal(BstNode current, StringBuilder builder, boolean first){
        if (current == null){return;}
        
        inOrderTraversal(current.getLeftChild(), builder, first);
        if (!first){
            builder.append(", ");
        }
        builder.append(current.getValue());
        first = false;
        inOrderTraversal(current.getRightChild(), builder, first);
    }
    
    public int[] sort(){
        BstNode current = this.maximum();
        int[] array = new int[this.size];
        for (int i = 0; i < this.size; i++){
            array[i] = current.getValue();
            current = successor(current);
        }
        return array;
    }
    
    
    
    
}
