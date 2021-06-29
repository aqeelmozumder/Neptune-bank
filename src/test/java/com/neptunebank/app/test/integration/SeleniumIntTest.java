package com.neptunebank.app.test.integration;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.Random;
import java.util.List;
import java.util.ArrayList;


import static org.junit.jupiter.api.Assertions.*;

@Tag("Integration")
public class SeleniumIntTest {
	private WebDriver driver;
	private String url;

	private final String validUsername = "rbaird";
	private final String validPassword = "wonderwoman";

	@BeforeEach
	public void setUp() {
		System.setProperty("webdriver.chrome.driver", "/Users/aqeel/OneDrive/Desktop/chromedriver.exe");
		// setDriver(browser);
		driver = new ChromeDriver();
		url = "http://localhost:4080";
		driver.navigate().to(url);
		driver.manage().window().maximize();
		// driver.get(url);
	}

	private void login() {
		login(validUsername, validPassword);
	}

	private void login(String username, String password) {
		driver.findElement(By.cssSelector("nav.navbar li.nav-item > a#login-item")).click();

		WebElement formEl = driver.findElement(By.cssSelector("#login-page form"));

		formEl.findElement(By.id("username")).sendKeys(username);
		formEl.findElement(By.id("password")).sendKeys(password);

		formEl.findElement(By.cssSelector("button[type=submit]")).click();
	}

		@Test
	public void headerIsCorrect() throws Exception {
		assertEquals("Neptune Bank", driver.getTitle());
	}

	@Test
	public void logsUserWithCorrectCredentials() {
		assertFalse(isElementPresent(By.id("account-menu"))); // starts logged out
		login(validUsername, validPassword);
		assertTrue(isElementPresent(By.id("account-menu")));
	}

	@Test
	public void doesNotLogUserWithIncorrectCredentials() {
		assertFalse(isElementPresent(By.id("account-menu"))); // starts logged out
		login(validUsername, validPassword + "toMakeInvalid");
		assertFalse(isElementPresent(By.id("account-menu")));
	}

	@Test
	public void payeeIsAddedCorrectly() {
		String firstName = "Lola";
		String lastName = RandomStringUtils.randomAlphanumeric(8);
		String email = (firstName + "@" + lastName + ".mail").toLowerCase();
		String telephone = RandomStringUtils.randomNumeric(10);

		login();
		driver.findElement(By.linkText("Manage Payees")).click();

		driver.findElement(By.id("jh-create-entity")).click();

		WebElement formEl = driver.findElement(By.cssSelector("#app-view-container form"));

		formEl.findElement(By.id("payee-emailID")).sendKeys(email);
		formEl.findElement(By.id("payee-firstName")).sendKeys(firstName);
		formEl.findElement(By.id("payee-lastName")).sendKeys(lastName);
		formEl.findElement(By.id("payee-telephone")).sendKeys(telephone);

		formEl.submit();

		//after creation ensure the payee is in the list

		WebElement tableEl = driver.findElement(By.cssSelector("#app-view-container table"));
		assertTrue(isElementPresent(tableEl, By.xpath(".//td[text()='" + email + "']")), "Email of new payee not found in table");
	}

	@Test
	public void transferToBenefiarySuccessful() {
		Random random = new Random();
		String transactionAmount = RandomStringUtils.randomNumeric(100);

		login();
		WebDriverWait wait3 = new WebDriverWait(driver, 10);
		wait3.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("ele_to_inv")));
		driver.findElement(By.linkText("Banking")).click();
		driver.findElement(By.linkText("Transfer Money")).click();

		driver.findElement(By.linkText("jh-create-entity")).click();

		WebElement formEl = driver.findElement(By.cssSelector("#app-view-container form"));

		formEl.findElement(By.id("transaction-amount")).sendKeys(transactionAmount);
		formEl.findElement(By.id("checkbox-payeeCheckBox-P")).click();
		Select payeeSelect = new Select(formEl.findElement(By.id("transaction-toAccount")));
		payeeSelect.selectByIndex(random.nextInt(payeeSelect.getOptions().size()));
		WebElement selectedToAccount = payeeSelect.getFirstSelectedOption();

		Select fromAccountSelect = new Select(formEl.findElement(By.id("transaction-fromAccount")));
		fromAccountSelect.selectByIndex(random.nextInt(fromAccountSelect.getOptions().size()));
		WebElement selectedFromAccount = fromAccountSelect.getFirstSelectedOption();

		formEl.submit();

		//after transfer ensure the transaction is in the list

		WebElement tableEl = driver.findElement(By.cssSelector("#app-view-container table"));
		assertTrue(isElementPresent(tableEl, By.xpath(".//td[text()='" + transactionAmount + "']")), "Transaction amount not found in table");
		assertTrue(isElementPresent(tableEl, By.xpath(".//td[text()='" + selectedToAccount.getAttribute("value") + "']")), "To-account not found in table");
		assertTrue(isElementPresent(tableEl, By.xpath(".//td[text()='" + selectedFromAccount.getAttribute("value") + "']")), "From-account not found in table");
		
		
	}

	@Test
	public void EditCustomerDetailsSuccessful() {
		String firstName = "Tester";
		String lastName =  "Testingedit";
		
		login();

		WebElement Appheader = driver.findElement(By.id("app-header"));
		WebElement Headertabs = Appheader.findElement(By.id("header-tabs"));

		
		WebDriverWait wait3 = new WebDriverWait(driver, 10);
		wait3.until(ExpectedConditions.invisibilityOfElementLocated(By.id("entity-menu")));
		Headertabs.findElement(By.id("entity-menu")).click();
		

		driver.findElement(By.linkText("Customer Details")).click();

		
		WebElement tableEl = driver.findElement(By.cssSelector("#app-view-container table"));
		WebElement tableRow = tableEl.findElement(By.xpath("//*[@id= \"app-view-container\"]/div[1]/div/div/div/div[1]/div/table/tbody/tr[3]"));
		

		WebElement EditEl =  tableRow.findElement(By.xpath("//*[@id=\"app-view-container\"]/div[1]/div/div/div/div[1]/div/table/tbody/tr[3]/td[11]/div/a[2]"));
		EditEl.click();

		WebElement formEl = driver.findElement(By.cssSelector("#app-view-container"));
	
		formEl.findElement(By.id("customer-firstName")).clear();
		formEl.findElement(By.id("customer-firstName")).sendKeys(firstName);
		formEl.findElement(By.id("customer-lastName")).clear();
		formEl.findElement(By.id("customer-lastName")).sendKeys(lastName);

		WebElement myelement = formEl.findElement(By.cssSelector("button[type=submit]"));
		JavascriptExecutor jse2 = (JavascriptExecutor)driver;
		jse2.executeScript("arguments[0].scrollIntoView()", myelement); 
		myelement.click();
		//after saving the edited details
		WebElement newtableEl = driver.findElement(By.cssSelector("#app-view-container table"));
		WebElement newtablerow = newtableEl.findElement(By.xpath("//*[@id= \"app-view-container\"]/div[1]/div/div/div/div[1]/div/table/tbody/tr[3]"));
		WebElement Cellneed = newtablerow.findElement(By.xpath("//*[@id=\"app-view-container\"]/div[1]/div/div/div/div[1]/div/table/tbody/tr[3]/td[2]"));
		System.out.println(Cellneed.getText());
		assertEquals(Cellneed.getText(), firstName);
	}

	@Test
	public void EditCustomerDetailsUnSuccessful() {
		String firstName = ""; // it wont add as system would require an input
		String lastName =  "Testingedit";
	
		login();


		WebElement Appheader = driver.findElement(By.id("app-header"));
		WebElement Headertabs = Appheader.findElement(By.id("header-tabs"));

		
		WebDriverWait wait3 = new WebDriverWait(driver, 10);
		wait3.until(ExpectedConditions.invisibilityOfElementLocated(By.id("entity-menu")));
		Headertabs.findElement(By.id("entity-menu")).click();
		

		driver.findElement(By.linkText("Customer Details")).click();

		
		WebElement tableEl = driver.findElement(By.cssSelector("#app-view-container table"));
		WebElement tableRow = tableEl.findElement(By.xpath("//*[@id= \"app-view-container\"]/div[1]/div/div/div/div[1]/div/table/tbody/tr[3]"));
		

		WebElement EditEl =  tableRow.findElement(By.xpath("//*[@id=\"app-view-container\"]/div[1]/div/div/div/div[1]/div/table/tbody/tr[3]/td[11]/div/a[2]"));
		EditEl.click();

		WebElement formEl = driver.findElement(By.cssSelector("#app-view-container"));
	
		formEl.findElement(By.id("customer-firstName")).clear();
		formEl.findElement(By.id("customer-firstName")).sendKeys(firstName);
		formEl.findElement(By.id("customer-lastName")).clear();
		formEl.findElement(By.id("customer-lastName")).sendKeys(lastName);

		WebElement myelement = formEl.findElement(By.xpath("//*[@id=\"app-view-container\"]/div[1]/div/div/div/div[2]/div/form/button[1]"));
		JavascriptExecutor jse2 = (JavascriptExecutor)driver;
		jse2.executeScript("arguments[0].scrollIntoView()", myelement); 
		myelement.click(); //we gonna go back as it wont save
		//after backing from an unsuccesful edited details
		WebElement newtableEl = driver.findElement(By.cssSelector("#app-view-container table"));
		WebElement newtablerow = newtableEl.findElement(By.xpath("//*[@id= \"app-view-container\"]/div[1]/div/div/div/div[1]/div/table/tbody/tr[3]"));
		WebElement Cellneed = newtablerow.findElement(By.xpath("//*[@id=\"app-view-container\"]/div[1]/div/div/div/div[1]/div/table/tbody/tr[3]/td[2]"));
		System.out.println(Cellneed.getText());
		assertNotEquals(Cellneed.getText(), firstName);
	}
	
	
	@Test
	public void DeleteCustomerDetailsSuccessful() {
		

		login();


		WebElement Appheader = driver.findElement(By.id("app-header"));
		WebElement Headertabs = Appheader.findElement(By.id("header-tabs"));
		WebDriverWait wait3 = new WebDriverWait(driver, 10);
		wait3.until(ExpectedConditions.invisibilityOfElementLocated(By.id("entity-menu")));
		Headertabs.findElement(By.id("entity-menu")).click();
		

		driver.findElement(By.linkText("Customer Details")).click();
		
		WebElement tableEl = driver.findElement(By.cssSelector("#app-view-container table"));
		WebElement tableRow = tableEl.findElement(By.xpath("//*[@id= \"app-view-container\"]/div[1]/div/div/div/div[1]/div/table/tbody/tr[3]"));

		WebElement DeleteEl =  tableRow.findElement(By.xpath("//*[@id=\"app-view-container\"]/div[1]/div/div/div/div[1]/div/table/tbody/tr[3]/td[11]/div/a[3]"));
		WebElement Cellneed = tableRow.findElement(By.xpath("//*[@id=\"app-view-container\"]/div[1]/div/div/div/div[1]/div/table/tbody/tr[3]/td[2]"));
		String firstname = Cellneed.getText();
		DeleteEl.click();
		

		WebDriverWait wait2 = new WebDriverWait(driver, 10);
		wait2.until(ExpectedConditions.invisibilityOfElementLocated(By.id("jhi-confirm-delete-customer")));
		driver.findElement(By.id("jhi-confirm-delete-customer")).click();

		
		//after deleting

		WebElement newtableEl = driver.findElement(By.cssSelector("#app-view-container table"));
		WebElement newtablerow = newtableEl.findElement(By.xpath("//*[@id= \"app-view-container\"]/div[1]/div/div/div/div[1]/div/table/tbody/tr[3]"));
		WebElement newCellneed = newtablerow.findElement(By.xpath("//*[@id=\"app-view-container\"]/div[1]/div/div/div/div[1]/div/table/tbody/tr[3]/td[2]"));
		String newfirstname = newCellneed.getText();
		assertNotEquals(newfirstname, firstname );

		//Internal app bug issue. test Should work but becaus eof the bug test is not working
	}


	@Test
	public void EditTransactionDetailsSuccessful() {
		
		String transactionAmount = RandomStringUtils.randomNumeric(4);

		login();

		WebElement Appheader = driver.findElement(By.id("app-header"));
		WebElement Headertabs = Appheader.findElement(By.id("header-tabs"));

		
		WebDriverWait wait3 = new WebDriverWait(driver, 30);
		wait3.until(ExpectedConditions.invisibilityOfElementLocated(By.id("entity-menu")));
		Headertabs.findElement(By.id("entity-menu")).click();
		

		driver.findElement(By.linkText("Transfer Money")).click();

		
		WebElement tableEl = driver.findElement(By.cssSelector("#app-view-container table"));
		WebElement tableRow = tableEl.findElement(By.xpath("//*[@id=\"app-view-container\"]/div[1]/div/div/div/div[1]/div/table/tbody/tr[3]"));
		WebElement Cellneed = tableRow.findElement(By.xpath("//*[@id=\"app-view-container\"]/div[1]/div/div/div/div[1]/div/table/tbody/tr[4]/td[3]"));
		WebElement ToAccountneeded = tableRow.findElement(By.xpath("//*[@id=\"app-view-container\"]/div[1]/div/div/div/div[1]/div/table/tbody/tr[4]/td[5]"));
		String ToAccount = ToAccountneeded.getText();
		// String oldTransactionAmount = Cellneed.getText();
		WebElement EditEl =  tableRow.findElement(By.xpath("//*[@id=\"app-view-container\"]/div[1]/div/div/div/div[1]/div/table/tbody/tr[4]/td[7]/div/a[2]"));
		EditEl.click();

		WebElement formEl = driver.findElement(By.cssSelector("#app-view-container"));
		
		formEl.findElement(By.id("transaction-amount")).clear();
		formEl.findElement(By.id("transaction-amount")).sendKeys(transactionAmount);
		formEl.findElement(By.id("transaction-toAccount1")).clear();
		formEl.findElement(By.id("transaction-toAccount1")).sendKeys(ToAccount);

		WebElement myelement = formEl.findElement(By.cssSelector("button[type=submit]"));
		JavascriptExecutor jse2 = (JavascriptExecutor)driver;
		jse2.executeScript("arguments[0].scrollIntoView()", myelement); 
		myelement.click();
		//after saving the edited details
		WebElement newtableEl = driver.findElement(By.cssSelector("#app-view-container table"));
		WebElement newtablerow = newtableEl.findElement(By.xpath("//*[@id=\"app-view-container\"]/div[1]/div/div/div/div[1]/div/table/tbody/tr[3]"));
		WebElement newCellneed = newtablerow.findElement(By.xpath("//*[@id=\"app-view-container\"]/div[1]/div/div/div/div[1]/div/table/tbody/tr[4]/td[3]"));
		String EditedTransactionAmount = newCellneed.getText();
		assertEquals(EditedTransactionAmount, transactionAmount);
	}


	@Test
	public void EditTransactionDetailsUnSuccessful() {
		
		String transactionAmount = ""; // This will fail beacuse it requires an input

		login();

		WebElement Appheader = driver.findElement(By.id("app-header"));
		WebElement Headertabs = Appheader.findElement(By.id("header-tabs"));

		
		WebDriverWait wait3 = new WebDriverWait(driver, 30);
		wait3.until(ExpectedConditions.invisibilityOfElementLocated(By.id("entity-menu")));
		Headertabs.findElement(By.id("entity-menu")).click();
		

		driver.findElement(By.linkText("Transfer Money")).click();

		
		WebElement tableEl = driver.findElement(By.cssSelector("#app-view-container table"));
		WebElement tableRow = tableEl.findElement(By.xpath("//*[@id=\"app-view-container\"]/div[1]/div/div/div/div[1]/div/table/tbody/tr[3]"));
		WebElement Cellneed = tableRow.findElement(By.xpath("//*[@id=\"app-view-container\"]/div[1]/div/div/div/div[1]/div/table/tbody/tr[4]/td[3]"));
		WebElement ToAccountneeded = tableRow.findElement(By.xpath("//*[@id=\"app-view-container\"]/div[1]/div/div/div/div[1]/div/table/tbody/tr[4]/td[5]"));
		String ToAccount = ToAccountneeded.getText();
		// String oldTransactionAmount = Cellneed.getText();
		WebElement EditEl =  tableRow.findElement(By.xpath("//*[@id=\"app-view-container\"]/div[1]/div/div/div/div[1]/div/table/tbody/tr[4]/td[7]/div/a[2]"));
		EditEl.click();

		WebElement formEl = driver.findElement(By.cssSelector("#app-view-container"));
		
		formEl.findElement(By.id("transaction-amount")).clear();
		formEl.findElement(By.id("transaction-amount")).sendKeys(transactionAmount);
		formEl.findElement(By.id("transaction-toAccount1")).clear();
		formEl.findElement(By.id("transaction-toAccount1")).sendKeys(ToAccount);

		WebElement myelement = formEl.findElement(By.xpath("//*[@id=\"app-view-container\"]/div[1]/div/div/div/div[2]/div/form/button[1]"));
		JavascriptExecutor jse2 = (JavascriptExecutor)driver;
		jse2.executeScript("arguments[0].scrollIntoView()", myelement); 
		myelement.click();
		//after backing from an unsuccesful edited details
		WebElement newtableEl = driver.findElement(By.cssSelector("#app-view-container table"));
		WebElement newtablerow = newtableEl.findElement(By.xpath("//*[@id=\"app-view-container\"]/div[1]/div/div/div/div[1]/div/table/tbody/tr[3]"));
		WebElement newCellneed = newtablerow.findElement(By.xpath("//*[@id=\"app-view-container\"]/div[1]/div/div/div/div[1]/div/table/tbody/tr[4]/td[3]"));
		String EditedTransactionAmount = newCellneed.getText();
		assertNotEquals(EditedTransactionAmount, transactionAmount);
	}

	@Test
	public void AddNewTransactionSuccessful() {
		
		String transactionAmount = RandomStringUtils.randomNumeric(4);
		
		login();


		WebElement Appheader = driver.findElement(By.id("app-header"));
		WebElement Headertabs = Appheader.findElement(By.id("header-tabs"));

		
		WebDriverWait wait3 = new WebDriverWait(driver, 10);
		wait3.until(ExpectedConditions.invisibilityOfElementLocated(By.id("entity-menu")));
		Headertabs.findElement(By.id("entity-menu")).click();
		

		driver.findElement(By.linkText("Transfer Money")).click();
		driver.findElement(By.id("jh-create-entity")).click();


		WebElement formEl = driver.findElement(By.cssSelector("#app-view-container"));
		
		formEl.findElement(By.id("transaction-amount")).clear();
		formEl.findElement(By.id("transaction-amount")).sendKeys(transactionAmount);
		WebElement AmountFrom = formEl.findElement(By.id("transaction-fromAccount"));
		AmountFrom.click();
		Select drpdownAccount = new Select (AmountFrom);
		drpdownAccount.selectByIndex(1);
		List<WebElement> getOptions = drpdownAccount.getOptions();
		List<String> list = new ArrayList<String>(); 
		// Use advanced for loop to add all options in the list. 
		  for(WebElement element : getOptions) 
		  { 
			list.add(element.getText()); // Here, getText() method of WebElement class has been used to add the text label of all the options in the list. 
		  } 
		String AmountTo = list.get(2);
		
		formEl.findElement(By.id("transaction-toAccount1")).sendKeys(AmountTo);

		WebElement myelement = formEl.findElement(By.cssSelector("button[type=submit]"));
		JavascriptExecutor jse2 = (JavascriptExecutor)driver;
		jse2.executeScript("arguments[0].scrollIntoView()", myelement); 
		myelement.click();
		//after saving the edited details
		// WebDriverWait wait2 = new WebDriverWait(driver, 10);
		// wait2.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("#app-view-container table")));
		WebElement newtableEl = driver.findElement(By.cssSelector("#app-view-container table"));
		
		WebElement clickDate = newtableEl.findElement(By.xpath("//*[@id=\"app-view-container\"]/div[1]/div/div/div/div[1]/div/table/thead/tr/th[2]"));
		clickDate.click();
		WebElement newtablerow = newtableEl.findElement(By.xpath("//*[@id=\"app-view-container\"]/div[1]/div/div/div/div[1]/div/table/tbody/tr[1]"));
		WebElement newCellneed = newtablerow.findElement(By.xpath("//*[@id=\"app-view-container\"]/div[1]/div/div/div[1]/div[1]/div/table/tbody/tr[1]/td[5]"));
		String NewToAccount = newCellneed.getText();
		assertEquals(NewToAccount, AmountTo);
	}


	@Test
	public void AddNewTransactionUnSuccessful() {
		
		String transactionAmount = RandomStringUtils.randomNumeric(4);

		login();

		WebElement Appheader = driver.findElement(By.id("app-header"));
		WebElement Headertabs = Appheader.findElement(By.id("header-tabs"));

		
		WebDriverWait wait3 = new WebDriverWait(driver, 10);
		wait3.until(ExpectedConditions.invisibilityOfElementLocated(By.id("entity-menu")));
		Headertabs.findElement(By.id("entity-menu")).click();
		

		driver.findElement(By.linkText("Transfer Money")).click();
		driver.findElement(By.id("jh-create-entity")).click();


		WebElement formEl = driver.findElement(By.cssSelector("#app-view-container"));
		
		formEl.findElement(By.id("transaction-amount")).clear();
		formEl.findElement(By.id("transaction-amount")).sendKeys(transactionAmount);
		WebElement AmountFrom = formEl.findElement(By.id("transaction-fromAccount"));
		AmountFrom.click();
		Select drpdownAccount = new Select (AmountFrom);
		drpdownAccount.selectByIndex(1);
		List<WebElement> getOptions = drpdownAccount.getOptions();
		List<String> list = new ArrayList<String>(); 
		// Use advanced for loop to add all options in the list. 
		  for(WebElement element : getOptions) 
		  { 
			list.add(element.getText()); // Here, getText() method of WebElement class has been used to add the text label of all the options in the list. 
		  } 
		String AmountTo = ""; // will fail here so have to go back
		
		formEl.findElement(By.id("transaction-toAccount1")).sendKeys(AmountTo);

		WebElement myelement = formEl.findElement(By.xpath("//*[@id=\"app-view-container\"]/div[1]/div/div/div/div[2]/div/form/button[1]"));
		JavascriptExecutor jse2 = (JavascriptExecutor)driver;
		jse2.executeScript("arguments[0].scrollIntoView()", myelement); 
		myelement.click();
		//after backing from an unsicessful edited details
		// WebDriverWait wait2 = new WebDriverWait(driver, 10);
		// wait2.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("#app-view-container table")));
		WebElement newtableEl = driver.findElement(By.cssSelector("#app-view-container table"));
		
		WebElement clickDate = newtableEl.findElement(By.xpath("//*[@id=\"app-view-container\"]/div[1]/div/div/div/div[1]/div/table/thead/tr/th[2]"));
		clickDate.click();
		WebElement newtablerow = newtableEl.findElement(By.xpath("//*[@id=\"app-view-container\"]/div[1]/div/div/div/div[1]/div/table/tbody/tr[1]"));
		WebElement newCellneed = newtablerow.findElement(By.xpath("//*[@id=\"app-view-container\"]/div[1]/div/div/div[1]/div[1]/div/table/tbody/tr[1]/td[5]"));
		String NewToAccount = newCellneed.getText();
		assertNotEquals(NewToAccount, AmountTo);
	}
	
	@Test
	public void FilterTransactionSuccessful() throws ParseException {
		String Tester = "InCorrect";
		String Correct = "Correct";
		
		login();
	
		WebElement Appheader = driver.findElement(By.id("app-header"));
		WebElement Headertabs = Appheader.findElement(By.id("header-tabs"));

		
		WebDriverWait wait3 = new WebDriverWait(driver, 10);
		wait3.until(ExpectedConditions.invisibilityOfElementLocated(By.id("entity-menu")));
		Headertabs.findElement(By.id("entity-menu")).click();
		

		driver.findElement(By.linkText("Transfer Money")).click();
		WebElement StartDate = driver.findElement(By.xpath("//*[@id=\"start-date\"]"));
		WebElement EndDate = driver.findElement(By.xpath("//*[@id=\"end-date\"]"));
		String dateStart = "24-09-2018";
		StartDate.sendKeys("002018-09-25");
		EndDate.sendKeys("002020-09-25");
		WebElement Filter = driver.findElement(By.id("filter-tran"));
		Filter.click();
		
		WebElement newtableEl = driver.findElement(By.cssSelector("#app-view-container table"));
		
		WebElement clickDate = newtableEl.findElement(By.xpath("//*[@id=\"app-view-container\"]/div[1]/div/div/div/div[1]/div/table/thead/tr/th[2]"));
		clickDate.click();
		WebElement newtablerow = newtableEl.findElement(By.xpath("//*[@id=\"app-view-container\"]/div[1]/div/div/div/div[1]/div/table/tbody/tr[1]"));
		WebElement newCellneed = newtablerow.findElement(By.xpath("//*[@id=\"app-view-container\"]/div[1]/div/div/div/div[1]/div/table/tbody/tr[1]/td[2]/span"));
		String NewDate = newCellneed.getText();
		NewDate = NewDate.replace("/", "-");
		SimpleDateFormat sdformat = new SimpleDateFormat("dd-MM-yyyy");
		Date d1 = sdformat.parse(dateStart);
		Date d2 = sdformat.parse(NewDate);
		System.out.println("The date 1 is: " + sdformat.format(d1));
		System.out.println("The date 2 is: " + sdformat.format(d2));
		

		// int NewStartDate = Integer.parseInt(NewDate);

		if(d2.compareTo(d1)>0){
			Tester = "Correct";
			
		}
		
		assertEquals(Tester, Correct);
	}

	@Test
	public void FilterTransactionUnSuccessful() throws ParseException {
		String Tester = "InCorrect";
		String Correct = "Correct";

		login();

		WebElement Appheader = driver.findElement(By.id("app-header"));
		WebElement Headertabs = Appheader.findElement(By.id("header-tabs"));

		
		WebDriverWait wait3 = new WebDriverWait(driver, 10);
		wait3.until(ExpectedConditions.invisibilityOfElementLocated(By.id("entity-menu")));
		Headertabs.findElement(By.id("entity-menu")).click();
		

		driver.findElement(By.linkText("Transfer Money")).click();
		WebElement StartDate = driver.findElement(By.xpath("//*[@id=\"start-date\"]"));
		WebElement EndDate = driver.findElement(By.xpath("//*[@id=\"end-date\"]"));
		String dateStart = "24-09-20182345";
		StartDate.sendKeys("20182345-09-25"); //Wrong format so will fail
		EndDate.sendKeys("002020-09-25");
		WebElement Filter = driver.findElement(By.id("filter-tran"));
		Filter.click();
		
		WebElement newtableEl = driver.findElement(By.cssSelector("#app-view-container table"));
		
		WebElement clickDate = newtableEl.findElement(By.xpath("//*[@id=\"app-view-container\"]/div[1]/div/div/div/div[1]/div/table/thead/tr/th[2]"));
		clickDate.click();
		WebElement newtablerow = newtableEl.findElement(By.xpath("//*[@id=\"app-view-container\"]/div[1]/div/div/div/div[1]/div/table/tbody/tr[1]"));
		WebElement newCellneed = newtablerow.findElement(By.xpath("//*[@id=\"app-view-container\"]/div[1]/div/div/div/div[1]/div/table/tbody/tr[1]/td[2]/span"));
		String NewDate = newCellneed.getText();
		NewDate = NewDate.replace("/", "-");
		SimpleDateFormat sdformat = new SimpleDateFormat("dd-MM-yyyy");
		Date d1 = sdformat.parse(dateStart);
		Date d2 = sdformat.parse(NewDate);
		System.out.println("The date 1 is: " + sdformat.format(d1));
		System.out.println("The date 2 is: " + sdformat.format(d2));
		

		// int NewStartDate = Integer.parseInt(NewDate);

		if(d2.compareTo(d1)>0){
			Tester = "Correct";
			
		}
		
		assertNotEquals(Tester, Correct);
	}


	@Test
	public void FilterAccountIDSuccessful() {

		login();

		WebElement Appheader = driver.findElement(By.id("app-header"));
		WebElement Headertabs = Appheader.findElement(By.id("header-tabs"));

		
		WebDriverWait wait3 = new WebDriverWait(driver, 10);
		wait3.until(ExpectedConditions.invisibilityOfElementLocated(By.id("entity-menu")));
		Headertabs.findElement(By.id("entity-menu")).click();
		

		driver.findElement(By.linkText("Accounts")).click();
		WebElement AccountId = driver.findElement(By.xpath("//*[@id=\"app-view-container\"]/div[1]/div/div/div/div[1]/input"));
		
		WebElement tableEl = driver.findElement(By.cssSelector("#app-view-container table"));
		WebElement tableRow = tableEl.findElement(By.xpath("//*[@id=\"app-view-container\"]/div[1]/div/div/div/div[2]/div/table/tbody/tr[1]"));
		WebElement Cellneed = tableRow.findElement(By.xpath("//*[@id=\"app-view-container\"]/div[1]/div/div/div/div[2]/div/table/tbody/tr[1]/td[1]/a"));
		String Accountid = Cellneed.getText();
		AccountId.sendKeys(Accountid);
		
		WebElement newtableEl = driver.findElement(By.cssSelector("#app-view-container table"));
		

		WebElement newtablerow = newtableEl.findElement(By.xpath("//*[@id=\"app-view-container\"]/div[1]/div/div/div/div[2]/div/table/tbody/tr[1]"));
		WebElement newCellneed = newtablerow.findElement(By.xpath("//*[@id=\"app-view-container\"]/div[1]/div/div/div/div[2]/div/table/tbody/tr[1]/td[1]/a"));
		String NewAccountid = newCellneed.getText();

		
		assertEquals(Accountid, NewAccountid);
	}


	@AfterEach
	public void tearDown() {
		driver.quit();
	}

	private boolean isElementPresent(By by) {
		return isElementPresent(driver, by);
	}

	private boolean isElementPresent(SearchContext context, By by) {
		try {
			context.findElement(by);
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}

	private void setDriver(String driverName) {
		driverName = driverName.toLowerCase();

		long timeout = 5;

		switch (driverName) {
			case "firefox":
				System.setProperty("webdriver.gecko.driver", "/opt/geckodriver");
				driver = new FirefoxDriver();
				driver.manage().window().maximize();
				break;
			case "chrome":
				System.setProperty("webdriver.chrome.driver", "/opt/chromedriver");
				ChromeOptions options = new ChromeOptions();
				options.addArguments("--start-maximized");
				driver = new ChromeDriver(options);
				break;
			default:
				driver = new HtmlUnitDriver();
				((HtmlUnitDriver) driver).setJavascriptEnabled(true);
				timeout = 20; // HtmlUnitDriver is slower than Firefox and Chrome
		}

		driver.manage().timeouts().implicitlyWait(timeout, TimeUnit.SECONDS);
		driver.manage().timeouts().setScriptTimeout(timeout, TimeUnit.SECONDS);
	}
}
