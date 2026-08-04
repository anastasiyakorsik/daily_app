package korsik.daily.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Formatter;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.BinaryOperator;

public class Expense {

    private final Long id;
    private final BigDecimal amount; //todo прочитать про double vs bigdeciaml
    private final ExpenseCategory category;
    private final String comment;
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

    public Optional<ExpenseCategory> getCategory() {
        return Optional.ofNullable(category);
    }

    public Optional<String> getComment() {
        return Optional.ofNullable(comment);
    }

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
        private LocalDateTime date = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
        private Currency currency = defaultCurrency;

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
            this.date = Objects.requireNonNull(date.truncatedTo(ChronoUnit.SECONDS), "date must be set");
            return this;
        }

        public Builder currency(Currency currency){
            this.currency = Objects.requireNonNull(currency, "currency must be set");
            return this;
        }

        public Expense build(){
            return new Expense(this);
        }

    }

    public Expense withComment(String comment){
        if (comment == null){
            throw new NullPointerException("comment must be set");
        }
        if (comment.isEmpty()){
            throw new IllegalArgumentException("comment can not be empty");
        }
        if (comment.isBlank()){
            throw new IllegalArgumentException("comment can not be blank");
        }
        return Expense.builder()
                .id(this.id)
                .amount(this.amount)
                .category(this.category)
                .comment(comment)
                .date(this.date)
                .currency(this.currency)
                .build();

    }

    public Expense withCategory(ExpenseCategory category){
        if (category == null){
            throw new NullPointerException("category must be set");
        }
        return Expense.builder()
                .id(this.id)
                .amount(this.amount)
                .category(category)
                .comment(this.comment)
                .date(this.date)
                .currency(this.currency)
                .build();

    }

    public static void setDefaultCurrency(Currency currency){
        defaultCurrency = Objects.requireNonNull(currency, "default currency must not be null");
    }

    public static Currency getDefaultCurrency(){
        return defaultCurrency;
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
