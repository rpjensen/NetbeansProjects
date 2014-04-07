
package exam2_practical;

/**
 * A class that extends employee and adds fields for hourly wage and hours worked.
 * @author Ryan Jensen
 * @version March 31, 2014
 */
public class NonExempt extends Employee{
    private int hourlyWage;
    private int hoursWorked;
    
    /**
     * Convenience initializer that creates an employee with a name and id number
     * but sets the hourly wage and hours worked fields to zero. Use this if you
     * do not know what you will pay the new employee or how long they will work
     * per week.
     * @param name the employee's name
     * @param idNum the employee's id number
     */
    public NonExempt(String name, String idNum){
        this(name, idNum, 0, 0);
    }
    
    /**
     * Default initializer: Creates a new employee with all four fields filled in
     * with the given values.
     * @param name the employee's name
     * @param idNum the employee's id number
     * @param hourlyWage the hourly wage of the employee
     * @param hoursWorked the number of hours the employee will work per week
     */
    public NonExempt(String name, String idNum, int hourlyWage, int hoursWorked){
        super(name, idNum);
        this.hourlyWage = hourlyWage;
        this.hoursWorked = hoursWorked;
    }
    
    /**
     * @return the current hourly wage of the employee
     */
    public int getHourlyWage(){
        return this.hourlyWage;
    }
    
    /**
     * Set a new hourly wage for the employee.
     * @param hourlyWage the new wage
     */
    public void setHourlyWage(int hourlyWage){
        this.hourlyWage = hourlyWage;
    }
    
    /**
     * @return the current number of hours the employee has worked per week
     */
    public int getHoursWorked(){
        return this.hoursWorked;
    }

    /**
     * Set a new number of hours the employee works in a week
     * @param hoursWorked 
     */
    public void setHoursWorked(int hoursWorked){
        this.hoursWorked = hoursWorked;
    }
    
    /**
     * Overrides the abstract method of the employee super class and returns
     * the hourly wage times the number of hours worked.
     * @return the total pay
     */
    @Override
    public int getPay(){
        return this.hourlyWage * this.hoursWorked;
    }
    
    /**
     * Test's the equality against another employee
     * @param obj the other employee
     * @return true if equal, else false
     */
    @Override
    public boolean equals(Object obj){
        if (this == obj){return true;}
        if (!(obj instanceof NonExempt)){return false;}
        NonExempt emp = (NonExempt)obj;
        return this.hourlyWage == emp.hourlyWage && this.hoursWorked == emp.hoursWorked && this.name.equals(emp.name) && this.idNum.equals(emp.idNum);
    }
    
    /**
     * Returns a string representation of an employee in the form:
     * "Name: , IDNum: , Hourly Wage: , Hours Worked: "
     * @return the string representation of the employee
     */
    @Override
    public String toString(){
        return String.format("Name: %s, IDNum: %s, Hourly Wage: %d, Hours Worked: %d", this.name, this.idNum, this.hourlyWage, this.hoursWorked);
                
    }
}
