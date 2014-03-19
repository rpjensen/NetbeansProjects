

package inheritancelab;

/**
 *Extends the UniversityMember class to include an office address.
 * @author Ryan Jensen
 * @version March 17, 2014
 */
public class Professor extends UniversityMember{
    private String officeAddress;
    
    /**
     * Initializes a new Professor with the given values.
     * @param name the new name of the professor
     * @param age the age of the professor
     * @param idNumber their unique id number
     * @param officeAddress their office address
     */
    public Professor(String name, int age, String idNumber, String officeAddress){
        super(name, age, idNumber);
        this.officeAddress = officeAddress;
    }
    
    /**
     * @return the office address
     */
    public String getOfficeAddress(){
        return this.officeAddress;
    }
    
    /**
     * @param officeAddress the new office address to be set
     */
    public void setOfficeAddress(String officeAddress){
        this.officeAddress = officeAddress;
    }
    
    /**
     * Tests the equality of two professors
     * @param object the professor to test against
     * @return true if equal, else false
     */
    @Override
    public boolean equals(Object object){
        if (this == object){
            return true;
        }
        if (!(object instanceof Professor)){
            return false;
        }
        Professor professor = (Professor)object;
        if (!(super.equals(professor))){
            return false;
        }
        return officeAddress.equals(professor.officeAddress);
    }
    
    /**
     * A to string method to return a string representation of the professor.
     * Format "name, age, id, office address"
     * @return the string representation
     */
    @Override
    public String toString(){
        return String.format("%s Office Address: %s", super.toString(), officeAddress);
    }
}
