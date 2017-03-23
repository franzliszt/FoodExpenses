/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import entities.Expenses;
import entities.Person;
import expenses.util.HibernateUtil;
import java.util.Date;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

/**
 *
 * @author Stian
 */
public class Program {
    public static void main(String[] args) {
        createDB();
        insert();
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
        
        Person person = new Person();
        person.setFirstName("Donald");
        person.setLastName("Duck");
        session.save(person);
        
        Expenses expenses = new Expenses();
        expenses.setDate(new Date());
        expenses.setPurchase(404.50);
        
        expenses.setBuyer(person);
        person.getExpenses().add(expenses);
        
        session.save(expenses);
        session.getTransaction().commit();
        System.out.println("Done");
    }
}
