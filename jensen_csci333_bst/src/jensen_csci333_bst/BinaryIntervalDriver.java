

package jensen_csci333_bst;

import java.util.ArrayList;
import java.util.Random;


/**
 * Testing for binary search tree hw
 * @author Ryan Jensen
 * @version October 18, 2014
 */
public class BinaryIntervalDriver {
    
    
    /**
     * Testing for binary search tree
     * @param args not used
     */
    public static void main(String[] args) {
        Random gen = new Random();
        
        int cases = 40;
        int lower = -50;
        int upper = 50;
        BinaryIntervalTree bst = new BinaryIntervalTree();
        ArrayList<Interval> control = new ArrayList<>();
        ArrayList<Interval> notFound = new ArrayList<>();
        
        for (int i = 0; i < cases; i++){
            int val1 = gen.nextInt(upper - lower + 1) + lower;//[0, upper-lower+1)+lower=[lower, upper]
            int val2 = gen.nextInt(upper - val1 + 1) + val1;//[0, upper-val1+1)+val1=[val1, upper]
            Interval interval = new Interval(val1, val2);
            bst.insert(interval);
            if (i % 4 == 0){
                control.add(interval);
            }
        }
        
        for (int i = 0; i < cases/4; i++){
            int val1 = gen.nextInt(upper) + upper + 1;//[0,upper-1]+upper + 1 = [upper+1, 2*upper] 
            int val2 = gen.nextInt(2*upper - val1 + 1) + val1;//[0,2*upper - val1]+val1 = [val1, 2*upper] 
            notFound.add(new Interval(val1, val2)); 
        }
        
        System.out.println(bst.inOrderTraversal());
        
        int counter = 0;
        for (Interval interval : control){
            int med = (interval.upper()+interval.lower()) / 2;
            Interval newInt;
           
            if (counter % 2 == 0){
                
                newInt = new Interval(interval.lower() - Math.abs(interval.lower()/2), med);
            }
            else {
                newInt = new Interval(med, interval.upper() + Math.abs(interval.upper()/2));
            }
            counter++;
            System.out.println("Interval to intersect: " + newInt);

            Interval found = bst.intervalIntersects(newInt).getInterval();

            System.out.println("Interval that was found: " + found);
            System.out.println();
        }
        
        for (Interval interval : notFound){
            System.out.println("Interval to not find: " + interval);
            BstIntNode found = bst.intervalIntersects(interval);
            System.out.println("Interval that was found: " + found);
            System.out.println();
        }
    }
    
}
