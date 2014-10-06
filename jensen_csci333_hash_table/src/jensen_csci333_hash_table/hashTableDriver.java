
package jensen_csci333_hash_table;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
 *
 * @author Ryan
 */
public class hashTableDriver {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        int values = 20;
        ChainedHashTable table = new ChainedHashTable(values);
        OpenAddressedHashTable table2 = new OpenAddressedHashTable(values);
        ArrayList<Integer> control = new ArrayList<>();
        
        Random gen = new Random();
        Integer[] toRemove = new Integer[(values + 3) / 4];
        Integer[] toFind = new Integer[(values + 2) / 4];
        int maxNum = 100;
        int minNum = -100;
        for (int i = 0; i < values; i++){
            Integer value = gen.nextInt(maxNum - minNum + 1) + minNum;//[0,maxNum-minNum + 1)+minNum = [minNum, maxNum + 1) = [minNum, maxNum]
            table.insert(value);
            table2.insert(value);
            control.add(value);
            
            if (i % 4 == 0){
                toRemove[i / 4] = value;
            }
            if (i % 4 == 1){
                toFind[i / 4] = value;
            }
        }
        System.out.println("Print Table Test");
        table.printTable();
        System.out.println();
        table2.printTable();
        System.out.println();
        System.out.println();
        
        System.out.println("Filled Hash Table");
        System.out.println("Control " + control);        
        System.out.println("Table1: " + table);
        System.out.println("Table2: " + table2);
        
        for (Integer value : toRemove){
            table.delete(value);
            table2.delete(value);
            control.remove(value);
        }
        System.out.println();
        System.out.printf("Deleted %d values: %s\n", toRemove.length, Arrays.toString(toRemove));
        System.out.println("Control " + control);        
        System.out.println("Table1: " + table);
        System.out.println("Table2: " + table2);
        
        Integer[] toNotFind = new Integer[5];
        for (int i = 0; i < toNotFind.length; i++){
            toNotFind[i] = gen.nextInt(maxNum) + maxNum + 1;//[0,maxNum-1]+maxNum + 1 = [maxNum+1, 2*maxNum] 
        }
        
        int table1Failed = 0;
        int table2Failed = 0;
        
        System.out.printf("\nSearching for values in the list\n");
        for (Integer value : toFind){
            System.out.println("Value: " + value);
            if (table.search(value) != null){
                System.out.println("Table1 Passed => found: " + value);
            }
            else {
                System.out.println("*****Table1 Failed*****");
                table1Failed++;
            }
            
            if (table2.search(value) != null){
                System.out.println("Table2 Passed => found: " + value);
            }
            else {
                System.out.println("*****Table2 Failed*****");
                table2Failed++;
            }  
        }
        
        System.out.printf("\nSearching for values not in the list\n");
        for (Integer value : toNotFind){
            System.out.println("Value: " + value);
            Integer result = table.search(value);
            if (result == null){
                System.out.println("Table1 Passed => found: null");
            }
            else {
                System.out.println("*****Table1 Failed***** Found: " + result);
                table1Failed++;
            }
            
            result = table2.search(value);
            if (result == null){
                System.out.println("Table2 Passed => found: null");
            }
            else {
                System.out.println("*****Table2 Failed***** Found: " + result);
                table2Failed++;
            }
            
            

        }
        
        System.out.println();
        System.out.println("--------------------");
        System.out.printf("Summary: table1Failed: %d, table2Failed: %d \n", table1Failed, table2Failed);        
        
    }
}
