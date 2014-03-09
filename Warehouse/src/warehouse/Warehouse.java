/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package warehouse;

import java.util.ArrayList;

/**
 *
 * @author Ryan Jensen
 * @version February 10, 2014
 */
public class Warehouse {
    	private ArrayList<InventoryItem> itemsInStorage;
/**
 * Constructs a new warehouse
 */    
	public Warehouse(){
    	this.itemsInStorage = new ArrayList<InventoryItem>();
   	 
	}
    
/**
 * Adds a new item to the warehouse inventory list
 * @param item the item to be added
 */
	public void addItem(InventoryItem item){
    	itemsInStorage.add(item);
	}
    
/**
 * Returns an item for a given unit number.  Returns null item if not found.
 * @param unitNumber the unit number for the item to search for
 * @return the item with that unit number from the list
 */
	public InventoryItem findItem(int unitNumber){
    	int itemIndex = this.findHelper(unitNumber);
    	if (itemIndex == -1){
        	InventoryItem nullItem = null;
        	return  nullItem;
    	}
    	InventoryItem item = this.itemsInStorage.get(itemIndex);
    	return item;
   	 
	}
   	 
/**
 * Removes an item for a given unit number.
 * @param unitNumber the unit number of the item to remove
 */
	public void removeItem(int unitNumber){
    	int itemIndex = this.findHelper(unitNumber);
    	if (itemIndex >= 0){
        	this.itemsInStorage.remove(itemIndex);
    	}
	}
    
/**
 * Updates the quantity of a given item from the inventory list.
 * @param unitNumber the unit number to search for
 * @param newQuantity the new quantity to change
 */
	public void updateItemQuantitiy(int unitNumber, int newQuantity){
    	int itemIndex = this.findHelper(unitNumber);
    	if (itemIndex >= 0){
        	this.itemsInStorage.get(itemIndex).setQuantitiy(newQuantity);
    	}
	}
    
/**
 * Updates the item price for a given item given its unit number.
 * @param unitNumber the unit number to search for
 * @param newPrice the new price to update
 */
	public void updateItemPrice(int unitNumber, double newPrice){
    	int itemIndex = this.findHelper(unitNumber);
    	if (itemIndex >= 0){
        	this.itemsInStorage.get(itemIndex).setPrice(newPrice);
    	}
	}
/**
 * Returns a string representation of a warehouse inventory.
 * @return the return string
 */
	public String toString(){
    	int index = 0;
    	StringBuilder returnString = new StringBuilder("Warehouse Storage: \n");
    	while (index < this.itemsInStorage.size()){
        	returnString.append(this.itemsInStorage.get(index).toString());
                returnString.append("\n");
        	index++;
    	}
   	 
    	return returnString.toString();
	}
	private int findHelper(int unitNumber){
    	int index = 0;
    	while (index < this.itemsInStorage.size()){
        	if (this.itemsInStorage.get(index).getUnitNumber() == unitNumber){
            	return index;
        	}
        	index ++;
    	}
    	return -1;
	}

}