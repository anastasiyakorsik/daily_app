package korsik.daily.service;

import korsik.daily.model.Expense;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Currency;
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

    public Collection<Expense> getAllExpenses(){
        return expenses.values();
    }

    public void addExpense(Expense expense){
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
        if (expenses.isEmpty()){
            return List.of();
        }

        return expenses.values().stream()
                .filter(expense -> expense.getAmount().compareTo(amount) == 0)
                .toList();
    }

    public List<Expense> findCheaperThanExpenses(BigDecimal amount){
        if (expenses.isEmpty()){
            return List.of();
        }

        return expenses.values().stream()
                .filter(expense -> expense.getAmount().compareTo(amount) < 0)
                .toList();
    }

    public List<Expense> findMoreExpensiveThanExpenses(BigDecimal amount){
        if (expenses.isEmpty()){
            return List.of();
        }

        return expenses.values().stream()
                .filter(expense -> expense.getAmount().compareTo(amount) > 0)
                .toList();
    }

    public List<Expense> findByExpenseCategoryName(String expenseCategoryName){
        if (expenses.isEmpty()){
            return List.of();
        }

        return expenses.values().stream()
                .filter(expense -> expense.getCategory()
                        .map(expenseCategory -> expenseCategory.getName().equals(expenseCategoryName))
                        .orElse(false))
                .toList();
    }

    public List<Expense> findUncategorizedExpenses(){
        if (expenses.isEmpty()){
            return List.of();
        }

        return expenses.values().stream()
                .filter(expense -> expense.getCategory().isEmpty())
                .toList();
    }

    public List<Expense> findByCommentPart(String commentPart){
        if (expenses.isEmpty()){
            return List.of();
        }

        return expenses.values().stream()
                .filter(expense -> expense.getComment()
                        .map(comment -> comment.contains(commentPart))
                        .orElse(false))
                .toList();
    }

    public List<Expense> findConcreteDayExpenses(LocalDate date){
        if (expenses.isEmpty()){
            return List.of();
        }

        return expenses.values().stream()
                .filter(expense -> expense.getDate().toLocalDate().isEqual(date))
                .toList();
    }

    public List<Expense> findConcreteDayExpenses(){
        if (expenses.isEmpty()){
            return List.of();
        }

        LocalDate today = LocalDateTime.now().toLocalDate();

        return expenses.values().stream()
                .filter(expense -> expense.getDate().toLocalDate().isEqual(today))
                .toList();
    }

    public List<Expense> findByCurrencyExpenses(Currency currency){
        if (expenses.isEmpty()){
            return List.of();
        }

        return expenses.values().stream()
                .filter(expense -> expense.getCurrency().equals(currency))
                .toList();
    }
}
