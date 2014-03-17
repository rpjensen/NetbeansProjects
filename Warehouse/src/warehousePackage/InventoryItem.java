package warehousePackage;



/**
 * This class represents an inventory item.
 * @author Ryan Jensen, Chudier Pal
 * @version Feb 3, 2014
 */
public class InventoryItem {
        private int unitNumber;
        private String description;
        private double price;
        private int quantitiy;
    
/**
 * This is the default constructor for an inventory item.
 * @param unitNumber the unique identifier for the item
 * @param description the description of the item
 * @param price the price of the item
 * @param quantitiy the number of this item in stock
 */
    public InventoryItem(int unitNumber, String description, double price, int quantitiy){
        this.unitNumber = unitNumber;
        this.description = description;
        this.price = price;
        this.quantitiy = quantitiy;
    }

/**
 * Converts an Inventory Item to a string.
 * @return a string representation of the object.
 */
        @Override
    public String toString(){
        String returnString = "Item #" + unitNumber + ": " + description + "\n";
        returnString += String.format("Price: $%.2f Number in stock: %d", price, quantitiy);
        return returnString;
    }
        
/**
 * Return the unique unit number of the item.
 * @return the unit number
 */
    public int getUnitNumber(){
        return this.unitNumber;
    }
    
/**
 * Return the description of the item.
 * @return the description string
 */
    public String getDescription(){
        return this.description;
    }
    
/**
 * Returns the price of the object.
 * @return the price
 */
    public double getPrice(){
        return this.price;
    }
    
/**
 * Returns the number of this item in inventory.
 * @return the quantity
 */
    public int getQuantity(){
        return this.quantitiy;
    }
    
/**
 * Set a new price for the item.
 * @param price the new price
 */
    public void setPrice(double price){
        this.price = price;
    }
    
/**
 * Set a new quantity of the item.
 * @param quantitiy the new quantity
 */
    public void setQuantitiy(int quantitiy){
        this.quantitiy = quantitiy;
    }
    
/**
 * @param args 
 */
    public static void main(String[] args){
        InventoryItem item = new InventoryItem(417497, "The best beef soup in town!", 3.00, 4);
        System.out.println(item);
        item.setPrice(20.00);
        item.setQuantitiy(0);
        System.out.println(item);
        System.out.printf("%d %s %.2f %d\n", item.getUnitNumber(), item.getDescription(), item.getPrice(), item.getQuantity());
        
    }
    
    
    
}
