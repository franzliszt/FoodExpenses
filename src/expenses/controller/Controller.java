/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package expenses.controller;

import entities.Person;
import expenses.dal.DBOperation;
import expenses.view.View;
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
    private final Button register;
    private final Button exit;
    private final Button total;
    
    public Controller(View view, Button register, Button total, Button exit) {
        this.view = view;
        this.register = register;
        this.exit = exit;
        this.total = total;
        db = new DBOperation();
        createListeners();
    }
    
    private void newPurchase() {
        String buyer = view.getBuyer();
        double amount = view.getAmountField();
        boolean ok = db.registerNewPurchase(buyer, amount);
        view.setBuyerInfo(buyer, amount, ok);
    }
    
    public void total() {
        Iterable<Person> buyers = db.getTotal()::iterator;
        view.formatTotal(buyers);
    }
    
    public List<Person> users() {
        return db.getPersons();
    }

    private void createListeners() {
        register.setOnAction((ActionEvent e) -> { newPurchase(); });
        exit.setOnAction((ActionEvent e) -> { System.exit(0); });
        total.setOnAction((ActionEvent e) -> { total(); });
    }
}
