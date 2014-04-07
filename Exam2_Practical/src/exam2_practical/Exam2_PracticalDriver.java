

package exam2_practical;

/**
 * Driver for exam 2
 * @author Ryan Jensen
 * @version March 31, 2014
 */
public class Exam2_PracticalDriver {

    /**
     * 
     * @param args Not used
     */
    public static void main(String[] args) {
        NonExempt[] employeeList = new NonExempt[4];
        employeeList[0] = new NonExempt("emp1", "1", 10, 30);
        employeeList[1] = new NonExempt("emp2", "2", 7, 20);
        employeeList[2] = new NonExempt("emp3", "3", 25, 40);
        employeeList[3] = new NonExempt("emp4", "4");//convenience initializer
        
        NonExempt[] employeeList2 = new NonExempt[4];
        employeeList2[0] = new NonExempt("emp1", "1", 10, 30);
        employeeList2[1] = new NonExempt("emp2", "2", 7, 20);
        employeeList2[2] = new NonExempt("emp3", "3", 25, 40);
        employeeList2[3] = new NonExempt("emp4", "4");//convenience initializer
        
        for (NonExempt emp : employeeList){
            System.out.println(emp);
            System.out.printf("Pay: %d, Expected: %d * %d\n", emp.getPay(), emp.getHourlyWage(), emp.getHoursWorked());
        }
        
        System.out.println();
        System.out.println();
        
        for (NonExempt emp1 : employeeList){
            System.out.println(emp1);
            System.out.println("---------Equality Against ---------");
            for (NonExempt emp2 : employeeList2){
                System.out.println(emp2);
                System.out.println(emp1.equals(emp2));
                System.out.println("--------------------");
            }
            System.out.println();
            System.out.println();
        }
        
        System.out.println();
        System.out.println();
        
        for (int i = 0; i < employeeList.length; i++){
            employeeList[i].setHourlyWage(i + 10);
            employeeList[i].setHoursWorked((i + 1) * 10);
        }
                
        for (NonExempt emp : employeeList){
            System.out.println(emp);
            System.out.printf("Pay: %d, Expected: %d * %d\n", emp.getPay(), emp.getHourlyWage(), emp.getHoursWorked());
        }
        
        System.out.println();
        System.out.println();
        
        for (NonExempt emp1 : employeeList){
            System.out.println(emp1);
            System.out.println("---------Equality Against ---------");
            for (NonExempt emp2 : employeeList2){
                System.out.println(emp2);
                System.out.println(emp1.equals(emp2));
                System.out.println("--------------------");
            }
            System.out.println();
            System.out.println();
        }
        
        System.out.println();
        System.out.println();
        
        for (NonExempt emp : employeeList){
            System.out.printf("Name: %s, Id Num %s\n", emp.getName(), emp.getIdNum());
        }
        
    }
    
}
