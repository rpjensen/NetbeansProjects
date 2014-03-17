

package inheritancelab;

/**
 *Extends the person class and adds an university id number to the functionality
 * @author Ryan Jensen
 * @version March 17, 2014
 */
public class UniversityMember extends Person{
    protected String idNumber;
    
    /**
     * Initializes a new university member
     * @param name the new name
     * @param age the new age
     * @param idNumber the id number for that person
     */
    public UniversityMember(String name, int age, String idNumber){
        super(name,age);
        this.idNumber = idNumber;
    }
    
    /**
     * @return the id number of the person
     */
    public String getIdNumber(){
        return idNumber;
    }
    
    /**
     * @param idNumber the new id number of the person
     */
    public void setIdNumber(String idNumber){
        this.idNumber = idNumber;
    }
    
    /**
     * Tests the equality of two university members
     * @param object the person to test against
     * @return true if equal, else false
     */
    @Override
    public boolean equals(Object object){
        if (this == object){
            return true;
        }
        if (!(object instanceof UniversityMember)){
            return false;
        }
        UniversityMember um = (UniversityMember)object;
        if (!(super.equals(um))){
            return false;
        }
        return idNumber.equals(um.idNumber);
    }
    
    /**
     * Returns a string rep of the person "name, age, idnumber"
     * @return the string representation
     */
    @Override
    public String toString(){
        return String.format("%s id number = %s", super.toString(), idNumber);
    }
}
