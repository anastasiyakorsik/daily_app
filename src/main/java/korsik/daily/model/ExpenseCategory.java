package korsik.daily.model;

import java.util.Objects;

//todo: create parent class for categorization entities -> Label and ExpenseCategory
public class ExpenseCategory {
    private final Long id;
    private final String name;
    private String color;
    private final boolean custom;

    private static final int MAX_EXPENSE_CATEGORY_NAME_LENGTH = 120;

    public ExpenseCategory(Long id, String name, String color, boolean custom) {
        this.id = id;
        this.name = normalizeAndValidateName(name);
        this.color = color;
        this.custom = custom;
    }

    private String normalizeAndValidateName(String name) {

        if (name == null){
            throw new IllegalArgumentException("Expense Category name can not be null.");
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

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
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
