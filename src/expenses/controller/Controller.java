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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;

/**
 *
 * @author Stian Reistad Røgeberg
 */
public class Controller {
    private Person buyer;
    private Expenses expense;
    private final View view;
    private final DBOperation db;
    private final Button reg;
    private final Button exit;
    private final Button total;
    
    public Controller(View view) {
        this.view = view;
        db = new DBOperation();
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
    
    private void newPurchase() {
        String firstName = view.getFirstNameField();
        String lastName = view.getLastNameField();
        
        expense = new Expenses();
        expense.setDate(new Date());
        expense.setPurchase(view.getAmountField());
            
        if (db.registerNewPurchase(firstName, lastName, expense)) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
            
            String text = String.format("Buyer: %s %s%nPrice: %.2f%nDate: %s",
                    firstName, lastName, expense.getPurchase(), 
                    sdf.format(expense.getDate()));
            view.setInformationArea(text);
        }   
    }
    
    private void total() {
        List<Person> total = db.getTotal();
        
        
    }
}
