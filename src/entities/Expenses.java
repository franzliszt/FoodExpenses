/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import com.sun.istack.internal.NotNull;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import static javax.persistence.TemporalType.DATE;
import org.hibernate.annotations.GenericGenerator;

/**
 *
 * @author Stian
 */
@Entity
@Table(name = "Expenses")
public class Expenses implements java.io.Serializable {
    private Integer id;
    private double purchase;
    private Date date;
    private Person buyer;
    
    public Expenses() {}
    public Expenses(double purchase, Date date, Person buyer) {
        this.purchase = purchase;
        this.date = date;
        this.buyer = buyer;
    }
    
    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    @Column(name = "Ex_ID")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "Purchase", precision = 2)
    public double getPurchase() {
        return purchase;
    }

    public void setPurchase(double purchase) {
        this.purchase = purchase;
    }

    @Temporal(TemporalType.DATE) @NotNull
    @Column(nullable = false, name = "Date", unique = false)
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Buyer_ID", nullable = false)
    public Person getBuyer() {
        return buyer;
    }

    public void setBuyer(Person buyer) {
        this.buyer = buyer;
    }
}
