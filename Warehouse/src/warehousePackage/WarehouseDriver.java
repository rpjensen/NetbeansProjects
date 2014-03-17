
package warehousePackage;

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
        wh1.addItem(new InventoryItem(13, "Potatoes", 2.99, 5));
        wh1.addItem(new InventoryItem(2, "lettuce", 1.99, 4));
        wh1.addItem(new InventoryItem(23, "chocolate", 4.99, 15));
        wh1.addItem(new InventoryItem(47, "yum yums", .99, 8));
        wh1.addItem(new InventoryItem(5, "BIC lighters", 1.98, 15));
        wh1.addItem(new InventoryItem(78, "Easy Mac Mega Kit", 14.99, 10));
        System.out.println("before sort");
        System.out.println(wh1);
        wh1.selectSort();
        System.out.println("after sort");
        System.out.println(wh1);
        System.out.println("Sequentail search rec for #78");
        System.out.println(wh1.seqSearchRec(78));
        System.out.println("BinarySearch for 23");
        System.out.println(wh1.binSearch(23));
        System.out.println();
        System.out.println();
        System.out.println();
        wh1.removeItem(2);
        System.out.println("removed item 2");
        InventoryItem standalone1 = wh1.findItem(2);
        System.out.println("searched for item 4");
        InventoryItem standalone2 = wh1.findItem(13);
        System.out.println("Searched for item 13");
        wh1.updateItemPrice(5, 0.99);
        System.out.println("changed price of 5 to .99");
        wh1.updateItemQuantitiy(1, 15);
        System.out.println("Changed quantitiy of item 1 to 15");
        System.out.println("print found item for unit number 4");
        System.out.println(standalone1);
        System.out.println("print found item for unit number 3");
        System.out.println(standalone2);
        System.out.println();
        System.out.println("print updated inventory list");
        System.out.println(wh1);
        System.out.println();
        System.out.println();
        System.out.println();
        
    }
    
}
