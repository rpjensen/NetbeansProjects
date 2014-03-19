/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package inheritancelab;

/**
 *A class that extends UniversityMember and adds the GPA field
 * @author Ryan Jensen
 * @version March 17, 2014
 */
public class Student extends UniversityMember{
    private double GPA;
    
    /**
     * Creates a new student with the given initialized values.
     * @param name the initial name
     * @param age the initial age
     * @param idNumber the initial idnumber
     * @param GPA the initial gpa
     */
    public Student(String name, int age, String idNumber, double GPA){
        super(name,age,idNumber);
        this.GPA = GPA;
    }
    
    /**
     * @return the student's gpa 
     */
    public double getGPA(){
        return this.GPA;
    }
    
    /**
     * Set the gpa to a new value
     * @param GPA the new GPA
     */
    public void setGPA(double GPA){
        this.GPA = GPA;
    }
    
    /**
     * Tests the equality against another student
     * @param object the other student
     * @return True if equal, else false
     */
    @Override
    public boolean equals(Object object){
        if (this == object){
            return true;
        }
        if (!(object instanceof Student)){
            return false;
        }
        Student student = (Student)object;
        if (!(super.equals(student))){
            return false;
        }
        return this.GPA == student.GPA;
    }
    
    /**
     * Return a string representation of student.  Format "name,age,id,GPA"
     * @return the string representation
     */
    public String toString(){
        return String.format("%s GPA = %2.2f", super.toString(), this.GPA);
    }
}
