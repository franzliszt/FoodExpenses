/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

/**
 *
 * @author Stian Reistad Røgeberg
 */
@Entity
@Table(name = "person")
public class Person implements java.io.Serializable {
    private Integer id;
    private String firstName;
    private String lastName;
    private Set<Expenses> expenses = new HashSet<>(0);
    
    public Person(){}

    public Person(String fname, String lname) {
        firstName = fname;
        lastName = lname;
    }
    
    public Person(String fname, String lname, Set<Expenses> expenses) {
        firstName = fname;
        lastName = lname;
        this.expenses = expenses;
    }

    @Id
    @GeneratedValue(generator = "increment", strategy = IDENTITY)
    @GenericGenerator(name="increment", strategy = "increment")
    @Column(name = "PersonId")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "FirstName", nullable = false)
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Column(name = "Lastname", nullable = false)
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    
    @OneToMany(mappedBy = "buyer")
    public Set<Expenses> getExpenses() {
        return expenses;
    }
    
    public void setExpenses(Set<Expenses> e) {
        expenses = e;
    }
}
