/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package expenses.dal;

import entities.Expenses;
import entities.Person;
import expenses.util.HibernateUtil;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;
import org.hibernate.JDBCException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author Stian
 */
public class DBOperation {
    private Session session;
    private Transaction trx;
    
    public DBOperation() {
        //TODO:...
    }
    
    /**
     * Register a new purchase.
     * @param buyer
     * @param amount
     * @return 
     */
    public boolean registerNewPurchase(String buyer, double amount) {
       boolean result = true;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            trx = session.beginTransaction();
            Person person = searchPerson(buyer);
            if (person != null) {
                Expenses expens = new Expenses(amount, new Date(), person);
                expens.setBuyer(person);
                person.getExpenses().add(expens);
                session.save(expens);
                trx.commit();
            }
        } catch (JDBCException e) {
            if (trx != null) trx.rollback();
            System.out.println("****" + e.toString());
            result = false;
        } finally {
            session.close();
        }
        return result;
    }
    
    /**
     * Searching for a person.
     * @param buyer
     * @return the person if already exists or null.
     */
    private Person searchPerson(String buyer) {
        Query query = session.createQuery("from Person");
        List<Person> persons = query.list();
        Iterator<Person> iter = persons.iterator();
        
        Person foundPerson = null;
        while (iter.hasNext()) {
            Person p = (Person) iter.next();
            if ((p.getFirstName() + " " + p.getLastName()).equals(buyer))
                foundPerson = p;
        }
        return foundPerson;
    }
    
    
    public Stream<Person> getTotal() {
        session = HibernateUtil.getSessionFactory().openSession();
        Query query = session.createQuery("from Person");
        List<Person> list = query.list();
        //session.close();
        return list.stream();
    }
    
    
    public List<Person> getPersons() {
        session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Query query = session.createQuery("from Person");
       
        List<Person> list = query.list();
        return list;
    } 
}
