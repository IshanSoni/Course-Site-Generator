/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.data;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author isoni
 */
public class Student<E extends Comparable<E>> implements Comparable<E> {
    StringProperty firstName;
    StringProperty lastName;
    StringProperty team;
    StringProperty role;
    
    public Student(String firstName, String lastName, String team, String role) {
        this.firstName = new SimpleStringProperty(firstName);
        this.lastName = new SimpleStringProperty(lastName);
        this.team = new SimpleStringProperty(team);
        this.role = new SimpleStringProperty(role);
    }
    
    public String getFirstName() {
        return firstName.get();
    }
    public String getLastName() {
        return lastName.get();
    }
    public String getTeam() {
        return team.get();
    }
    public String getRole() {
        return role.get();
    }

    public void setFirstName(String firstName) {
        this.firstName.set(firstName);
    }

    public void setLastName(String lastName) {
        this.lastName.set(lastName);
    }

    public void setTeam(String team) {
        this.team.set(team);
    }

    public void setRole(String role) {
        this.role.set(role);
    }
    
    
    
    @Override
    public int compareTo(E o) {
        return firstName.get().compareTo(((Student)o).getFirstName());
    }
}
