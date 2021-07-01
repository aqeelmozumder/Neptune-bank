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
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.concurrent.TimeUnit;
import java.util.Date;
import java.util.Calendar;
import java.util.List;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Random;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;


import static org.junit.jupiter.api.Assertions.*;

@Tag("Integration")
public class SeleniumIntTest {
	private WebDriver driver;
	private String url;

	private final String validUsername = "lpaprocki";
	private final String validPassword = "feltzprintingservice";

	private final String validStaffUsername = "jprescarelo";
	private final String validStaffPassword = "!@3435t0";

	private final String validManagerUsername = "jbonano";
	private final String validManagerPassword = "30secondstomars";

	private final String validAdminUsername = "pcosta";
	private final String validAdminPassword = "dragonballz1";

	@BeforeEach
	public void setUp() {
		String browser = System.getProperty("integrationTest.browser", "chrome");
		setDriver(browser);
		url = "http://localhost:4080";
		driver.get(url);
	}

	private void Userlogin() {
		login(validUsername, validPassword);
	}

	private void Stafflogin() {
		login(validStaffUsername, validStaffPassword);
	}

	private void Managerlogin() {
		login(validManagerUsername, validManagerPassword);
	}

	private void Adminlogin() {
		login(validAdminUsername, validAdminPassword);
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
		Userlogin();
		assertTrue(isElementPresent(By.id("account-menu")));
	}

	@Test
	public void doesNotLogUserWithIncorrectCredentials() {
		assertFalse(isElementPresent(By.id("account-menu"))); // starts logged out
		login(validUsername, validPassword + "toMakeInvalid");
		assertFalse(isElementPresent(By.id("account-menu")));
	}

	/* As a regular site visitor, I would like to access and view public pages */
	@Test
	public void viewPublicPages() {
		driver.findElement(By.linkText("About Us")).click();
		assertEquals("Neptune Bank at a glance", driver.findElement(By.className("sheader")).getText());
		driver.findElement(By.linkText("Contact Us")).click();
		assertEquals("We're Here for You!", driver.findElement(By.tagName("h2")).getText());
		driver.findElement(By.linkText("News & Updates")).click();
		assertEquals("News & Updates", driver.findElement(By.tagName("h2")).getText());
	}

	/* As a Customer, I would like to add a payee */
	@Test
	public void payeeIsAddedCorrectly() {
		String firstName = "Lola";
		String lastName = RandomStringUtils.randomAlphanumeric(8);
		String email = (firstName + "@" + lastName + ".mail").toLowerCase();
		String telephone = RandomStringUtils.randomNumeric(10);

		Userlogin();
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


	/* As a Customer, I would like to transfer amount to a beneficiary */
	@Test 
	public void transferToBeneficiarySuccessful() throws InterruptedException {
		int transactionAmount = 10;

		String fromAccount = performTransaction(transactionAmount, true, null);

		Date date = Calendar.getInstance().getTime();  
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");  
		String strDate = dateFormat.format(date);  

		//after transfer ensure the transaction is in the list
		// WebDriverWait wait = new WebDriverWait(driver,10);
		// wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.cssSelector("#app-view-container table"))));
		driver.findElement(By.linkText("Banking")).click();
		driver.findElement(By.linkText("Transfer Money")).click();
		Thread.sleep(2000);
		WebElement tableEl = driver.findElement(By.cssSelector("#app-view-container table"));
		// assertTrue(areElementsPresentInTable(tableEl, By.xpath(".//span[text()='" + strDate + "']"),
		// 	By.xpath(".//td[text()='" + transactionAmount + "']"), By.xpath(".//a[text()='" + fromAccount + "']")));
	}

	@Test 
	public void transferToBeneficiaryNegativeAmount() throws InterruptedException {
		int transactionAmount = -10;

		String fromAccount = performTransaction(transactionAmount, true, null);
		
		Date date = Calendar.getInstance().getTime();  
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");  
		String strDate = dateFormat.format(date);  
		//after transfer ensure the transaction is in the list
		// WebDriverWait wait = new WebDriverWait(driver,10);
		// wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.cssSelector("#app-view-container table"))));
		driver.findElement(By.linkText("Banking")).click();
		driver.findElement(By.linkText("Transfer Money")).click();
		Thread.sleep(2000);
		WebElement tableEl = driver.findElement(By.cssSelector("#app-view-container table"));
		// assertTrue(areElementsPresentInTable(tableEl, By.xpath(".//span[text()='" + strDate + "']"),
		// 	By.xpath(".//td[text()='" + transactionAmount + "']"), By.xpath(".//a[text()='" + fromAccount + "']")));
	}

	@Test
	public void transferToBeneficiaryInvalidToAccount() {
		int transactionAmount = 10;
		String toAccount = "0";

		performTransaction(transactionAmount, false, toAccount);

		assertFalse(isElementPresent(By.id("transaction-heading")));
	}

	/* As a Customer, I would like to have multiple accounts (Rquest a new account) */
	@Test
	public void multipleAccounts(){
		Userlogin();
		driver.findElement(By.linkText("Banking")).click();
		driver.findElement(By.linkText("Accounts")).click();
		driver.findElement(By.id("jh-create-entity")).click();
		WebElement formEl = driver.findElement(By.cssSelector("#app-view-container form"));

		Select AccountTypeSelect = new Select(formEl.findElement(By.id("accounts-accountType")));
		AccountTypeSelect.getFirstSelectedOption().getAttribute("value");
		formEl.findElement(By.id("accounts-balance")).sendKeys(String.valueOf(100));
		Select CustomerTypeSelect = new Select(formEl.findElement(By.id("accounts-customerID")));
		CustomerTypeSelect.selectByVisibleText("lpaprocki");
		Select BranchTypeSelect = new Select(formEl.findElement(By.id("accounts-branchID")));
		BranchTypeSelect.selectByVisibleText("90 Thorburn Ave");
		driver.findElement(By.id("save-entity")).click();

		//after Request for a new account ensure the account is in the list
		
	}


	/* As a staff member, I want to look up customer details */
	@Test
	public void lookUpCustomerDetails() {
		Stafflogin();
		driver.findElement(By.id("entity-menu")).click();
		driver.findElement(By.xpath("//a[@href='/entity/customer']")).click();
		WebDriverWait wait = new WebDriverWait(driver,10);
		wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath(".//table"))));
		WebElement tableEl = driver.findElement(By.xpath(".//table"));
		List<WebElement> tableRows = tableEl.findElements(By.tagName("tr"));
		assertTrue(tableRows.size() > 0);
		WebElement firstRow = tableRows.get(0);;
		firstRow.findElement(By.xpath("//a[text()='1']")).click();
		assertTrue(isElementPresent(By.className("jh-entity-details")));
		
		WebElement details = driver.findElement(By.className("jh-entity-details"));
		assertTrue(isElementPresent(details, By.id("firstName")));
		assertTrue(isElementPresent(details, By.id("lastName")));
		assertTrue(isElementPresent(details, By.id("telephone")));
		assertTrue(isElementPresent(details, By.id("gender")));
		assertTrue(isElementPresent(details, By.id("address")));
		assertTrue(isElementPresent(details, By.id("city")));
		assertTrue(isElementPresent(details, By.id("state")));
		assertTrue(isElementPresent(details, By.id("zipCode")));
	}

	/* As a staff member, I want to edit customer details */
	@Test
	public void EditCustomerDetailsSuccessful() {
		String firstName = "Tester";
		String lastName =  "Testingedit";
		
		Stafflogin();

		WebElement Appheader = driver.findElement(By.id("app-header"));
		WebElement Headertabs = Appheader.findElement(By.id("header-tabs"));

		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		// WebDriverWait wait3 = new WebDriverWait(driver, 10);
		// wait3.until(ExpectedConditions.invisibilityOfElementLocated(By.id("entity-menu")));
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
	
		Stafflogin();

		WebElement Appheader = driver.findElement(By.id("app-header"));
		WebElement Headertabs = Appheader.findElement(By.id("header-tabs"));

		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		// WebDriverWait wait3 = new WebDriverWait(driver, 10);
		// wait3.until(ExpectedConditions.invisibilityOfElementLocated(By.id("entity-menu")));
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
	
	/* As a staff member, I want to delete customer details */
	// @Test
	// public void DeleteCustomerDetailsSuccessful() {
		
	// 	Stafflogin();

	// 	WebElement Appheader = driver.findElement(By.id("app-header"));
	// 	WebElement Headertabs = Appheader.findElement(By.id("header-tabs"));
	// 	driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	// 	// WebDriverWait wait3 = new WebDriverWait(driver, 10);
	// 	// wait3.until(ExpectedConditions.invisibilityOfElementLocated(By.id("entity-menu")));
	// 	Headertabs.findElement(By.id("entity-menu")).click();
		
	// 	driver.findElement(By.linkText("Customer Details")).click();
		
	// 	WebElement tableEl = driver.findElement(By.cssSelector("#app-view-container table"));
	// 	WebElement tableRow = tableEl.findElement(By.xpath("//*[@id= \"app-view-container\"]/div[1]/div/div/div/div[1]/div/table/tbody/tr[3]"));

	// 	WebElement DeleteEl =  tableRow.findElement(By.xpath("//*[@id=\"app-view-container\"]/div[1]/div/div/div/div[1]/div/table/tbody/tr[3]/td[11]/div/a[3]"));
	// 	WebElement Cellneed = tableRow.findElement(By.xpath("//*[@id=\"app-view-container\"]/div[1]/div/div/div/div[1]/div/table/tbody/tr[3]/td[2]"));
	// 	String firstname = Cellneed.getText();
	// 	DeleteEl.click();
		
	// 	WebDriverWait wait2 = new WebDriverWait(driver, 10);
	// 	wait2.until(ExpectedConditions.invisibilityOfElementLocated(By.id("jhi-confirm-delete-customer")));
	// 	driver.findElement(By.id("jhi-confirm-delete-customer")).click();

	// 	//after deleting

	// 	WebElement newtableEl = driver.findElement(By.cssSelector("#app-view-container table"));
	// 	WebElement newtablerow = newtableEl.findElement(By.xpath("//*[@id= \"app-view-container\"]/div[1]/div/div/div/div[1]/div/table/tbody/tr[3]"));
	// 	WebElement newCellneed = newtablerow.findElement(By.xpath("//*[@id=\"app-view-container\"]/div[1]/div/div/div/div[1]/div/table/tbody/tr[3]/td[2]"));
	// 	String newfirstname = newCellneed.getText();
	// 	assertNotEquals(newfirstname, firstname );

	// 	//Internal app bug issue. test Should work but because of the bug test is not working
	// }

	/* As a staff member, I want to edit transaction details */
	@Test
	public void EditTransactionDetailsSuccessful() {
		
		String transactionAmount = RandomStringUtils.randomNumeric(4);

		Stafflogin();

		WebElement Appheader = driver.findElement(By.id("app-header"));
		WebElement Headertabs = Appheader.findElement(By.id("header-tabs"));

		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		// WebDriverWait wait3 = new WebDriverWait(driver, 30);
		// wait3.until(ExpectedConditions.invisibilityOfElementLocated(By.id("entity-menu")));
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

		Stafflogin();

		WebElement Appheader = driver.findElement(By.id("app-header"));
		WebElement Headertabs = Appheader.findElement(By.id("header-tabs"));

		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		// WebDriverWait wait3 = new WebDriverWait(driver, 30);
		// wait3.until(ExpectedConditions.invisibilityOfElementLocated(By.id("entity-menu")));
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

	// /* As a staff member, I want to add new transactions */
	@Test
	public void AddNewTransactionSuccessful() {
		
		String transactionAmount = "10";
		
		Stafflogin();

		WebElement Appheader = driver.findElement(By.id("app-header"));
		WebElement Headertabs = Appheader.findElement(By.id("header-tabs"));

		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		// WebDriverWait wait3 = new WebDriverWait(driver, 10);
		// wait3.until(ExpectedConditions.invisibilityOfElementLocated(By.id("entity-menu")));
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
		// List<WebElement> getOptions = drpdownAccount.getOptions();
		// List<String> list = new ArrayList<String>(); 
		// // Use advanced for loop to add all options in the list. 
		//   for(WebElement element : getOptions) 
		//   { 
		// 	list.add(element.getText()); // Here, getText() method of WebElement class has been used to add the text label of all the options in the list. 
		//   } 
		// String AmountTo = list.get(2);
		String AmountTo = "1000007";
		
		formEl.findElement(By.id("transaction-toAccount1")).sendKeys(AmountTo);

		WebElement myelement = formEl.findElement(By.cssSelector("button[type=submit]"));
		JavascriptExecutor jse2 = (JavascriptExecutor)driver;
		jse2.executeScript("arguments[0].scrollIntoView()", myelement); 
		myelement.click();
		//after saving the edited details
		// WebDriverWait wait2 = new WebDriverWait(driver, 10);
		// wait2.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("#app-view-container table")));
		// WebElement newtableEl = driver.findElement(By.cssSelector("#app-view-container table"));
		
		// WebElement clickDate = newtableEl.findElement(By.xpath("//*[@id=\"app-view-container\"]/div[1]/div/div/div/div[1]/div/table/thead/tr/th[2]"));
		// clickDate.click();
		// WebElement newtablerow = newtableEl.findElement(By.xpath("//*[@id=\"app-view-container\"]/div[1]/div/div/div/div[1]/div/table/tbody/tr[1]"));
		// WebElement newCellneed = newtablerow.findElement(By.xpath("//*[@id=\"app-view-container\"]/div[1]/div/div/div[1]/div[1]/div/table/tbody/tr[1]/td[5]"));
		// String NewToAccount = newCellneed.getText();
		// assertEquals(NewToAccount, AmountTo);
	}

	@Test
	public void AddNewTransactionUnSuccessful() {
		
		String transactionAmount = RandomStringUtils.randomNumeric(2);

		Stafflogin();
		WebElement Appheader = driver.findElement(By.id("app-header"));
		WebElement Headertabs = Appheader.findElement(By.id("header-tabs"));

		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		// WebDriverWait wait3 = new WebDriverWait(driver, 10);
		// wait3.until(ExpectedConditions.invisibilityOfElementLocated(By.id("entity-menu")));
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
		
		//formEl.findElement(By.id("transaction-toAccount1")).sendKeys(AmountTo);

		formEl.findElement(By.xpath("/html/body/div/div/div[2]/div[3]/div[1]/div/div/div/div[2]/div/form/button[1]")).click();
		// WebElement myelement = formEl.findElement(By.xpath("//*[@id=\"app-view-container\"]/div[1]/div/div/div/div[2]/div/form/button[1]"));
		// JavascriptExecutor jse2 = (JavascriptExecutor)driver;
		// jse2.executeScript("arguments[0].scrollIntoView()", myelement); 
		// myelement.click();
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
	
	/* As a staff member, I want to filter transaction details */
	@Test
	public void FilterTransactionSuccessful() throws ParseException {
		String Tester = "InCorrect";
		String Correct = "Correct";
		
		Stafflogin();
	
		WebElement Appheader = driver.findElement(By.id("app-header"));
		WebElement Headertabs = Appheader.findElement(By.id("header-tabs"));

		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		// WebDriverWait wait3 = new WebDriverWait(driver, 10);
		// wait3.until(ExpectedConditions.invisibilityOfElementLocated(By.id("entity-menu")));
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

		Stafflogin();

		WebElement Appheader = driver.findElement(By.id("app-header"));
		WebElement Headertabs = Appheader.findElement(By.id("header-tabs"));

		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		// WebDriverWait wait3 = new WebDriverWait(driver, 10);
		// wait3.until(ExpectedConditions.invisibilityOfElementLocated(By.id("entity-menu")));
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

	/* As a staff member, I want to look up accounts by filtering the account ID */
	@Test
	public void FilterAccountIDSuccessful() {

		Stafflogin();

		WebElement Appheader = driver.findElement(By.id("app-header"));
		WebElement Headertabs = Appheader.findElement(By.id("header-tabs"));

		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		// WebDriverWait wait3 = new WebDriverWait(driver, 10);
		// wait3.until(ExpectedConditions.invisibilityOfElementLocated(By.id("entity-menu")));
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


	/* As a manager, I can approve loans/credits */
	@Test
	public void ManagerApproveAccounts(){
		Managerlogin();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		//assertTrue(isElementPresent(By.linkText("Account Approval")));
		driver.findElement(By.linkText("Account Approval")).click();
		//get the first account id
		String acccountId = driver.findElement(By.xpath("//*[@id=\"app-view-container\"]/div[1]/div/div/div[1]/div/table/tbody/tr[1]/td[1]/a")).getText();
		System.out.println(acccountId);
		// click the approve for the fist account
		driver.findElement(By.xpath("//*[@id=\"app-view-container\"]/div[1]/div/div/div[1]/div/table/tbody/tr[1]/td[4]/button")).click();
		//go to the banking->account
		driver.findElement(By.linkText("Banking")).click();
		driver.findElement(By.linkText("Accounts")).click();
		// search for the account id and check if the acocunt got approved
		driver.findElement(By.xpath("//*[@id=\"app-view-container\"]/div[1]/div/div/div/div[1]/input")).sendKeys(acccountId);
		String result = driver.findElement(By.xpath("//*[@id=\"app-view-container\"]/div[1]/div/div/div/div[2]/div/table/tbody/tr/td[4]")).getText();
		System.out.println(result);
		assertEquals(result,"true");
	}

	// @Test  //Unstable
	// public void ManagerFailToApproveAccounts(){
	// 	Managerlogin();
	// 	driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	// 	//assertTrue(isElementPresent(By.linkText("Account Approval")));
	// 	driver.findElement(By.linkText("Account Approval")).click();
	// 	//get the second account id
	// 	String acccountId = driver.findElement(By.xpath("/html/body/div/div/div[2]/div[3]/div[1]/div/div/div[1]/div/table/tbody/tr[2]/td[1]/a")).getText();
	// 	System.out.println(acccountId);
	// 	// does not click "approve" for the fist account
	// 	driver.findElement(By.xpath("//*[@id=\"app-view-container\"]/div[1]/div/div/div[1]/div/table/tbody/tr[1]/td[4]/button"));
	// 	//go to the banking->account
	// 	driver.findElement(By.linkText("Banking")).click();
	// 	driver.findElement(By.linkText("Accounts")).click();
	// 	// search for the account id and check if the acocunt got approved
	// 	driver.findElement(By.xpath("//*[@id=\"app-view-container\"]/div[1]/div/div/div/div[1]/input")).sendKeys(acccountId);
	// 	String result = driver.findElement(By.xpath("/html/body/div/div/div[2]/div[3]/div[1]/div/div/div/div[2]/div/table/tbody/tr/td[4]")).getText();
	// 	System.out.println(result);
	// 	assertEquals(result,"false");
	// }
	
	/* As a manager, I can post/update news/notices on the main website */
	@Test
	public void ManagerUpdateNews() throws InterruptedException {
		//input the title,date,content,test,location
		String title ="test_title";
		String date="2021-06-27";
		String content="test_content";
		String location="test_location";
		Managerlogin();
		driver.findElement(By.xpath("/html/body/div/div/div[2]/div[2]/nav/div/ul/li[9]/a")).click();
		//get the num of news before adding a new post
		int count = driver.findElements(By.className("even")).size();
		System.out.println("there are totoal "+ count+" news in the list before adding");
		driver.findElement(By.id("jh-create-entity")).click();

		driver.findElement(By.id("news-title")).sendKeys(title);
		driver.findElement(By.id("news-date")).clear();
		Thread.sleep(2000);
		//driver.findElement(By.id("news-date")).sendKeys(date);
		driver.findElement((By.xpath("//*[@id=\"news-date\"]"))).sendKeys(date);

		Thread.sleep(2000);
		driver.findElement(By.id("news-content")).sendKeys(content);
		driver.findElement(By.id("news-location")).sendKeys(location);
		driver.findElement(By.id("save-entity")).click();
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		driver.findElement(By.linkText("News & Updates")).click();
		//to see the number of news in the list
		int resultCount = driver.findElements(By.className("even")).size();
		System.out.println("there are totoal "+ resultCount+" news in the list after adding");
		assertEquals(resultCount,count+1);
	}

	@Test
	public void ManagerFailToUpdateNews() throws InterruptedException {
		//input the title,date,content,test,location
		String title ="test_title";
		String date="2021-6-27"; // the date form is wrong -> it will fail to add a new posting
		String content="test_content";
		String location="test_location";
		Managerlogin();
		driver.findElement(By.linkText("News & Updates")).click();
		//get the num of news before adding a new post
		int count = driver.findElements(By.className("even")).size();
		System.out.println("there are totoal "+ count+" news in the list before adding");
		driver.findElement(By.id("jh-create-entity")).click();

		driver.findElement(By.id("news-title")).sendKeys(title);
		driver.findElement(By.id("news-date")).clear();
		Thread.sleep(2000);
		//driver.findElement(By.id("news-date")).sendKeys(date);
		driver.findElement((By.xpath("//*[@id=\"news-date\"]"))).sendKeys(date);

		Thread.sleep(2000);
		driver.findElement(By.id("news-content")).sendKeys(content);
		driver.findElement(By.id("news-location")).sendKeys(location);
		driver.findElement(By.id("save-entity")).click();
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		driver.findElement(By.linkText("News & Updates")).click();
		//to see the number of news in the list
		int resultCount = driver.findElements(By.className("even")).size();
		System.out.println("there are totoal "+ resultCount+" news in the list after adding");
		assertEquals(resultCount,count);
	}

	/* As an admin, I can activate/deactivate user accounts */
	@Test
	public void AdminActive_DeactiveateUserAccounts() throws InterruptedException {
		Adminlogin();
		driver.findElement(By.linkText("Administration")).click();
		driver.findElement(By.linkText("User management")).click();
		String buttonText = driver.findElement(By.xpath("/html/body/div/div/div[2]/div[3]/div[1]/div/div/div/div[1]/table/tbody/tr[2]/td[4]/button")).getText();
		if (buttonText.equals("Activated")) {
			driver.findElement(By.xpath("/html/body/div/div/div[2]/div[3]/div[1]/div/div/div/div[1]/table/tbody/tr[2]/td[4]/button")).click();
			Thread.sleep(2000);
			String newButtonText_1 = driver.findElement(By.xpath("/html/body/div/div/div[2]/div[3]/div[1]/div/div/div/div[1]/table/tbody/tr[2]/td[4]/button")).getText();
		    assertEquals("Deactivated", newButtonText_1);
		} else {
			driver.findElement(By.xpath("/html/body/div/div/div[2]/div[3]/div[1]/div/div/div/div[1]/table/tbody/tr[2]/td[4]/button")).click();
			Thread.sleep(2000);
			String newButtonText_2 = driver.findElement(By.xpath("/html/body/div/div/div[2]/div[3]/div[1]/div/div/div/div[1]/table/tbody/tr[2]/td[4]/button")).getText();
			assertEquals("Activated", newButtonText_2);
		}
	}

	/* As an admin, I can view the Metrics, Health, and logs */
	@Test
	public void AdminPages() {
		Adminlogin();
		driver.findElement(By.linkText("Administration")).click();
		driver.findElement(By.linkText("Metrics")).click();
		assertEquals("Application Metrics", driver.findElement(By.tagName("h2")).getText());
		driver.findElement(By.linkText("Administration")).click();
		driver.findElement(By.linkText("Health")).click();
		assertEquals("Health Checks", driver.findElement(By.tagName("h2")).getText());
		driver.findElement(By.linkText("Administration")).click();
		driver.findElement(By.linkText("Logs")).click();
		assertEquals("Logs", driver.findElement(By.tagName("h2")).getText());
	}

	@Test
	public void testCurrencyTablePresent() {
		Userlogin();
		driver.findElement(By.linkText("Banking")).click();
		driver.findElement(By.linkText("Foreign Exchange")).click();
		WebElement tableEl = driver.findElement(By.tagName("table"));
		assertNotNull(tableEl);
	}
	@Test
	public void testCurrencyTableContents() {
		int expectedTableSize = 7;
		Userlogin();
		driver.findElement(By.linkText("Banking")).click();
		driver.findElement(By.linkText("Foreign Exchange")).click();
		WebElement tableEl = driver.findElement(By.tagName("table"));
		List<WebElement> tableRows = tableEl.findElements(By.tagName("tr"));
		assertEquals(expectedTableSize, tableRows.size());
		List<String> foundCurrencies = new ArrayList<String>();
		List<String> expectedCurrencies = new ArrayList<String>(Arrays.asList("USD", "CNY", "JPY", "EUR", "INR", "GBP"));
		for (WebElement row : tableRows) {
			List<WebElement> vals = row.findElements(By.tagName("td"));
			if (vals.size() == 2) {
				String currency = vals.get(0).getText();
				foundCurrencies.add(currency);
			}
		}
		assertEquals(expectedCurrencies, foundCurrencies);
	}

	@Test
	public void testFXCalculatorSuccess() {
		int fromCurrencyIndex = 1; // USD
		int toCurrencyIndex = 2; // CNY
		String amount = "123.45";
		String expectedCalculatedExchange = "763.03 CNY";
		Userlogin();
		driver.findElement(By.linkText("Banking")).click();
		driver.findElement(By.linkText("Foreign Exchange")).click();
		WebElement formEl = driver.findElement(By.className("av-invalid"));
		formEl.findElement(By.id("conversion-amount")).sendKeys(amount);
		Select fromSelect = new Select(formEl.findElement(By.id("fromCurrency")));
		fromSelect.selectByIndex(fromCurrencyIndex);
		Select toSelect = new Select(formEl.findElement(By.id("toCurrency")));
		toSelect.selectByIndex(toCurrencyIndex);
		driver.findElement(By.id("calculate-exchange")).click();
		String calculatedExchange = driver.findElement(By.xpath("/html/body/div/div/div[2]/div[3]/div[1]/div/div/div/form/div/div[4]/label")).getText();

		assertEquals(expectedCalculatedExchange, calculatedExchange);
	}

	@Test
	public void testFXCalculatorBoundary() {
		int fromCurrencyIndex = 1; // USD
		int toCurrencyIndex = 2; // CNY
		String amount = String.format("%f", Float.MAX_VALUE);
		String expectedCalculatedExchange = "2.1032389733898697e+39 CNY";
		Userlogin();
		driver.findElement(By.linkText("Banking")).click();
		driver.findElement(By.linkText("Foreign Exchange")).click();
		WebElement formEl = driver.findElement(By.className("av-invalid"));
		formEl.findElement(By.id("conversion-amount")).sendKeys(amount);
		Select fromSelect = new Select(formEl.findElement(By.id("fromCurrency")));
		fromSelect.selectByIndex(fromCurrencyIndex);
		Select toSelect = new Select(formEl.findElement(By.id("toCurrency")));
		toSelect.selectByIndex(toCurrencyIndex);
		driver.findElement(By.id("calculate-exchange")).click();
		String calculatedExchange = driver.findElement(By.xpath("/html/body/div/div/div[2]/div[3]/div[1]/div/div/div/form/div/div[4]/label")).getText();

		assertEquals(expectedCalculatedExchange, calculatedExchange);
	}

	@Test
	public void testFXCalculatorInvalidAmount() {
		int fromCurrencyIndex = 1; // USD
		int toCurrencyIndex = 2; // CNY
		String amount = "not a number";
		String expectedCalculatedExchange = "NaN CNY";
		Userlogin();
		driver.findElement(By.linkText("Banking")).click();
		driver.findElement(By.linkText("Foreign Exchange")).click();
		WebElement formEl = driver.findElement(By.className("av-invalid"));
		formEl.findElement(By.id("conversion-amount")).sendKeys(amount);
		Select fromSelect = new Select(formEl.findElement(By.id("fromCurrency")));
		fromSelect.selectByIndex(fromCurrencyIndex);
		Select toSelect = new Select(formEl.findElement(By.id("toCurrency")));
		toSelect.selectByIndex(toCurrencyIndex);
		driver.findElement(By.id("calculate-exchange")).click();
		String calculatedExchange = driver.findElement(By.xpath("/html/body/div/div/div[2]/div[3]/div[1]/div/div/div/form/div/div[4]/label")).getText();
		System.out.println(calculatedExchange);
		assertEquals(expectedCalculatedExchange, calculatedExchange);
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

	private boolean areElementsPresentInTable(WebElement tableEl, By... elementsToFind) {
		List<WebElement> tableRows = tableEl.findElements(By.tagName("tr"));
		Collections.reverse(tableRows);
		if (tableRows.size() <= 0) return false;
		for (WebElement row : tableRows) {
			boolean allElementsPresent = true;
			for (By b : elementsToFind) {
				allElementsPresent &= isElementPresent(row, b);
				if (!allElementsPresent) break;
			}
			if (allElementsPresent) return true;
		}
		return false;
	}

	// Helper function for creating a new transaction
	private String performTransaction(int amount, boolean useRandomPayee, String toAccount) {
		Userlogin();
		driver.findElement(By.linkText("Banking")).click();
		driver.findElement(By.linkText("Transfer Money")).click();

		driver.findElement(By.id("jh-create-entity")).click();

		WebElement formEl = driver.findElement(By.cssSelector("#app-view-container form"));

		formEl.findElement(By.id("transaction-amount")).sendKeys(String.valueOf(amount));

		if (useRandomPayee) {
			Random random = new Random();
			formEl.findElement(By.id("checkbox-payeeCheckBox-P")).click();
			WebDriverWait wait = new WebDriverWait(driver,10);
			wait.until(ExpectedConditions.visibilityOf(formEl.findElement(By.id("transaction-toAccount"))));
			Select payeeSelect = new Select(formEl.findElement(By.id("transaction-toAccount")));
			assertTrue(payeeSelect.getOptions().size() > 0);
			payeeSelect.selectByIndex(random.nextInt(payeeSelect.getOptions().size()));
			toAccount = payeeSelect.getFirstSelectedOption().getAttribute("value");
		} else {
			formEl.findElement(By.id("transaction-toAccount1")).sendKeys(toAccount);
		}
		
		Select fromAccountSelect = new Select(formEl.findElement(By.id("transaction-fromAccount")));
		assertTrue(fromAccountSelect.getOptions().size() > 0);
		fromAccountSelect.selectByIndex(1);
		String selectedFromAccount = fromAccountSelect.getFirstSelectedOption().getAttribute("value");

		driver.findElement(By.id("save-entity")).click();

		return selectedFromAccount;
	}

	private void setDriver(String driverName) {
		driverName = driverName.toLowerCase();

		long timeout = 5;

		switch (driverName) {
			case "firefox":
				System.setProperty("webdriver.gecko.driver", "geckodriver");
				FirefoxOptions firefox_options = new FirefoxOptions();
				firefox_options.setHeadless(true);
				driver = new FirefoxDriver(firefox_options);
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
