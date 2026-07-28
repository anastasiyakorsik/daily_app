package korsik.daily.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class ExpenseTest {

    @Test
    @DisplayName("Create valid Expense when define only notNull required fields")
    public void createValidExpenseDefineOnlyNotNullRequiredFields() {
        Expense expense = Expense.builder()
                .id(1L)
                .amount(BigDecimal.valueOf(10))
                .build();

        assertEquals(1L, expense.getId());
        assertEquals(BigDecimal.valueOf(10), expense.getAmount());
        assertTrue(expense.getCategory().isEmpty());
        assertTrue(expense.getComment().isEmpty());
        assertTrue(expense.getDate().isBefore(LocalDateTime.now()));
        assertEquals(Currency.RUB, expense.getCurrency());
    }

    @Test
    @DisplayName("Create valid Expense when define all fields")
    public void createValidExpenseDefineAllFields() {
        Expense expense = Expense.builder()
                .id(1L)
                .amount(BigDecimal.valueOf(1000))
                .category(new ExpenseCategory(1L, "test_category", null, false))
                .comment("expense_comment")
                .date(LocalDateTime.of(2026, 7, 25, 17, 30))
                .currency(Currency.RUB)
                .build();

        assertEquals(1L, expense.getId());
        assertEquals(BigDecimal.valueOf(1000), expense.getAmount());
        assertEquals(new ExpenseCategory(1L, "test_category", null, false), expense.getCategory().get());
        assertEquals("expense_comment", expense.getComment().get());
        assertEquals(LocalDateTime.of(2026, 7, 25, 17, 30),
                expense.getDate());
        assertEquals(Currency.RUB, expense.getCurrency());
    }

    @Test
    @DisplayName("Create valid Expense when null id")
    public void createValidExpenseWhenNullId() {

        assertThrows(NullPointerException.class,
                () -> {
                    Expense.builder()
                            .id(null)
                            .amount(BigDecimal.valueOf(1000))
                            .build();
                },
                "id must not be null");
    }

    @Test
    @DisplayName("Create valid Expense when null amount")
    public void createValidExpenseWhenNullAmount() {

        assertThrows(NullPointerException.class,
                () -> {
                    Expense.builder()
                            .id(1L)
                            .amount(null)
                            .build();
                },
                "amount must not be null");
    }

    @Test
    @DisplayName("Create valid Expense when null date")
    public void createValidExpenseWhenNullDate() {

        assertThrows(NullPointerException.class,
                () -> {
                    Expense.builder()
                            .id(1L)
                            .amount(BigDecimal.valueOf(1000))
                            .date(null)
                            .build();
                },
                "date must not be null");
    }

    @Test
    @DisplayName("Create valid Expense when null currency")
    public void createValidExpenseWhenNullCurrency() {

        assertThrows(NullPointerException.class,
                () -> {
                    Expense.builder()
                            .id(1L)
                            .amount(BigDecimal.valueOf(1000))
                            .currency(null)
                            .build();
                },
                "currency must not be null");
    }

    @Test
    @DisplayName("Create valid Expense and change comment with valid value")
    public void createValidExpenseAndChangeCommentWithValidValue() {

        Expense expense_1 = Expense.builder()
                .id(1L)
                .amount(BigDecimal.valueOf(1000))
                .comment("test_1")
                .build();

        Expense expense_2 = expense_1.withComment("test_2");
        assertEquals("test_2", expense_2.getComment().get());
    }

    @Test
    @DisplayName("Create valid Expense and change comment with null value")
    public void createValidExpenseAndChangeCommentWithNullValue() {

        Expense expense_1 = Expense.builder()
                .id(1L)
                .amount(BigDecimal.valueOf(1000))
                .comment("test_1")
                .build();

        assertThrows(NullPointerException.class,
                () -> {
                    expense_1.withComment(null);
                },
                "comment must be set");
    }

    @Test
    @DisplayName("Create valid Expense and change comment with empty value")
    public void createValidExpenseAndChangeCommentEmptyValue() {

        Expense expense_1 = Expense.builder()
                .id(1L)
                .amount(BigDecimal.valueOf(1000))
                .comment("test_1")
                .build();

        assertThrows(IllegalArgumentException.class,
                () -> {
                    expense_1.withComment("");
                },
                "comment can not be empty");
    }

    @Test
    @DisplayName("Create valid Expense and change comment with blank value")
    public void createValidExpenseAndChangeCommentBlankValue() {

        Expense expense_1 = Expense.builder()
                .id(1L)
                .amount(BigDecimal.valueOf(1000))
                .comment("test_1")
                .build();

        assertThrows(IllegalArgumentException.class,
                () -> {
                    expense_1.withComment("   ");
                },
                "comment can not be blank");
    }

    @Test
    @DisplayName("Create valid Expense and change category with valid value")
    public void createValidExpenseAndChangeCategoryWithValidValue() {

        Expense expense_1 = Expense.builder()
                .id(1L)
                .amount(BigDecimal.valueOf(1000))
                .category(new ExpenseCategory(1L, "test_category_1", null, false))
                .build();

        ExpenseCategory expenseCategory = new ExpenseCategory(1L, "test_category_2", null, false);
        Expense expense_2 = expense_1.withCategory(expenseCategory);
        assertEquals(expenseCategory, expense_2.getCategory().get());
    }

    @Test
    @DisplayName("Create valid Expense and change category with null value")
    public void createValidExpenseAndChangeCategoryWithNullValue() {

        Expense expense_1 = Expense.builder()
                .id(1L)
                .amount(BigDecimal.valueOf(1000))
                .category(new ExpenseCategory(1L, "test_category_1", null, false))
                .build();

        assertThrows(NullPointerException.class,
                () -> {
                    expense_1.withCategory(null);
                });
    }

    @Test
    @DisplayName("Set Default Currency With Valid Value")
    public void setDefaultCurrencyValidValue() {
        Expense.setDefaultCurrency(Currency.RUB);
    }

    @Test
    @DisplayName("Set Default Currency With Null Value")
    public void setDefaultCurrencyNullValue() {
        assertThrows(NullPointerException.class,
                () -> {
                    Expense.setDefaultCurrency(null);
                });
    }

}
