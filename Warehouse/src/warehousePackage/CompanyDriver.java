
package warehousePackage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * A driver class to test the Company class.
 * @author Ryan Jensen
 * @version Mar 3, 2014
 */
public class CompanyDriver {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Scanner keyboard = new Scanner(System.in);
        String inString;
        ArrayList<File> fileList = new ArrayList<>();
        Company company = new Company();
        
        do{
            System.out.println("Please enter an input file name or q to advance");
            inString = keyboard.next();
            if (!inString.equalsIgnoreCase("q")){
                fileList.add(new File(inString));
            }
        }while (!inString.equalsIgnoreCase("q"));
        
        for (File file: fileList){
            try{
                company.addWarehouse(company.readInventoryFile(file));
            }
            catch(FileNotFoundException e){
                System.out.printf("File Not Found: %s\n", file.getName());
            }
        }
        System.out.println("Please enter the desired output file:");
        String output = keyboard.next();
        try{
            PrintWriter outWriter = new PrintWriter(new File(output));
            company.printValues(outWriter);
            outWriter.close();
        }
        catch(FileNotFoundException e){
            System.out.println("Unknown Error Occured opening " + output);
            System.exit(0);
        }
            
        
    }
    
}
