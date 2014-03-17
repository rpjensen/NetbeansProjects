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
public class Professor extends UniversityMember{
    private String officeAddress;
    
    public Professor(String name, int age, String idNumber, String officeAddress){
        super(name, age, idNumber);
        this.officeAddress = officeAddress;
    }
    
    public String getOfficeAddress(){
        return this.officeAddress;
    }
    
    public void setOfficeAddress(String officeAddress){
        this.officeAddress = officeAddress;
    }
    
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
}
