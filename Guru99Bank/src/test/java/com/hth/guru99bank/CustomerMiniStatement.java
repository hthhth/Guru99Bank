package com.hth.guru99bank;

import java.io.IOException;

import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.hth.guru99bank.pages.CustomerHomePage;
import com.hth.guru99bank.pages.CustomerMiniStatementDetails;
import com.hth.guru99bank.pages.CustomerMiniStatementForm;

public class CustomerMiniStatement {
	private WebDriver driver;
	private CustomerHomePage customerHomePage;
	private CustomerMiniStatementForm customerMiniStatementForm;
	private CustomerMiniStatementDetails customerMiniStatementDetails;
	
	@DataProvider(name="customer_mini_statement", indices= {0})
	public Object[][] getDataCustomerMiniStatement() throws IOException {
		return Util.getData(1, "CustomerMiniStatement");
	}
	
	@DataProvider(name="customer_mini_statement_permission", indices= {1})
	public Object[][] getDataCustomerMiniStatementPermission() throws IOException {
		return Util.getData(1, "CustomerMiniStatement");
	}
	
	@DataProvider(name="customer_mini_statement_wrong_account_no", indices= {2})
	public Object[][] getDataCustomerMiniStatementWrongAccount() throws IOException {
		return Util.getData(1, "CustomerMiniStatement");
	}
	
	
	@BeforeTest
	public void setUp() {
		driver = Util.customerLogInSuccess(driver);
	}
	
	@Test(priority=1, dataProvider="customer_mini_statement")
	public void verifyTransferDetailsAppearOnMiniStatement(String accountNo) 
			throws IOException, InterruptedException {
		
		customerHomePage = new CustomerHomePage(driver);
		customerHomePage.clickMiniStatement();
		
		customerMiniStatementForm = new CustomerMiniStatementForm(driver);
		customerMiniStatementForm.fillAccountAndSubmit(accountNo);
		Thread.sleep(2000);
		Util.takeScreenShot(driver, "CustomerMiniStatementDetails.png");
		System.out.println("Check screenshot /data/CustomerMiniStatementDetails.png");
		
		customerMiniStatementDetails = new CustomerMiniStatementDetails(driver);
		
		String expectedHeading = "Last Five Transaction Details for Account No: " + accountNo;
		String actualHeading = customerMiniStatementDetails.getHeading().getText();
		
		Assert.assertEquals(actualHeading, expectedHeading);
	}
	
	@Test(priority=2, dataProvider="customer_mini_statement_permission")
	public void verifyCustomerCanSeeMiniStatementOfOnlyHisAccount(String accountNo) 
			throws InterruptedException {
		customerHomePage = new CustomerHomePage(driver);
		customerHomePage.clickMiniStatement();
		
		customerMiniStatementForm = new CustomerMiniStatementForm(driver);
		customerMiniStatementForm.fillAccountAndSubmit(accountNo);
		Thread.sleep(2000);
		
		Alert alert = driver.switchTo().alert();
		String actualAlert = alert.getText();
		String expectedAlert = "You are not authorize to generate statement of this Account!!";
		Assert.assertEquals(actualAlert, expectedAlert);
		
		alert.accept();
		Thread.sleep(2000);
	}
	
	@Test(priority=3, dataProvider="customer_mini_statement_wrong_account_no")
	public void verifyWrongAccountNoIsEnteredInMiniStatement(String accountNo) 
			throws InterruptedException {
		customerHomePage = new CustomerHomePage(driver);
		customerHomePage.clickMiniStatement();
		
		customerMiniStatementForm = new CustomerMiniStatementForm(driver);
		customerMiniStatementForm.fillAccountAndSubmit(accountNo);
		Thread.sleep(2000);
		
		Alert alert = driver.switchTo().alert();
		String actualAlert = alert.getText();
		String expectedAlert = "Account does not exist";
		Assert.assertEquals(actualAlert, expectedAlert);
		
		alert.accept();
		Thread.sleep(2000);
	}
	
	@AfterTest
	public void tearDown() {
		driver.quit();
	}
	
}
