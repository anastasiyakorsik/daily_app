package korsik.daily.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Formatter;
import java.util.List;
import java.util.Objects;
import java.util.function.BinaryOperator;

//todo builder
public class Expense {

    private final Long id;
    private final BigDecimal amount; //todo прочитать про double vs bigdeciaml
    private ExpenseCategory category;
    private String comment;
    private final LocalDateTime date;
    private final Currency currency;

    private static Currency defaultCurrency = Currency.RUB;

    public Expense(Builder builder) {
        this.id = builder.id;
        this.amount = builder.amount;
        this.category = builder.category;
        this.comment = builder.comment;
        this.date = builder.date;
        this.currency = builder.currency;
    }

    public Long getId() {
        return id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

//    public Expense withAmount(BigDecimal amount) {
//        return new Expense(
//                this.id,
//                amount,
//                this.category,
//                this.date,
//                this.currency
//        );
//    }

    public ExpenseCategory getCategory() {
        return category;
    }

//    public Expense withCategory(ExpenseCategory category) {
//        return new Expense(
//                this.id,
//                this.amount,
//                category,
//                this.date,
//                this.currency
//        );
//    }

    public String getComment() {
        return comment;
    }

//    public Expense withComment(String comment) {
//        return new Expense(
//                this.id,
//                this.amount,
//                this.category,
//                this.date,
//                comment
//        );
//    }

    public LocalDateTime getDate() {
        return date;
    }

    public Currency getCurrency() {
        return currency;
    }

    public static Builder builder(){
        return new Builder();
    }

    public static class Builder {
        private Long id;
        private BigDecimal amount; //todo прочитать про double vs bigdeciaml
        private ExpenseCategory category;
        private String comment;
        private LocalDateTime date;
        private Currency currency;

        public Builder id(Long id){
            this.id = Objects.requireNonNull(id, "id must not be null");
            return this;
        }

        public Builder amount(BigDecimal amount){
            this.amount = Objects.requireNonNull(amount, "amount must not be null");
            return this;
        }

        public Builder category(ExpenseCategory category){
            this.category = category;
            return this;
        }

        public Builder comment(String comment){
            this.comment = comment;
            return this;
        }

        public Builder date(LocalDateTime date){
            this.date = Objects.requireNonNull(date, "date must not be null");
            return this;
        }

        public Builder currency(Currency currency){
            if (currency == null){
                currency = defaultCurrency;
            }
            this.currency = currency;
            return this;
        }

        public Expense build(){
            return new Expense(this);
        }

    }

    public static void setDefaultCurrency(Currency currency){
        defaultCurrency = Objects.requireNonNull(currency, "default currency must not be null");
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
