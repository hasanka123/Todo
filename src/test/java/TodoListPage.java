import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class TodoListPage {
    private WebDriver driver;
    private WebDriverWait wait;

    public TodoListPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    @FindBy(css = ".new-todo")
    private WebElement todoInput;

    @FindBy(css = ".toggle-all")
    private WebElement toggleAllCheckbox;

    @FindBy(css = ".destroy")
    private WebElement deleteButton;

    @FindBy(css = ".clear-completed")
    private WebElement clearCompletedButton;

    @FindBy(xpath = "//ul[@class='todo-list']/li")
    private List<WebElement> todoItems;

    @FindBy(linkText = "All")
    private WebElement allFilterLink;

    @FindBy(linkText = "Active")
    private WebElement activeFilterLink;

    @FindBy(linkText = "Completed")
    private WebElement completedFilterLink;

    public void deleteTodo() {
        deleteButton.click();
    }

    public void clearCompletedTodos() {
        clearCompletedButton.click();
    }

    public void markAllTodosAsCompleted() {
        toggleAllCheckbox.click();
    }

    public void markAllTodosAsActive() {
        toggleAllCheckbox.click();
    }

    public void showAllTodos() {
        allFilterLink.click();
    }

    public void showActiveTodos() {
        activeFilterLink.click();
    }

    public void showCompletedTodos() {
        completedFilterLink.click();
    }

    public By getTodoByLabel(String todoLabel) {
        return By.xpath("//label[text()='" + todoLabel + "']");
    }
    public List<WebElement> getTodoItems() {
        return todoItems;
    }

    public int getTodoItemsCount() {
        return todoItems.size();
    }

    public void addTodo(String todoText) {
        todoInput.sendKeys(todoText, Keys.ENTER);
    }

    public WebElement markTodoAsCompleted(String todoText, boolean returnItemElement) {
        WebElement todoCheckbox = driver.findElement(By.xpath("//label[text()='" + todoText + "']/preceding-sibling::input[@class='toggle']"));
        todoCheckbox.click();

        if (returnItemElement) {
            return driver.findElement(By.xpath("//label[text()='" + todoText + "']/ancestor::li"));
        } else {
            return null;
        }
    }

    public void editTodo(String oldTodoText, String newTodoText) {
        By todoLabelLocator = getTodoByLabel(oldTodoText);
        WebElement todoLabel = wait.until(ExpectedConditions.elementToBeClickable(todoLabelLocator));

        Actions actions = new Actions(driver);
        actions.doubleClick(todoLabel).perform();

        WebElement todoEditInput = wait.until(ExpectedConditions.elementToBeClickable(getFocusedElement()));

        todoEditInput.sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.DELETE, newTodoText, Keys.ENTER);
    }

    public WebElement getFocusedElement() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        return (WebElement) js.executeScript("return document.activeElement");
    }
}