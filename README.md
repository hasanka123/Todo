**How to run the tests:**
Step 1: Clone https://github.com/hasanka123/Todo.git
Step 2: Open Intellij import as maven project
Step 3: pom.xml will download all the dependencies automatically
Step 4: Once build the project, go to testng.xml and click Run
Step 5: Chrome browser will open and execute all the tests (ChromeDriver setup not required)

OR

Make sure local machine has installed java 18 and maven, then follow below steps
Step 1: Clone https://github.com/hasanka123/Todo.git
Step 2: Open terminal and navigate to project directory 
Step 3: Run mvn compile
Step 4: Run mvn test -Dsurefire.suiteXmlFiles=testng.xml


**Implementation strategy:**
Technology: Java, Selenium (Action class/JavascriptExecutor), TestNG, Maven
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