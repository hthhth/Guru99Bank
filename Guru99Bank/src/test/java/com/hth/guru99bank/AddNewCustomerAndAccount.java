package com.hth.guru99bank;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.hth.guru99bank.pages.AddAccountPage;
import com.hth.guru99bank.pages.AddCustomerPage;
import com.hth.guru99bank.pages.HomePage;
import com.hth.guru99bank.pages.ManagerHomePage;

import static com.hth.guru99bank.Util.*;

public class AddNewCustomerAndAccount {
	WebDriver driver;
	String customerID, accountID;
	ManagerHomePage managerHomePage;
	
	@DataProvider(name="CustomerData")
	public Object[][] getCustomerData() throws IOException{
		String projectPath = System.getProperty("user.dir");
		ExcelUtils excel = new ExcelUtils(projectPath + "/Data/Data.xlsx", "Sheet2");
		Object[][] data = new Object[excel.getRowCount()-2][10];
		for (int i=0; i<excel.getRowCount()-2; i++) {
			for (int j=0; j<10; j++) {
				data[i][j] = excel.getCellDataString(i+2, j);
			}	
		}
		return data;
	}
	
	@DataProvider(name="AccountData")
	public Object[][] getAccountData() throws IOException{
		String projectPath = System.getProperty("user.dir");
		ExcelUtils excel = new ExcelUtils(projectPath + "/Data/Data.xlsx", "Sheet2");
		Object[][] data = new Object[excel.getRowCount()-2][4];
		for (int i=0; i<excel.getRowCount()-2; i++) {
			for (int j=0; j<2; j++) {
				data[i][j] = excel.getCellDataString(i+2, j+10);
			}
			data[i][2] = excel.getCellDataString(i+2, 0);
			data[i][3] = excel.getCellDataString(i+2, 8);
		}
		return data;
	}
	@BeforeTest
	public void setUp() {
		driver = Util.logInSuccess(driver);
	}
	
	
	@Test(priority=0, dataProvider="CustomerData")
	public void verifyAfterAddingNewCustomerPageRedirectsToDetailsOfAddedCustomer(String customerName, 
			String gender, String dateOfBirth, String address, String city, String state,
			String PIN, String mobileNumber, String email, 
			String password) throws InterruptedException {
		managerHomePage = new ManagerHomePage(driver);
		managerHomePage.clickLinkNewCustomer();
		AddCustomerPage addCustomerPage = new AddCustomerPage(driver);
		addCustomerPage.fillDataAndSubmitNewCustomer(customerName, gender,
				dateOfBirth, address, city, state, PIN,
				mobileNumber, email, password);
		
		String currentURL = driver.getCurrentUrl();
		customerID = currentURL.substring(currentURL.length()-5);
		System.out.println(customerID);
		
		Assert.assertTrue(currentURL.contains("http://www.demo.guru99.com/V4/manager/CustomerRegMsg.php"));
		Assert.assertTrue(driver.getPageSource().contains(customerName));
		Assert.assertTrue(driver.getPageSource().contains(gender));
		if (driver instanceof ChromeDriver) {
			Assert.assertTrue(driver.getPageSource().contains(dateOfBirth.substring(4, 8) + "-"
							+ dateOfBirth.substring(0, 2) + "-" + dateOfBirth.substring(2, 4)));
		} else if (driver instanceof FirefoxDriver) {
			Assert.assertTrue(driver.getPageSource().contains(dateOfBirth.substring(0, 4) + "-"
							+ dateOfBirth.substring(5, 7) + "-" + dateOfBirth.substring(8, 10)));
		}
		Assert.assertTrue(driver.getPageSource().contains(address));
		Assert.assertTrue(driver.getPageSource().contains(city));
		Assert.assertTrue(driver.getPageSource().contains(state));
		Assert.assertTrue(driver.getPageSource().contains(PIN));
		Assert.assertTrue(driver.getPageSource().contains(mobileNumber));
		Assert.assertTrue(driver.getPageSource().contains(email));
		
	}
	@Test(priority=1, dataProvider="AccountData")
	public void verifyANewAccountCanBeAddedToNewCustomer(String accountType, String initialDeposit,
			String customerName, String email) throws InterruptedException {
//		managerHomePage = ManagerHomePage.getInstance(driver);
		managerHomePage.clickLinkNewAccount();
		AddAccountPage addAccountPage = new AddAccountPage(driver);
		addAccountPage.fillDataAndSubmitNewAccount(customerID, accountType, initialDeposit);
		
		String currentURL = driver.getCurrentUrl();
		accountID = currentURL.substring(currentURL.length()-5);
		System.out.println(accountID);
		
		Assert.assertTrue(currentURL.contains("http://www.demo.guru99.com/V4/manager/AccCreateMsg.php"));
		Assert.assertTrue(driver.getPageSource().contains(customerID));
		Assert.assertTrue(driver.getPageSource().contains(customerName));
		Assert.assertTrue(driver.getPageSource().contains(email));
		if (accountType.equalsIgnoreCase("savings")) {
			Assert.assertTrue(driver.getPageSource().contains("Savings"));
		} else if (accountType.equalsIgnoreCase("current")) {
			Assert.assertTrue(driver.getPageSource().contains("Current"));
		} 	
		
		Date date = new Date();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String currentDate = dateFormat.format(date);
		Assert.assertTrue(driver.getPageSource().contains(currentDate));
		
		Assert.assertTrue(driver.getPageSource().contains(initialDeposit));
		
		
	}
//	@AfterTest
//	public void tearDown() {
//		driver.quit();
//	}
}
