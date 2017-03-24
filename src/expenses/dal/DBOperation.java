/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package expenses.dal;

import entities.Expenses;
import entities.Person;
import expenses.util.HibernateUtil;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.hibernate.JDBCException;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author Stian
 */
public class DBOperation {
    private Session session;
    
    /**
     * Starting a session to be used for transactions.
     */
    public DBOperation() {
        session = HibernateUtil.getSessionFactory().openSession();
    }
    
    /**
     * Register a new purchase.
     * @param buyer the purchase.
     * @param expens purchase amount.
     */
    public boolean registerNewPurchase(String firstName, String lastName, Expenses expens) {
        boolean result = true;
        try {
            Person person = searchPerson(firstName, lastName);
            if (person == null) {
                person = new Person();
                person.setFirstName(firstName);
                person.setLastName(lastName);
                session.save(person);
            }
            expens.setBuyer(person);
            person.getExpenses().add(expens);
            
            session.save(expens);
            session.getTransaction().commit();
        } catch (JDBCException e) {
            result = false;
        } finally {
            //session.close();
            return result;
        }
    }
    
    /**
     * Searching for a person.
     * @param buyer
     * @return the person if already exists or null.
     */
    private Person searchPerson(String firstName, String lastName) {
        session.beginTransaction();
        
        Query query = session.createQuery("from Person");
        List<Person> persons = query.list();
        Iterator<Person> iter = persons.iterator();
        
        Person foundPerson = null;
        while (iter.hasNext()) {
            Person p = (Person) iter.next();
            if (p.getFirstName().equals(firstName) && p.getLastName().equals(lastName))
                foundPerson = p;
        }
        session.close();
        return foundPerson;
    }
    
    // hent alle kjøp: Kjøpers antall, kjøpers totalsum
    public List<Person> getTotal() {
        session.beginTransaction();
        Query query = session.createQuery("from Person");
        List<Person> list = query.list();
        
        list.forEach(p -> {
            
            System.out.println("Buyer: " + p.getFirstName());
            Set<Expenses> ex =  p.getExpenses();
            ex.forEach(e -> {
                System.out.println(e.getPurchase());
            });
        });
        return null;
    }
}
