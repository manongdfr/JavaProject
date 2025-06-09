/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hr.algebra.godefroyproject.model;

/**
 *
 * @author manongodefroy
 */


public class Director {
    private int id;
    private String firstName;
    private String lastName;

    public Director() {}

    public Director(int id, String firstName, String lastName) {
        this.id        = id;
        this.firstName = firstName;
        this.lastName  = lastName;
    }
    
     //new test
    public Director(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName  = lastName;
    }

    public int    getId()        { return id; }
    public String getFirstName(){ return firstName; }
    public String getLastName() { return lastName; }

    public void setId(int id)               { this.id = id; }
    public void setFirstName(String fn)     { this.firstName = fn; }
    public void setLastName(String ln)      { this.lastName  = ln; }

    /** return full name */
    public String getName() {
        return firstName + " " + lastName;
    }

    @Override
    public String toString() {
        return getName();
    }
}

