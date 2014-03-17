
package warehousePackage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * A class that represents a company invoice of warehouse items.
 * @author Ryan Jensen
 * @version Mar 3, 2014
 */
public class Company {
        private ArrayList<Warehouse> warehouses;
    
/**
 * Default constructor for the Company Class.  Initializes warehouses array list.
 */
    public Company(){
        warehouses = new ArrayList<Warehouse>();
        
    }
    
/**
 * Reads an input file's contents and creates a warehouse object with those inventory items on the 
 * inventory list.
 * @param inFile the file whose contents will be read
 * @return a warehouse inventory list
 * @throws FileNotFoundException thrown when a scanner object attempts to link with the file
 */
    public Warehouse readInventoryFile(File inFile) throws FileNotFoundException{
        Warehouse returnWarehouse = new Warehouse();
        Scanner input = new Scanner(inFile);
        while (input.hasNext()){
            String description = input.nextLine();
            Scanner dataLine = new Scanner(input.nextLine());
            int sku = dataLine.nextInt();
            double price = dataLine.nextDouble();
            int quantity = dataLine.nextInt();
            returnWarehouse.addItem(new InventoryItem(sku,description,price,quantity));
        }
        input.close();
        return returnWarehouse;
    }
    
/**
 * Adds a new warehouse to the company
 * @param warehouse the warehouse to add
 */
    public void addWarehouse(Warehouse warehouse){
        this.warehouses.add(warehouse);
    }
    
/**
 * Prints the total value of all items in each warehouse in the company's list.
 * @param output the print writer hooked up to the desired output file 
 */
    public void printValues(PrintWriter output){
        output.println("Company Inventory Totals:");
        for (int i = 0; i < this.warehouses.size(); i++){
            output.printf("Warehouse #%d total: %15.2f\n", i+1, this.computeValue(warehouses.get(i)));   
        }
    }
    
    private double computeValue(Warehouse warehouse){
        double total = 0;
        for (int i = 0; i < warehouse.numberOfItems(); i++){
            total = total + (warehouse.getItemAtIndex(i).getPrice() * warehouse.getItemAtIndex(i).getQuantity());
        }
        return total;
    }
  
}
