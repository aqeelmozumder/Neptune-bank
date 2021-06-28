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

import java.util.concurrent.TimeUnit;
import java.util.Date;
import java.util.Calendar;
import java.util.List;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Random;
import java.util.Collections;

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

	@BeforeEach
	public void setUp() {
		String browser = System.getProperty("integrationTest.browser", "firefox");
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

	private void login(String username, String password) {
		driver.findElement(By.cssSelector("nav.navbar li.nav-item > a#login-item")).click();

		WebElement formEl = driver.findElement(By.cssSelector("#login-page form"));

		formEl.findElement(By.id("username")).sendKeys(username);
		formEl.findElement(By.id("password")).sendKeys(password);

		formEl.findElement(By.cssSelector("button[type=submit]")).click();
	}

	// @Test
	// public void headerIsCorrect() throws Exception {
	// 	assertEquals("Neptune Bank", driver.getTitle());
	// }

	// @Test
	// public void logsUserWithCorrectCredentials() {
	// 	assertFalse(isElementPresent(By.id("account-menu"))); // starts logged out
	// 	Userlogin();
	// 	assertTrue(isElementPresent(By.id("account-menu")));
	// }

	// @Test
	// public void doesNotLogUserWithIncorrectCredentials() {
	// 	assertFalse(isElementPresent(By.id("account-menu"))); // starts logged out
	// 	login(validUsername, validPassword + "toMakeInvalid");
	// 	assertFalse(isElementPresent(By.id("account-menu")));
	// }

	/* As a regular site visitor, I would like to access and view public pages */
	// @Test
	// public void viewPublicPages() {
	// 	driver.findElement(By.linkText("About Us")).click();
	// 	assertEquals("Neptune Bank at a glance", driver.findElement(By.className("sheader")).getText());
	// 	driver.findElement(By.linkText("Contact Us")).click();
	// 	assertEquals("We're Here for You!", driver.findElement(By.tagName("h2")).getText());
	// 	driver.findElement(By.linkText("News & Updates")).click();
	// 	assertEquals("News & Updates", driver.findElement(By.tagName("h2")).getText());
	// }

	/* As a Customer, I would like to add a payee */
	// @Test
	// public void payeeIsAddedCorrectly() {
	// 	String firstName = "Lola";
	// 	String lastName = RandomStringUtils.randomAlphanumeric(8);
	// 	String email = (firstName + "@" + lastName + ".mail").toLowerCase();
	// 	String telephone = RandomStringUtils.randomNumeric(10);

	// 	Userlogin();
	// 	driver.findElement(By.linkText("Manage Payees")).click();

	// 	driver.findElement(By.id("jh-create-entity")).click();

	// 	WebElement formEl = driver.findElement(By.cssSelector("#app-view-container form"));

	// 	formEl.findElement(By.id("payee-emailID")).sendKeys(email);
	// 	formEl.findElement(By.id("payee-firstName")).sendKeys(firstName);
	// 	formEl.findElement(By.id("payee-lastName")).sendKeys(lastName);
	// 	formEl.findElement(By.id("payee-telephone")).sendKeys(telephone);

	// 	formEl.submit();

	// 	//after creation ensure the payee is in the list

	// 	WebElement tableEl = driver.findElement(By.cssSelector("#app-view-container table"));
	// 	assertTrue(isElementPresent(tableEl, By.xpath(".//td[text()='" + email + "']")), "Email of new payee not found in table");
	// }


	/* As a Customer, I would like to transfer amount to a beneficiary */
	// @Test //passed
	// public void transferToBeneficiarySuccessful() {
	// 	int transactionAmount = 10;

	// 	String fromAccount = performTransaction(transactionAmount, true, null);

	// 	Date date = Calendar.getInstance().getTime();  
	// 	DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");  
	// 	String strDate = dateFormat.format(date);  

	// 	//after transfer ensure the transaction is in the list
	// 	WebDriverWait wait = new WebDriverWait(driver,10);
	// 	wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.cssSelector("#app-view-container table"))));
	// 	WebElement tableEl = driver.findElement(By.cssSelector("#app-view-container table"));
	// 	assertTrue(areElementsPresentInTable(tableEl, By.xpath(".//span[text()='" + strDate + "']"),
	// 		By.xpath(".//td[text()='" + transactionAmount + "']"), By.xpath(".//a[text()='" + fromAccount + "']")));
	// }

	// @Test //passed
	// public void transferToBeneficiaryNegativeAmount() {
	// 	int transactionAmount = -10;

	// 	String fromAccount = performTransaction(transactionAmount, true, null);
		
	// 	Date date = Calendar.getInstance().getTime();  
	// 	DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");  
	// 	String strDate = dateFormat.format(date);  
	// 	//after transfer ensure the transaction is in the list
	// 	WebDriverWait wait = new WebDriverWait(driver,10);
	// 	wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.cssSelector("#app-view-container table"))));
	// 	WebElement tableEl = driver.findElement(By.cssSelector("#app-view-container table"));
	// 	assertTrue(areElementsPresentInTable(tableEl, By.xpath(".//span[text()='" + strDate + "']"),
	// 		By.xpath(".//td[text()='" + transactionAmount + "']"), By.xpath(".//a[text()='" + fromAccount + "']")));
	// }

	// @Test //passed
	// public void transferToBeneficiaryInvalidToAccount() {
	// 	int transactionAmount = 10;
	// 	String toAccount = "0";

	// 	performTransaction(transactionAmount, false, toAccount);

	// 	assertFalse(isElementPresent(By.id("transaction-heading")));
	// }

	/* As a Customer, I would like to have multiple accounts (Rquest a new account) */
	// @Test
	// public void multipleAccounts(){
	// 	Userlogin();
	// 	driver.findElement(By.linkText("Banking")).click();
	// 	driver.findElement(By.linkText("Accounts")).click();
	// 	driver.findElement(By.id("jh-create-entity")).click();
	// 	WebElement formEl = driver.findElement(By.cssSelector("#app-view-container form"));

	// 	Select AccountTypeSelect = new Select(formEl.findElement(By.id("accounts-accountType")));
	// 	AccountTypeSelect.getFirstSelectedOption().getAttribute("value");
	// 	formEl.findElement(By.id("accounts-balance")).sendKeys(String.valueOf(100));
	// 	Select CustomerTypeSelect = new Select(formEl.findElement(By.id("accounts-customerID")));
	// 	CustomerTypeSelect.selectByVisibleText("lpaprocki");
	// 	Select BranchTypeSelect = new Select(formEl.findElement(By.id("accounts-branchID")));
	// 	BranchTypeSelect.selectByVisibleText("90 Thorburn Ave");

	// 	driver.findElement(By.id("save-entity")).click();

	// 	//after Request for a new account ensure the account is in the list
		
	// }


	/* As a staff member, I want to look up customer details */
	// @Test //passed
	// public void lookUpCustomerDetails() {
	// 	Stafflogin();
	// 	driver.findElement(By.id("entity-menu")).click();
	// 	driver.findElement(By.xpath("//a[@href='/entity/customer']")).click();
	// 	WebDriverWait wait = new WebDriverWait(driver,10);
	// 	wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath(".//table"))));
	// 	WebElement tableEl = driver.findElement(By.xpath(".//table"));
	// 	List<WebElement> tableRows = tableEl.findElements(By.tagName("tr"));
	// 	assertTrue(tableRows.size() > 0);
	// 	WebElement firstRow = tableRows.get(0);;
	// 	firstRow.findElement(By.xpath("//a[text()='1']")).click();
	// 	assertTrue(isElementPresent(By.className("jh-entity-details")));
		
	// 	WebElement details = driver.findElement(By.className("jh-entity-details"));
	// 	assertTrue(isElementPresent(details, By.id("firstName")));
	// 	assertTrue(isElementPresent(details, By.id("lastName")));
	// 	assertTrue(isElementPresent(details, By.id("telephone")));
	// 	assertTrue(isElementPresent(details, By.id("gender")));
	// 	assertTrue(isElementPresent(details, By.id("address")));
	// 	assertTrue(isElementPresent(details, By.id("city")));
	// 	assertTrue(isElementPresent(details, By.id("state")));
	// 	assertTrue(isElementPresent(details, By.id("zipCode")));
	// }


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
				//firefox_options.setHeadless(true);
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
