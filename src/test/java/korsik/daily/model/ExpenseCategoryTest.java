package korsik.daily.model;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ExpenseCategoryTest {

    @Test
    @DisplayName("Create valid ExpenseCategory with null color")
    public void createCorrectExpenseCategoryNullColor(){
        ExpenseCategory expenseCategory = new ExpenseCategory(1L, "test_ex_cat", null, true);
        assertEquals(1L, (long) expenseCategory.getId());
        assertEquals("test_ex_cat", expenseCategory.getName());
        assertEquals(ExpenseCategoryColor.TRANSPARENT, expenseCategory.getColor());
        assertTrue(expenseCategory.isCustom());
    }

    @Test
    @DisplayName("Create valid ExpenseCategory with all args")
    public void createCorrectExpenseCategoryAllArgs(){
        ExpenseCategory expenseCategory = new ExpenseCategory(1L, "test_ex_cat", ExpenseCategoryColor.GREY, true);
        assertEquals(1L, (long) expenseCategory.getId());
        assertEquals("test_ex_cat", expenseCategory.getName());
        assertEquals(ExpenseCategoryColor.GREY, expenseCategory.getColor());
        assertTrue(expenseCategory.isCustom());
    }

    @Test
    @DisplayName("Create id = null ExpenseCategory")
    public void createNullIdExpenseCategory(){
        assertThrows(NullPointerException.class,
                () -> {
                    new ExpenseCategory(null, "test_ex_cat", ExpenseCategoryColor.GREY, true);
                }, "id must be set");
    }

    @Test
    @DisplayName("Create name = null ExpenseCategory")
    public void createNullNameExpenseCategory(){
        assertThrows(NullPointerException.class,
                () -> {
                    new ExpenseCategory(1L, null, ExpenseCategoryColor.GREY, true);
                }, "Expense Category name can not be null.");
    }

    @Test
    @DisplayName("Create empty name ExpenseCategory")
    public void createEmptyNameExpenseCategory(){
        assertThrows(IllegalArgumentException.class,
                () -> {
                    new ExpenseCategory(1L, "", ExpenseCategoryColor.GREY, true);
                }, "Expense Category name can not be empty or contains only spaces.");
    }

    @Test
    @DisplayName("Create blank name ExpenseCategory")
    public void createBlankNameExpenseCategory(){
        assertThrows(IllegalArgumentException.class,
                () -> {
                    new ExpenseCategory(1L, "  ", ExpenseCategoryColor.GREY, true);
                }, "Expense Category name can not be empty or contains only spaces.");
    }

    @Test
    @DisplayName("Create ExpenseCategory with too long name")
    public void createExtraLongNameExpenseCategory(){
        assertThrows(IllegalArgumentException.class,
                () -> new ExpenseCategory(1L,
                        "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa",
                        null, true),
                "Expense Category name is too big. Please, make it shorter.");
    }

    @Test
    @DisplayName("Change ExpenseCategory color with correct value")
    public void changeExpenseCategoryColorCorrect(){
        ExpenseCategory expenseCategory = new ExpenseCategory(1L, "test", null, false);
        expenseCategory.setColor(ExpenseCategoryColor.GREY);
        assertEquals(ExpenseCategoryColor.GREY, expenseCategory.getColor());
    }

    @Test
    @DisplayName("Change ExpenseCategory color with null value")
    public void changeExpenseCategoryColorNull(){
        ExpenseCategory expenseCategory = new ExpenseCategory(1L, "test", null, false);
        assertThrows(NullPointerException.class,
                () -> expenseCategory.setColor(null),
                "color must be set");
    }

    @Test
    @DisplayName("Change ExpenseCategory color with same value")
    public void changeExpenseCategoryColorSame(){
        ExpenseCategory expenseCategory = new ExpenseCategory(1L, "test", null, false);
        assertThrows(IllegalArgumentException.class,
                () -> expenseCategory.setColor(ExpenseCategoryColor.TRANSPARENT),
                "To change color provide different one");
    }

    @Test
    @DisplayName("ExpenseCategories with same names equals")
    public void equalNameExpenseCategoriesEquals(){
        ExpenseCategory expenseCategory1 = new ExpenseCategory(1L, "test", null, false);
        ExpenseCategory expenseCategory2 = new ExpenseCategory(2L, "test", null, true);
        assertEquals(expenseCategory1, expenseCategory2);
    }

    @Test
    @DisplayName("ExpenseCategories with same names but different cases equals")
    public void differentCaseNameExpenseCategoriesEquals(){
        ExpenseCategory expenseCategory1 = new ExpenseCategory(1L, "test", null, false);
        ExpenseCategory expenseCategory2 = new ExpenseCategory(2L, "TEST", null, true);
        assertEquals(expenseCategory1, expenseCategory2);
    }

    @Test
    @DisplayName("ExpenseCategories with different names does not equal")
    public void differentNameExpenseCategoriesDoesNotEqual(){
        ExpenseCategory expenseCategory1 = new ExpenseCategory(1L, "test", null, false);
        ExpenseCategory expenseCategory2 = new ExpenseCategory(2L, "TST", null, true);
        assertNotEquals(expenseCategory1, expenseCategory2);
    }

    @Test
    @DisplayName("Equal ExpenseCategories has same hashcode")
    public void equalExpenseCategoriesHaveEqualHashCodes(){
        ExpenseCategory expenseCategory1 = new ExpenseCategory(1L, "test", null, false);
        ExpenseCategory expenseCategory2 = new ExpenseCategory(2L, "test", null, true);
        assertEquals(expenseCategory1.hashCode(), expenseCategory2.hashCode());
    }
}
