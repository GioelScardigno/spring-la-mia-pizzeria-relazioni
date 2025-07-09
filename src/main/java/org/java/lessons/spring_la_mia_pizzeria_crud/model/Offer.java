package org.java.lessons.spring_la_mia_pizzeria_crud.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;

@Entity
@Table(name = "offers")
public class Offer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    
    @NotNull(message = "You need to specify a start-date for the special offer.")
    @PastOrPresent(message = "you cannot start a special offer in the future.")
    private LocalDate offerStartDate;

    @NotNull(message = "You need to specify a start-date for the special offer.")
    @FutureOrPresent(message = "You cannot end a special offer in the past")
    private LocalDate offerEndDate;

    @NotNull(message = "You must specify a title for the special offer.")
    private String title;


    @ManyToOne
    @JoinColumn(name = "pizza_id", nullable=false)
    private Pizza pizza;
    

    public Pizza getPizza() {
        return this.pizza;
    }

    public void setPizza(Pizza pizza) {
        this.pizza = pizza;
    }
    
    
    public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    public LocalDate getOfferStartDate() {
        return this.offerStartDate;
    }
    
    public void setOfferStartDate(LocalDate offerStartDate) {
        this.offerStartDate = offerStartDate;
    }
    
    public LocalDate getOfferEndDate() {
        return this.offerEndDate;
    }
    
    public void setOfferEndDate(LocalDate offerEndDate) {
        this.offerEndDate = offerEndDate;
    }
    
    public String getTitle() {
        return this.title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
}
