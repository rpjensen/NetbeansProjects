

package inheritancelab;

import java.util.ArrayList;

/**
 *A class to test Person,UniversityMember,Student,Professor inheritance 
 * @author Ryan Jensen
 * @version March 17, 2014
 */
public class InheritanceLabDriver {

    /**
     * Creates members of all four classes and tests the to string and equality methods
     */
    public static void main(String[] args) {
        ArrayList<Person> personList = new ArrayList<>();
        personList.add(new Person("Adam", 25));
        personList.add(new UniversityMember("Bob", 21, "10110"));
        personList.add(new Student("Cooper", 18, "33432", 2.10));
        personList.add(new Professor("Daniel", 34, "77873", "RRO 314"));
        personList.add(new Professor("Adam", 34, "77873", "RRO 314"));
        personList.add(new Student("Bob", 21, "10110", 3.44));
        personList.add(new UniversityMember("John", 21, "10110"));
        
        for (Person person1 : personList){
            
            System.out.println("--------------------------");
            System.out.println(person1);
            System.out.println("--------------------------");
            for (Person person2 : personList){
                String equalityResult = " >>--doesn't equal--> ";
                if (person1.equals(person2)){
                    equalityResult = " >>--equals--> ";
                }
                System.out.println(person1 + equalityResult + person2);
            }
        } 
    }
    
}
