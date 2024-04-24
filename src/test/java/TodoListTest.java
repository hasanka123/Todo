import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import io.github.bonigarcia.wdm.WebDriverManager;

import java.time.Duration;
import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class TodoListTest {
    private WebDriver driver;
    private TodoListPage todoListPage;
    private WebDriverWait wait;
    private String sampleTodo = "Sample Todo";
    private String updatedTodo = "Updated Todo";
    private String todo1 = "Todo 1";
    private String todo2 = "Todo 2";
    private String todo3 = "Todo 3";

    @BeforeClass
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://todomvc.com/examples/react/dist/");
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        todoListPage = new TodoListPage(driver);
    }

    @Test(priority = 1)
    public void testAddTodo() {
        int countBefore = todoListPage.getTodoItemsCount();
        todoListPage.addTodo(sampleTodo);
        wait.until(ExpectedConditions.visibilityOfElementLocated(todoListPage.getTodoByLabel(sampleTodo)));
        int countAfter = todoListPage.getTodoItemsCount();
        assertEquals(countAfter, countBefore + 1, "New todo is not added");
    }

    @Test(priority = 2)
    public void testMarkTodoAsCompleted() {
        WebElement completedTodo = todoListPage.markTodoAsCompleted(sampleTodo, true);
        wait.until(ExpectedConditions.attributeContains(completedTodo, "class", "completed"));
        assertTrue(completedTodo.getAttribute("class").contains("completed"));
    }

    @Test(priority = 3)
    public void testDeleteTodo() {
        int countBefore = todoListPage.getTodoItemsCount();
        todoListPage.deleteTodo();
        wait.until(ExpectedConditions.invisibilityOfElementLocated(todoListPage.getTodoByLabel(sampleTodo)));
        int countAfter = todoListPage.getTodoItemsCount();
        assertEquals(countAfter, countBefore - 1, "Todo is not deleted");
    }

    @Test(priority = 4)
    public void testAddAndEditTodo() {
        todoListPage.addTodo(sampleTodo);
        wait.until(ExpectedConditions.visibilityOfElementLocated(todoListPage.getTodoByLabel(sampleTodo)));
        todoListPage.editTodo(sampleTodo, updatedTodo);
        String combinedStr = sampleTodo.concat(updatedTodo);
        WebElement editedTodo = driver.findElement(todoListPage.getTodoByLabel(combinedStr));
        assertEquals(editedTodo.getText(), combinedStr, "Todo is not edited");
    }

    @Test(priority = 5)
    public void testCheckAndUncheckAll() {
        deleteAllTodos();

        todoListPage.addTodo(todo1);
        todoListPage.addTodo(todo2);
        todoListPage.addTodo(todo3);

        int countBefore = todoListPage.getTodoItemsCount();

        // Check all todos
        todoListPage.markAllTodosAsCompleted();

        int countAfter = todoListPage.getTodoItemsCount();

        Assert.assertEquals(countAfter, countBefore);

        // Uncheck all todos
        todoListPage.markAllTodosAsActive();

        int countAfterUncheck = todoListPage.getTodoItemsCount();

        List<WebElement> todoItems = todoListPage.getTodoItems();
        for (WebElement todoItem : todoItems) {
            Assert.assertFalse(todoItem.getAttribute("class").contains("completed"));
        }

        Assert.assertEquals(countBefore, countAfterUncheck);
    }

    @Test(priority = 6)
    public void testCheckActive() {
        deleteAllTodos();

        todoListPage.addTodo(todo1);
        todoListPage.addTodo(todo2);
        todoListPage.addTodo(todo3);

        todoListPage.markTodoAsCompleted(todo1, false);

        todoListPage.showActiveTodos();

        int activeTodoCount = 0;
        List<WebElement> todoItems = todoListPage.getTodoItems();
        for (WebElement todoItem : todoItems) {
            if (!todoItem.getAttribute("class").contains("completed")) {
                activeTodoCount++;
            }
        }

        Assert.assertEquals(activeTodoCount, 2);

        int totalTodoCount = todoListPage.getTodoItemsCount();
        Assert.assertEquals(todoItems.size(), totalTodoCount);
    }

    @Test(priority = 7)
    public void testCheckComplete() {
        deleteAllTodos();

        todoListPage.addTodo(todo1);
        todoListPage.addTodo(todo2);
        todoListPage.addTodo(todo3);

        todoListPage.markTodoAsCompleted(todo1, false);

        todoListPage.showCompletedTodos();

        List<WebElement> completedTodoItems = todoListPage.getTodoItems();
        for (WebElement todoItem : completedTodoItems) {
            Assert.assertTrue(todoItem.getAttribute("class").contains("completed"));
        }
    }

    @Test(priority = 8)
    public void testClearComplete() {
        deleteAllTodos();

        todoListPage.addTodo(todo1);
        todoListPage.addTodo(todo2);
        todoListPage.addTodo(todo3);

        todoListPage.markTodoAsCompleted(todo1, false);

        todoListPage.clearCompletedTodos();

        List<WebElement> todoItemsAfterClear = todoListPage.getTodoItems();
        for (WebElement todoItem : todoItemsAfterClear) {
            Assert.assertFalse(todoItem.getAttribute("class").contains("completed"));
        }
    }

    @Test(priority = 9)
    public void testSingleCharTodo() {
        String singleCharTodo = "a";
        int countBefore = todoListPage.getTodoItemsCount();
        todoListPage.addTodo(singleCharTodo);
        int countAfter = todoListPage.getTodoItemsCount();
        Assert.assertEquals(countAfter, countBefore, "Single char todo was added, which should not be allowed");
    }

    private void deleteAllTodos(){
        todoListPage.showAllTodos();
        todoListPage.markAllTodosAsCompleted();
        todoListPage.clearCompletedTodos();
    }

    @AfterClass
    public void tearDown() {
        driver.quit();
    }
}