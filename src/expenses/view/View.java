/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package expenses.view;

import entities.Expenses;
import entities.Person;
import expenses.controller.Controller;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 *
 * @author Stian Reistad Røgeberg
 */
public class View {
    private static final int HEIGHT = 500;
    private static final int WIDTH = 700;
    
    private TextField firstName;
    private final TextField lastName;
    private final TextField amount;
    private final Label buyerLabel;
    private final Label amountLabel;
    private final TextArea information;
    private final Button register;
    private final Button exit;
    private final Button total;
    private final ComboBox dropdownList;
    
    private final Controller controller;
    
    public View(Stage stage) {
        stage.setTitle("Registrering av handlesum");
        GridPane root = new GridPane();
        root.setAlignment(Pos.TOP_CENTER);
        root.setPadding(new Insets(10, 10, 10, 10));
        root.setVgap(5);
        root.setHgap(5);
        
        buyerLabel = new Label("Buyer: ");
        GridPane.setConstraints(buyerLabel, 0, 0);
        
        firstName = new TextField();
        firstName.setPromptText("Enter your first name");
        GridPane.setConstraints(firstName, 1, 0);
        
        
        dropdownList = new ComboBox();
        //GridPane.setConstraints(dropdownList, 1, 0);
        
        lastName = new TextField();
        lastName.setPromptText("Enter your last name");
        GridPane.setConstraints(lastName, 2, 0);
        
        amountLabel = new Label("Amount: ");
        GridPane.setConstraints(amountLabel, 0, 1);
        
        amount = new TextField();
        GridPane.setConstraints(amount, 1, 1);// hente verdier og sende t
        register = new Button("Register");
        GridPane.setConstraints(register, 0, 2);
        
        exit = new Button("Exit");
        GridPane.setConstraints(exit, 1, 2);
        
        total = new Button("Total");
        GridPane.setConstraints(total, 2, 2);
        
        information = new TextArea();
        GridPane.setConstraints(information, 0, 3, 3, 3);
        
        root.getChildren().addAll(buyerLabel, firstName, lastName, 
                amountLabel, amount, register, exit, total, information);
        
        controller = new Controller(this);
        //fillDropdown();
        
        Scene scene = new Scene(root, WIDTH, HEIGHT);
        scene.getStylesheets().add("./expenses/styles/styles.css");
        stage.setScene(scene);
        
    }
    
    public String getFirstNameField() {
        return firstName.getText();
    }
    
    public String getLastNameField() {
        return lastName.getText();
    }
    
    public double getAmountField() {
        return Double.parseDouble(amount.getText());
    }
    
    private boolean checkAmount(String input) {
        String regex = "^(([0-9]+)[,]([0-9]{2}))";
         return input.matches(regex);
         
    }
    
    public Button getRegisterButton() {
        return register;
    }
    
    public Button getExitButton() {
        return exit;
    }
    
    public Button getTotalButton() {
        return total;
    }
    
    public void setInformationArea(String text) {
        information.setText(text);
    }
    
    private void fillDropdown() {
        List<Person> list = controller.users();
        list.stream().forEach(p -> {
            String name = p.getFirstName() + " " + p.getLastName();
            dropdownList.getItems().add(name);
        });
        
        /*
        while (iter.hasNext()) {
            Person p = (Person) iter.next();
            String name = p.getFirstName() + " " + p.getLastName();
            dropdownList.getItems().add(name);
        }*/
    }
    
    public void formatTotal(Iterable<Person> buyers) {
        setInformationArea("");
        Calendar now = Calendar.getInstance();
        Integer year = now.get(Calendar.YEAR);
        
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
        setInformationArea(output.toString());
    }

    public void setBuyerInfo(String firstName, 
            String lastName, double amount, boolean ok) {
        clearInputFields();
        if (ok) {
            SimpleDateFormat sdf =new SimpleDateFormat();
            String text = String.format("Buyer: %s %s%nPrice: %.2f%nDate: %s",
                    firstName, lastName, amount, 
                    sdf.format(new Date()));
            setInformationArea(text);
        } else {
            final String error = "Klarte ikke lagre ny oppføring.";
            setInformationArea(error); 
        }  
    }
    
    public void clearInputFields() {
        firstName.setText("");
        lastName.setText("");
        amount.setText("");
    }
}
