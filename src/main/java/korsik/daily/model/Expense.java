package korsik.daily.model;

import java.util.Date;
import java.util.List;

public class Expense {

    private Long id;
    private Double amount;
    private String category;
    private String comment;
    private Date date;
    private Currency currency;

    public Expense(Long id, Double amount, String category, Date date, Currency currency) {
        this.id = id;
        this.amount = amount;
        this.category = category;
        this.date = date;
        this.currency = currency;
    }

    public Long getId() {
        return id;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    @Override
    public String toString() {
        return "Expense{" +
                "id=" + id +
                ", amount=" + amount +
                ", category='" + category + '\'' +
                ", comment='" + comment + '\'' +
                ", date=" + date +
                ", currency=" + currency +
                '}';
    }
}
