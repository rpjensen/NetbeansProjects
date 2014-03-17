
package warehousePackage;

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
 * @return the number of items in storage 
 */
    public int numberOfItems(){
        return this.itemsInStorage.size();
    }
    
/**
 * Returns the inventory item for a given index.  If the index is out of bounds returns null.
 * @param i the index
 * @return the inventory item or null
 */
    public InventoryItem getItemAtIndex(int i){
        if (i >= 0 && i < this.itemsInStorage.size()){
            return this.itemsInStorage.get(i);
        }else{
            return null;
        }
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
            returnString.append("-----------------------------------\n");
            index++;
        }
        return returnString.toString();
    }
    
/**
 * Executes a sequential recursive search of the inventory list for an item with a given id number.
 * @param sku the unit id number
 * @return the item found or null if not found
 */
    public InventoryItem seqSearchRec(int sku){
        int itemIndex = seqSearchRecHelper(sku, 0, itemsInStorage.size()-1);
        if (itemIndex == -1){
            return null;
        }else {
            return itemsInStorage.get(itemIndex);
        }
    }
    
/**
 * Executes a select sort to sort the warehouse inventory by unit id number.
 */
    public void selectSort(){
        for (int i = itemsInStorage.size() - 1; i > 0; i--){
            int largestIndex = 0;
            for (int j = 1; j <= i; j++){
                if (itemsInStorage.get(j).getUnitNumber() > itemsInStorage.get(largestIndex).getUnitNumber()){
                    largestIndex = j;
                }
            }
            swap(i, largestIndex);
        }
    }
    
/**
 * Executes a binary search for a given unit identifier number and returns the item if found
 * @param sku unit id number
 * @return the inventory item or null if not found
 */
    public InventoryItem binSearch(int sku){
        int itemIndex = binSearchHelper(sku, 0, itemsInStorage.size());
        if (itemIndex == -1){
                    return null;
        }else{
            return itemsInStorage.get(itemIndex);
        }
    }
    private int seqSearchRecHelper(int sku, int start, int end){
        if (start > end){
            return -1;
        }
        if (itemsInStorage.get(start).getUnitNumber() == sku){
            return start;
        }else {
            return seqSearchRecHelper(sku, start + 1, end);
        }
    }
    

    private void swap(int i, int j){
        if (i < itemsInStorage.size() && j < itemsInStorage.size()){
            InventoryItem tempItem = itemsInStorage.get(i);
            itemsInStorage.set(i, itemsInStorage.get(j));
            itemsInStorage.set(j, tempItem);
        }
    }
    
    private int binSearchHelper(int sku, int start, int end){
        if (start > end){
            return -1;
        }
        int mid = (start + end) / 2;
        if (itemsInStorage.get(mid).getUnitNumber() == sku){
            return mid;
        }
        if (itemsInStorage.get(mid).getUnitNumber() > sku){
            return binSearchHelper(sku, start, mid - 1);
        }else{
            return binSearchHelper(sku, mid + 1, end);
        }
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
