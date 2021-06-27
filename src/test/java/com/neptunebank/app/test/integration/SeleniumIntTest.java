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

import java.util.concurrent.TimeUnit;


import static org.junit.jupiter.api.Assertions.*;

@Tag("Integration")
public class SeleniumIntTest {
	private WebDriver driver;
	private String url;

	private final String validUsername = "lpaprocki";
	private final String validPassword = "feltzprintingservice";

	@BeforeEach
	public void setUp() {
		String browser = System.getProperty("integrationTest.browser", "chrome");
		setDriver(browser);
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--headless");
		WebDriver driver = new ChromeDriver(options);
		url = "http://localhost:4080";
		driver.get(url);
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
