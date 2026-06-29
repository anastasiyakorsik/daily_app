package korsik.daily.model;

import java.util.Objects;

//todo: create parent class for categorization entities -> Label and ExpenseCategory
public class ExpenseCategory {
    private final Long id;
    private final String name;
    private ExpenseCategoryColor color;
    private final boolean custom;

    private static final int MAX_EXPENSE_CATEGORY_NAME_LENGTH = 120;

    public ExpenseCategory(Long id, String name, ExpenseCategoryColor color, boolean custom) {
        this.id = Objects.requireNonNull(id, "id must be set");
        this.name = normalizeAndValidateName(name);
        this.color = color == null ? ExpenseCategoryColor.TRANSPARENT : color;
        this.custom =  custom;
    }

    private String normalizeAndValidateName(String name) {

        if (name == null){
            throw new NullPointerException("Expense Category name can not be null.");
        }

        if (name.isBlank()){
            throw new IllegalArgumentException("Expense Category name can not be empty or contains only spaces.");
        }

        if (name.length() > MAX_EXPENSE_CATEGORY_NAME_LENGTH){
            throw new IllegalArgumentException("Expense Category name is too big. Please, make it shorter.");
        }

        name = name.trim().toLowerCase();
        return name;

    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public ExpenseCategoryColor getColor() {
        return color;
    }

    public void setColor(ExpenseCategoryColor color) {

        this.color = Objects.requireNonNull(color, "color must be set");
    }

    public boolean isCustom() {
        return custom;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ExpenseCategory expenseCategory)) return false;
        return Objects.equals(name, expenseCategory.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
