/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package expenses.controller;

import entities.Expenses;
import entities.Person;
import expenses.dal.DBOperation;
import expenses.view.View;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;

/**
 *
 * @author Stian Reistad Røgeberg
 */
public class Controller {
    private final View view;
    private final DBOperation db;
    private Button reg;
    private Button exit;
    private Button total;
    
    public Controller(View view) {
        this.view = view;
        db = new DBOperation();
        createListeners();
    }
    
    private void newPurchase() {
        String firstName = view.getFirstNameField();
        String lastName = view.getLastNameField();
        
        double amount = view.getAmountField();
        boolean ok = db.registerNewPurchase(firstName, lastName, 
                amount);
            view.setBuyerInfo(firstName, lastName, amount, ok);
    }
    
    public void total() {
        Iterable<Person> buyers = db.getTotal()::iterator;
        view.formatTotal(buyers);
    }
    
    public List<Person> users() {
        return db.getPersons();
    }

    private void createListeners() {
        reg = view.getRegisterButton();
        reg.setOnAction((ActionEvent t) -> {
            newPurchase();
        });
        
        exit = view.getExitButton();
        exit.setOnAction((ActionEvent e) -> {
            System.exit(0);
        });
        
        total = view.getTotalButton();
        total.setOnAction((ActionEvent e) -> {
           total(); 
        });
    }
}
