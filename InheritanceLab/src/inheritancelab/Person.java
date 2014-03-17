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
public class Person {
    protected String name;
    protected int age;
    
    public Person(String name, int age){
        this.name = name;
        this.age = age;
    }
    
    
    public String getName(){
        return this.name;
    }
    
    
    public void setName(String name){
        this.name = name;
    }
    
    
    public int getAge(){
        return age;
    }
    
    
    public void setAge(int age){
        this.age = age;
    }
    
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
    
    @Override
    public String toString(){
        return String.format("Name = %s age = %d", name, age);
    }
}
