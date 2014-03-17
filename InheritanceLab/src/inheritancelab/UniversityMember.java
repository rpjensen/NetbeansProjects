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
public class UniversityMember extends Person{
    protected String idNumber;
    
    public UniversityMember(String name, int age, String idNumber){
        super(name,age);
        this.idNumber = idNumber;
    }
    
    public String getIdNumber(){
        return idNumber;
    }
    
    public void setIdNumber(String idNumber){
        this.idNumber = idNumber;
    }
    
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
}
