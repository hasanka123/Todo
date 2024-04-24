**How to run the tests:**
Step 1: clone https://github.com/hasanka123/Todo.git
Step 2: open Intellij import as maven project
Step 3: pom.xml will download all the dependencies automatically
Step 4: once build success, go to testng.xml and click Run
Step 5: chrome browser open and will execute all the tests (ChromeDriver will download automatically)

**Implementation strategy:**
Technology: Java, Selenium, TestNG, Maven
Design Pattern: Page Object Model (POM) and Page Factory


**Test Scenario:**
testAddTodo -> add todo
testMarkTodoAsCompleted -> add todo and mark as complete
testDeleteTodo -> add todo and delete
testAddAndEditTodo -> add todo and edit
testCheckAndUncheckAll -> check all todos and uncheck all todos
testCheckActive -> click active button and validate active todos
testCheckComplete  -> click complete button and validate complete todos
testClearComplete -> click clear-complete button and validate no any complete todos
testSingleCharTodo -> validate single char todo not allow to add
