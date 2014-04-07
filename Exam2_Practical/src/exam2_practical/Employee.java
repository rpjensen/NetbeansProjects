

package exam2_practical;

/**
 * An abstract class that represents a generic employee.
 * @author Ryan Jensen
 * @version March 31, 2014
 */
public abstract class Employee {
    protected String name;
    protected String idNum;
    
    /**
     * Initializes a new Employee.  Can only be called by a subclass since this
     * class is abstract.
     * @param name the employee's name
     * @param idNum the employee's id number
     */
    public Employee(String name, String idNum){
        this.name = name;
        this.idNum = idNum;
    }
    
    /**
     * @return the employee's name
     */
    public String getName(){
        return this.name;
    }
    
    /**
     * @return the employee's id number
     */
    public String getIdNum(){
        return this.idNum;
    }
    
    /**
     * Abstract - We lack information of how to calculate this number
     * @return the employee's net pay
     */
    public abstract int getPay();
}
