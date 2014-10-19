/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package jensen_csci333_bst;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * Testing for binary search tree hw
 * @author Ryan Jensen
 * @version October 18, 2014
 */
public class BinarySearchDriver {
    
    public void bstSort(int[] array){
        BinarySearchTree tree = new BinarySearchTree();
        for (int val : array){
            tree.insert(val);
        }
        System.out.println(tree.inOrderTraversal());
    }
    
    /**
     * Testing for binary search tree
     * @param args not used
     */
    public static void main(String[] args) {
        Random gen = new Random();
        int size = 20;
        int lower = -50;
        int upper = 50;
        int failed = 0;
        
        ArrayList<Integer> control = new ArrayList<>();
        BinarySearchTree bst = new BinarySearchTree();
        int[] toFind = new int[(size+3)/4];
        int[] toNotFind = new int[(size+3)/4];
        int[] toRemove = new int[(size+2)/4];
        for (int i = 0; i < size; i++){
            int val = gen.nextInt(upper - lower + 1) + lower;//[0, upper-lower+1)+lower=[lower, upper]
            control.add(new Integer(val));
            bst.insert(val);
            if (i%4 == 0){
                toFind[i/4] = val;
                toNotFind[i/4] = gen.nextInt(upper) + upper + 1;//[0,upper-1]+upper + 1 = [upper+1, 2*upper] 
            }
            if (i%4 == 1){
                toRemove[i/4] = val;
            }
        }
        System.out.println("Control Size: " + control.size());
        System.out.println(control);
        System.out.println();
        System.out.println("BST Size: " + bst.getSize());
        System.out.println("Preorder Traversal");
        System.out.println(bst.preOrderTraversal());
        System.out.println("Postorder Traversal");
        System.out.println(bst.postOrderTraversal());
        System.out.println("In order Traversal");
        System.out.println(bst.inOrderTraversal());
        
        System.out.println();
        Collections.sort(control);
        System.out.println("Post Sort");
        System.out.println(control);
        if (control.size() != bst.getSize()){
            System.out.println("*******Sizes are different*******");
            failed++;
        }
        if (!bst.inOrderTraversal().equals(control.toString())){
            System.out.println("********Sorted results are different*****");
            failed++;
        }        
        
        for (int val : toRemove){
            control.remove(new Integer(val));
            bst.delete(val);
        }
        
        System.out.println();
        System.out.println("Size: " + bst.getSize());

        System.out.println("In order Traversal");
        System.out.println(bst.inOrderTraversal());
        
        System.out.println();
        Collections.sort(control);
        System.out.println("Control Size: " + control.size());
        System.out.println(control);
        if (control.size() != bst.getSize()){
            System.out.println("*******Sizes are different*******");
            failed++;
        }
        if (!bst.inOrderTraversal().equals(control.toString())){
            System.out.println("********Sorted results are different*******");
            failed++;
        }
        System.out.println();
        System.out.println("Searching for values in the list");
        for (int val : toFind){
            System.out.println("Value: " + val);
            if (bst.search(val) == null){
                System.out.println("******Didn't find " + val + "********");
                failed++;
            }
        }
        System.out.println();
        System.out.println("Searching for values not in the list");
        for (int val : toNotFind){
            System.out.println("Value: " + val);
            if (bst.search(val) != null){
                System.out.println("******Did find " + val + "********");
                failed++;
            }
        }
        
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println("Test Summary, Failed: " + failed);
    }
    
}
