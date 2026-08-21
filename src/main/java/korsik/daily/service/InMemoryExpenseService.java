package korsik.daily.service;

import korsik.daily.model.Expense;
import korsik.daily.model.Currency;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public class InMemoryExpenseService {
    private final Map<Long, Expense> expenses;

    public InMemoryExpenseService(Map<Long, Expense> expenses) {
        this.expenses = Objects.requireNonNull(expenses, "expenses may not be null");
    }

    public InMemoryExpenseService() {
        this(new HashMap<>());
    }

    public List<Expense> getAllExpenses(){
        return List.copyOf(expenses.values());
    }

    public void addExpense(Expense expense){

        if (expenses.containsKey(expense.getId())) {
            throw new IllegalArgumentException(String.format("Expense with id %d is already added", expense.getId()));
        }
        expenses.put(Objects.requireNonNull(expense.getId()), expense);
    }

    public void updateExpense(Expense expense){
        expenses.put(Objects.requireNonNull(expense.getId()), expense);
    }

    public Optional<Expense> findExpenseById(Long expenseId){
        if (expenses.containsKey(Objects.requireNonNull(expenseId, "expenseId must be set"))){
            return Optional.ofNullable(expenses.get(expenseId));
        }
        return Optional.empty();
    }

    public boolean removeExpenseById(Long expenseId){
        if (expenses.containsKey(Objects.requireNonNull(expenseId, "expenseId must be set"))){
            expenses.remove(expenseId);
            return true;
        }
        return false;
    }

    //todo maybe round +-
    public List<Expense> findExpensesByAmount(BigDecimal amount){
        return expenses.values().stream()
                .filter(expense -> expense.getAmount().compareTo(amount) == 0)
                .toList();
    }

    public List<Expense> findCheaperThanExpenses(BigDecimal amount){
        return expenses.values().stream()
                .filter(expense -> expense.getAmount().compareTo(amount) < 0)
                .toList();
    }

    public List<Expense> findMoreExpensiveThanExpenses(BigDecimal amount){
        return expenses.values().stream()
                .filter(expense -> expense.getAmount().compareTo(amount) > 0)
                .toList();
    }

    public List<Expense> findByExpenseCategoryName(String expenseCategoryName){
        return expenses.values().stream()
                .filter(expense -> expense.getCategory()
                        .map(expenseCategory -> expenseCategory.getName().equals(expenseCategoryName))
                        .orElse(false))
                .toList();
    }

    public List<Expense> findUncategorizedExpenses(){
        return expenses.values().stream()
                .filter(expense -> expense.getCategory().isEmpty())
                .toList();
    }

    public List<Expense> findByCommentPart(String commentPart){
        return expenses.values().stream()
                .filter(expense -> expense.getComment()
                        .map(comment -> comment.contains(commentPart))
                        .orElse(false))
                .toList();
    }

    public List<Expense> findConcreteDayExpenses(LocalDate date){
        return expenses.values().stream()
                .filter(expense -> expense.getDate().toLocalDate().isEqual(date))
                .toList();
    }

    public List<Expense> findConcreteDayExpenses(){
        LocalDate today = LocalDateTime.now().toLocalDate();

        return expenses.values().stream()
                .filter(expense -> expense.getDate().toLocalDate().isEqual(today))
                .toList();
    }

    public List<Expense> findByCurrencyExpenses(Currency currency){
        return expenses.values().stream()
                .filter(expense -> expense.getCurrency().equals(currency))
                .toList();
    }
}
