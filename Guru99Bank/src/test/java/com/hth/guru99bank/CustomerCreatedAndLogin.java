package com.hth.guru99bank;

import java.io.IOException;

import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.hth.guru99bank.pages.*;

public class CustomerCreatedAndLogin {
	WebDriver driver;
	ManagerHomePage managerHomePage;
	AddCustomerPage addCustomerPage;
	HomePage homePage;
	
	@DataProvider(name="customer")
	public Object[][] getCustomerData() throws IOException {
		return Util.getData(1, "Customer");
	}
	
	@BeforeTest
	public void setUp() {
		driver = Util.logInSuccess(driver);
	}
	
	@Test(dataProvider="customer")
	public void verifyCustomerAccountCreated(String customerName, 
			String gender, String dateOfBirth, String address, String city, String state,
			String PIN, String mobileNumber, String email, 
			String password) throws InterruptedException {
		managerHomePage = new ManagerHomePage(driver);
		managerHomePage.clickLinkNewCustomer();
		
		addCustomerPage = new AddCustomerPage(driver);
		String dateOfBirthChromeFormat = dateOfBirth.substring(5, 7) + dateOfBirth.substring(8, 10) 
					+ dateOfBirth.substring(0, 4);
		if (driver instanceof FirefoxDriver) {
			addCustomerPage.fillDataAndSubmitNewCustomer(customerName, gender, dateOfBirth, 
				address, city, state, PIN, mobileNumber, email, password);
		} else if (driver instanceof ChromeDriver)	{
			addCustomerPage.fillDataAndSubmitNewCustomer(customerName, gender, dateOfBirthChromeFormat, 
					address, city, state, PIN, mobileNumber, email, password);
		}
		Thread.sleep(2000);
		String currentURL = driver.getCurrentUrl();
		String customerID = currentURL.substring(currentURL.length()-5);
		
		managerHomePage = new ManagerHomePage(driver);
		managerHomePage.clickLinkLogOut();
		Thread.sleep(2000);
		Alert alert = driver.switchTo().alert();
		alert.accept();
		Thread.sleep(2000);
		String expectedRedirectURL = "http://www.demo.guru99.com/V4/index.php";
		Assert.assertEquals(driver.getCurrentUrl(), expectedRedirectURL);
		
		homePage = new HomePage(driver);
		homePage.logInSuccess(customerID, password);
		
		Assert.assertTrue(driver.getPageSource().contains("Log out"));
	}
	
	@AfterTest
	public void tearDown() {
		driver.quit();
	}
}
