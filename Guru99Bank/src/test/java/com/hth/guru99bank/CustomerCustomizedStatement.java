package com.hth.guru99bank;

import java.io.IOException;

import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.hth.guru99bank.pages.*;

public class CustomerCustomizedStatement {
	private WebDriver driver;
	private CustomerHomePage customerHomePage;
	private CustomizedStatementInputPage customizedStatementInputPage;
	
	@DataProvider(name="customer_customized_statement", indices= {1})
	public Object[][] getDataCustomerCustomizedStatement() throws IOException {
		return Util.getData(1, "CustomerCustomizedStatement");
	}
	
	@DataProvider(name="customer_customized_statement_wrong_acc", indices= {2})
	public Object[][] getDataCustomerCustomizedStatementWrongAccount() throws IOException {
		return Util.getData(1, "CustomerCustomizedStatement");
	}
	
	@DataProvider(name="customer_customized_statement_toDate_lower_than_fromDate", indices= {0})
	public Object[][] getDataCustomerCustomizedStatementToDateLowerThanFromDate() throws IOException {
		return Util.getData(1, "CustomerCustomizedStatement");
	}
	
	@BeforeTest
	public void setUp() {
		driver = Util.customerLogInSuccess(driver);
	}
	
	@Test(priority=1, dataProvider="customer_customized_statement")
	public void verifyCustomerCanSeeCustomizedStatementOfOnlyHisAccount(String accountNO, String fromDate, 
			String toDate, String minimumTransactionValue, String numberOfTransaction) throws InterruptedException {
		customerHomePage = new CustomerHomePage(driver);
		customerHomePage.clickLinkCustomisedStatement();
		
		customizedStatementInputPage = new CustomizedStatementInputPage(driver);
		customizedStatementInputPage.fillDataAndSubmitCustomisedStatementForm(accountNO, Util.yesterday(driver), 
				Util.now(driver), minimumTransactionValue, numberOfTransaction);
		Thread.sleep(2000);
		
		Alert alert = driver.switchTo().alert();
		String actualAlert = alert.getText();
		String expectedAlert = "You are not authorize to generate statement of this Account!!";
		Assert.assertEquals(actualAlert, expectedAlert);
		
		alert.accept();
		Thread.sleep(2000);
	}
	
	@Test(priority=2, dataProvider="customer_customized_statement_wrong_acc")
	public void verifyWrongAccountEnteredInCustomerCustomizedStatement(String accountNO, String fromDate, 
			String toDate, String minimumTransactionValue, String numberOfTransaction) throws InterruptedException {
		customerHomePage = new CustomerHomePage(driver);
		customerHomePage.clickLinkCustomisedStatement();
		
		customizedStatementInputPage = new CustomizedStatementInputPage(driver);
		customizedStatementInputPage.fillDataAndSubmitCustomisedStatementForm(accountNO, Util.yesterday(driver), 
				Util.now(driver), minimumTransactionValue, numberOfTransaction);
		Thread.sleep(2000);
		
		Alert alert = driver.switchTo().alert();
		String actualAlert = alert.getText();
		String expectedAlert = "Account does not exist";
		Assert.assertEquals(actualAlert, expectedAlert);
		
		alert.accept();
		Thread.sleep(2000);
	}
	
	@Test(priority=3, dataProvider="customer_customized_statement_toDate_lower_than_fromDate")
	public void verifyToDateLowerThanFromDateInCustomizedStatement(String accountNO, String fromDate, 
			String toDate, String minimumTransactionValue, String numberOfTransaction) throws InterruptedException {
		customerHomePage = new CustomerHomePage(driver);
		customerHomePage.clickLinkCustomisedStatement();
		
		customizedStatementInputPage = new CustomizedStatementInputPage(driver);
		customizedStatementInputPage.fillDataAndSubmitCustomisedStatementForm(accountNO, Util.now(driver), 
				Util.yesterday(driver), minimumTransactionValue, numberOfTransaction);
		Thread.sleep(2000);
		
		Alert alert = driver.switchTo().alert();
		String actualAlert = alert.getText();
		String expectedAlert = "FromDate field should be lower than ToDate field!!";
		Assert.assertEquals(actualAlert, expectedAlert);
		
		alert.accept();
		Thread.sleep(2000);
	}
	
	@AfterTest
	public void tearDown() {
		driver.quit();
	}
}
