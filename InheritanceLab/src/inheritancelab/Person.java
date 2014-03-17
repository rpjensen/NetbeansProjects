

package inheritancelab;

/**
 *A class that represents a person with a name and age.
 * @author Ryan Jensen
 * @version March 17, 2014
 */
public class Person {
    protected String name;
    protected int age;
    
    /**
     * Constructor that initializes the ivars to prams
     * @param name the person's name
     * @param age the person's age
     */
    public Person(String name, int age){
        this.name = name;
        this.age = age;
    }
    
    /**
     * @return the person's name
     */
    public String getName(){
        return this.name;
    }
    
    /**
     * @param name the new name for the person
     */
    public void setName(String name){
        this.name = name;
    }
    
    /**
     * @return the age of the person
     */
    public int getAge(){
        return age;
    }
    
    /**
     * @param age the new age of the person
     */
    public void setAge(int age){
        this.age = age;
    }
    
    /**
     * Tests whether two people are equal
     * @param object the object to test against
     * @return true if equal, else false
     */
    @Override
    public boolean equals(Object object){
        if (this == object){
            return true;
        }
        if (!(object instanceof Person)){
            return false;
        }
        Person p = (Person)object;
        return name.equals(p.name) && age == p.age;
    }
    
    /**
     * Creates a string rep of the person "Name, Age"
     * @return the string representation of the person
     */
    @Override
    public String toString(){
        return String.format("Name = %s age = %d", name, age);
    }
}
