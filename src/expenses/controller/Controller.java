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
    private Person buyer;
    private Expenses expense;
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
        
        expense = new Expenses();
        expense.setDate(new Date());
        expense.setPurchase(view.getAmountField());
            
        if (db.registerNewPurchase(firstName, lastName, expense)) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
            
            String text = String.format("Buyer: %s %s%nPrice: %.2f%nDate: %s",
                    firstName, lastName, expense.getPurchase(), 
                    sdf.format(expense.getDate()));
            
            view.setInformationArea(text);
        } else {
            final String error = "Klarte ikke lagre ny oppføring.";
            view.setInformationArea(error);
        }  
    }
    
    public void total() {
        view.setInformationArea("");
        Calendar now = Calendar.getInstance();
        Integer year = now.get(Calendar.YEAR);
        
        Iterable<Person> buyers = db.getTotal()::iterator;
        
        StringBuilder output = new StringBuilder();
        output.append("Total oversikt\n".toUpperCase());
        for (Person p : buyers) {
            double sum = 0;
            output.append(p.getFirstName())
                    .append(" ")
                    .append(p.getLastName())
                    .append("\n");
            
            Iterator<Expenses> e = p.getExpenses().iterator();
            while (e.hasNext())
                sum += e.next().getPurchase();
            
            output.append("Totale kostnader hittil i året ")
                    .append(year)
                    .append("\n");
            
            DecimalFormat df = new DecimalFormat(".##");
            output.append(df.format(sum))
                    .append(" ")
                    .append("kroner.\n\n");
        } // end for-loop
        view.setInformationArea(output.toString());
    } // end method total
    
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
