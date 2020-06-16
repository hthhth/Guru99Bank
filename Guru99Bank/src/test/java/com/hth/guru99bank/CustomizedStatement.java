package com.hth.guru99bank;

import java.io.IOException;
import java.time.LocalDate;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.hth.guru99bank.pages.*;

public class CustomizedStatement {
	private WebDriver driver;
	private ManagerHomePage managerHomePage;
	private CustomizedStatementInputPage customizedStatementInputPage;
	private CustomisedStatementPage customisedStatementPage;
	
	@DataProvider(name="customized_statement")
	public Object[][] getDataCustomizedStatement() throws IOException {
		return Util.getData(1, "CustomizedStatement");
	}
	
	@BeforeTest
	public void setUp() {
		driver = Util.logInSuccess(driver);
	}
	
	@Test(priority=1, dataProvider="customized_statement")
	public void verifyCustomizedStatement(String accountNO, String fromDate,
			 String toDate, String minimumTransactionValue, String numberOfTransaction) 
					 throws InterruptedException, IOException {
		managerHomePage = new ManagerHomePage(driver);
		managerHomePage.clickLinkCustomisedStatement();
		
		customizedStatementInputPage = new CustomizedStatementInputPage(driver);
		customizedStatementInputPage.fillDataAndSubmitCustomisedStatementForm(accountNO, 
				Util.yesterday(driver), Util.now(driver), minimumTransactionValue, 
				numberOfTransaction);
		
		Thread.sleep(2000);
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scroll(0, 100)");
		
		Util.takeScreenShot(driver, "CustomizedStatement.png");
		System.out.println("Check screenshot at /Data/CustomizedStatement.png"); 
	}
	
	@Test(priority=2, dataProvider="customized_statement")
	public void verifyCustomizedStatmentNotGeneratedWhenFromDateGreaterThanToDate(String accountNO, 
			String fromDate, String toDate, String minimumTransactionValue, 
			String numberOfTransaction) throws InterruptedException, IOException {
		managerHomePage = new ManagerHomePage(driver);
		managerHomePage.clickLinkCustomisedStatement();
		
		customizedStatementInputPage = new CustomizedStatementInputPage(driver);
		customizedStatementInputPage.fillDataAndSubmitCustomisedStatementForm(accountNO, 
				Util.now(driver), Util.yesterday(driver), minimumTransactionValue, 
				numberOfTransaction);
		
		Alert alert = driver.switchTo().alert();
		String actualText = alert.getText();
		Thread.sleep(2000);
		alert.accept();
		Thread.sleep(2000);
		String expectedDate = "FromDate field should be lower than ToDate field!!";
		
		Assert.assertEquals(actualText, expectedDate);
	}	
	
	@Test(priority=3, dataProvider="customized_statement")
	public void verifyCustomizedStatmentGeneratedWhenFromDateLessThanToDate(String accountNO, 
			String fromDate, String toDate, String minimumTransactionValue, String numberOfTransaction) 
					throws InterruptedException, IOException {
		managerHomePage = new ManagerHomePage(driver);
		managerHomePage.clickLinkCustomisedStatement();
		
		customizedStatementInputPage = new CustomizedStatementInputPage(driver);
		customizedStatementInputPage.fillDataAndSubmitCustomisedStatementForm(accountNO, 
				Util.formatDate(fromDate, driver), Util.now(driver), minimumTransactionValue, 
				numberOfTransaction);
		
		customisedStatementPage = new CustomisedStatementPage(driver);
		String expectedText = "Transaction Details for Account No: " + accountNO + 
				" from Date: " + fromDate + " to: " + LocalDate.now();
		String actualText = customisedStatementPage.getHeading();
		
		Assert.assertEquals(actualText, expectedText);
		
		Thread.sleep(2000);
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scroll(0, 100)");
		
		Util.takeScreenShot(driver, "CustomizedStatement2.png");
		System.out.println("Check screenshot at /Data/CustomizedStatement2.png"); 
	}
	
	@AfterTest
	public void tearDown() {
		driver.quit();
	}
}
