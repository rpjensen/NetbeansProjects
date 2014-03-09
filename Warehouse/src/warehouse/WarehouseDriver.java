/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package warehouse;

/**
 * A class that tests a warehouse of inventory items.
 * @author Ryan Jensen
 * @version Feb 10, 2014
 */
public class WarehouseDriver {

	/**
 	* @param args the command line arguments
 	*/
	public static void main(String[] args) {
    	Warehouse wh1 = new Warehouse();
    	wh1.addItem(new InventoryItem(1, "Potatoes", 2.99, 5));
    	wh1.addItem(new InventoryItem(2, "lettuce", 1.99, 4));
    	wh1.addItem(new InventoryItem(3, "chocolate", 4.99, 15));
    	wh1.addItem(new InventoryItem(4, "yum yums", .99, 8));
    	wh1.addItem(new InventoryItem(5, "BIC lighters", 1.98, 15));
    	System.out.println(wh1);
    	wh1.removeItem(4);
    	System.out.println("**removed item 4");
    	InventoryItem standalone1 = wh1.findItem(4);
    	System.out.println("**searched for item 4");
    	InventoryItem standalone2 = wh1.findItem(3);
    	System.out.println("**Searched for item 3");
    	wh1.updateItemPrice(5, 0.99);
    	System.out.println("**changed price of 5 to .99");
    	wh1.updateItemQuantitiy(1, 15);
    	System.out.println("**Changed quantitiy of item 1 to 15");
    	if (standalone1 == null){
        	System.out.println("**item 4 not found ie null item");
    	}
    	System.out.println(standalone2);
    	System.out.println("**print found item for unit number 3");
    	System.out.println();
    	System.out.println(wh1);
    	System.out.println("**print updated inventory list");
	}
    
}


