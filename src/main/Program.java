/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import entities.Expenses;
import entities.Person;
import expenses.util.HibernateUtil;
import expenses.view.View;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javafx.application.Application;
import javafx.stage.Stage;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

/**
 *
 * @author Stian Reistad Røgeberg
 */
public class Program extends Application {
    @Override
    public void start(Stage stage) {
        View view = new View(stage);
        stage.show();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
    
    static void createDB() {
        Configuration config = new Configuration();
        config.configure("hibernate.cfg.xml");
        StandardServiceRegistryBuilder ssrb = 
                new StandardServiceRegistryBuilder()
                        .applySettings(config.getProperties());
        
        SessionFactory factory = config.buildSessionFactory(ssrb.build());
        
        
    }
    static void insert() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        
        Person person = finnPerson("Donald", "Duck");
        if (person == null) return;
        
        /*Person person = new Person();
        person.setFirstName("Donald");
        person.setLastName("Duck");
        session.save(person);*/
        
        Expenses expenses = new Expenses();
        expenses.setDate(new Date());
        expenses.setPurchase(404.50);
        
        expenses.setBuyer(person);
        person.getExpenses().add(expenses);
        
        session.save(expenses);
        session.getTransaction().commit();
        System.out.println("Done");
    }
    
    static Person finnPerson(String firstName, String lastName) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        
        Query query = session.createQuery("from Person");
        List<Person> persons = query.list();
        Iterator<Person> iter = persons.iterator();
        
        Person foundPerson = null;
        while (iter.hasNext()) {
            Person p = iter.next();
            if (p.getFirstName().equals(firstName) && p.getLastName().equals(lastName))
                foundPerson = p;
        }
        return foundPerson;
    }
}
