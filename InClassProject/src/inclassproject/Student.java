package inclassproject;

/**
 *
 * @author KH037s
 */
public class Student {
    //instance variables
    private int id;
    private String firstName;
    private String lastName;
    private int exam1, exam2, exam3;
    
    //constructors
    Student(int initId, String first, String last, int ex1, int ex2, int ex3){
        id = initId;
        firstName = first;
        lastName = last;
        exam1 = ex1;
        exam2 = ex2;
        exam3 = ex3;
    }
    Student(){
        
    }//default constructor
    
    
    
    //**********Accessor Methods *********/
    public int getID(){
        return id;
    }//get id
    public String getFirstName(){
        return firstName;
    }//method getFirstName
    public String getLastName(){
        return lastName;
    }//method lastname
    /*************************************/
    //******* Mutator Methods ************/
    public void setId(int initid){ 
    //init = initial
        id = initid;        
    }//set ID
    
    public void setFirstName(String first){
        firstName = first;
    }//set ID
    
    public void setLastName(String last){
        lastName = last;
    }//set ID
   
    
    public boolean equals(Student other){
        return(id == other.id);
        
    }
    @Override
   public String toString(){
       return id + " " + firstName + " " + lastName;
   }
    
    public double examAverage(){
        return (exam1 + exam2 + exam3)/3.0;
         
    }
    public String studentStr(){
        String result = String.format("Id: %d\n", id);
        result += String.format("Name: %s, %s\n",lastName, firstName);
        result += String.format("Exams: %d, %d, %d\n",exam1, exam2, exam3);
        result += String.format("Average: %1.2f\n", examAverage());
        
        return result;
    }

}//class Student

