/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package inheritancelab;

/**
 *
 * @author jensenrp
 */
public class Student extends UniversityMember{
    private double GPA;
    
    public Student(String name, int age, String idNumber, double GPA){
        super(name,age,idNumber);
        this.GPA = GPA;
    }
    
    public double getGPA(){
        return this.GPA;
    }
    
    public void setGPA(double GPA){
        this.GPA = GPA;
    }
    
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
    
    public String toString(){
        return String.format("%s GPA = %2.2f", super.toString(), this.GPA);
    }
}
