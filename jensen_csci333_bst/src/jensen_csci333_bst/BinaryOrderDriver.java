

package jensen_csci333_bst;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

/**
 * Testing for binary search tree hw
 * @author Ryan Jensen
 * @version October 18, 2014
 */
public class BinaryOrderDriver {
    
    
    /**
     * Testing for binary search tree
     * @param args not used
     */
    public static void main(String[] args) {
        Random gen = new Random();

        int size = 20;//size of test case
        int lower = -50;//highest number
        int upper = 50;//lowest number
        int orderStatCases = size/4;
        int failed = 0;//number of failed tests

        //insert,search,deleted testing
        ArrayList<Integer> control = new ArrayList<>();//control
        OrderStatTree bst = new OrderStatTree();//order stat tree
        int[] toFind = new int[(size+3)/4];//the array of things we will search for and find
        int[] toNotFind = new int[(size+3)/4];//the array of things we will search for and not find
        int[] toRemove = new int[(size+2)/4];//the array of things we will remove
        for (int i = 0; i < size; i++){
            int val = gen.nextInt(upper - lower + 1) + lower;//[0, upper-lower+1)+lower=[lower, upper]
            //initialize control and bst
            control.add(val);
            bst.insert(val);
            if (i%4 == 0){
                //every 4 add it to the to find list
                toFind[i/4] = val;
                toNotFind[i/4] = gen.nextInt(upper) + upper + 1;//[0,upper-1]+upper + 1 = [upper+1, 2*upper] 
            }
            if (i%4 == 1){
                //every multiple of 4 plus one add it the toRemove list
                toRemove[i/4] = val;
            }
        }
        
        System.out.println();
        System.out.println();
        System.out.println("Insert, delete, Order, Rank");
        System.out.println("Control Size: " + control.size());
        System.out.println(control);
        System.out.println();
        System.out.println("BST Size: " + bst.getSize());
        System.out.println("In order Traversal");
        System.out.println(bst.inOrderTraversal());
        
        System.out.println();
        Collections.sort(control);
        System.out.println("Control Post Sort");
        System.out.println(control);
        if (control.size() != bst.getSize()){
            System.out.println("*******Sizes are different*******");
            failed++;
        }
        if (!bst.inOrderTraversal().equals(control.toString())){
            System.out.println("********Sorted results are different*****");
            failed++;
        }        
        //remove some
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
        Collections.sort(control);
        for (int i = 0; i < orderStatCases; i++){
            int orderStat = gen.nextInt(bst.getSize())+1;//the order stat to find
            System.out.println("Looking for stat: " + orderStat);
            int realVal = control.get(orderStat-1);//the real value
            System.out.println("Real Value: " + realVal);
            int foundVal = bst.select(orderStat);//the value found in the bst
            System.out.println("Found Value: " + foundVal);
            if (realVal != foundVal){
                System.out.println("****** Select order Failed *******");
                failed++;
            }
            BstSizeNode nodeSelect = bst.selectNode(orderStat);//get the actual node
            int foundRank = bst.rank(nodeSelect);//use it to test the rank function
            System.out.println("Found rank: " + foundRank);
            if (foundRank != orderStat){
                System.out.println("****** Rank test Failed ******");
                failed++;
            }
            System.out.println("---------------");
            System.out.println();
        }
        
        
        
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println("Test Summary, Failed: " + failed);
    }
    
}
