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

public class CustomerFundTransferAndCustomizedStatementAndBalanceEnquiry {
	private WebDriver driver;
	private CustomerHomePage customerHomePage;
	private CustomerFundTransferPage customerFundTransferPage;
	private CustomerFundTransferDetailsPage customerFundTransferDetailsPage;
	private CustomizedStatementInputPage customizedStatementInputPage;	
	
	@DataProvider(name="fund_transfer", indices={0})
	public Object[][] getDataCustomerFundTransfer() throws IOException {
		return Util.getData(1, "CustomerFundTransfer");
	}
	
	@DataProvider(name="fund_transfer_authorization", indices={1})
	public Object[][] getDataCustomerFundTransferAuthorization() throws IOException {
		return Util.getData(1, "CustomerFundTransfer");
	}
	
	@DataProvider(name="fund_transfer_not_existing_payer_and_payee", indices={2, 3})
	public Object[][] getDataCustomerFundTransferNotExistPayerAndPayee() throws IOException {
		return Util.getData(1, "CustomerFundTransfer");
	}
	
	@DataProvider(name="fund_transfer_same_payer_and_payee", indices={4})
	public Object[][] getDataCustomerFundTransferSamePayerAndPayee() throws IOException {
		return Util.getData(1, "CustomerFundTransfer");
	}
	
	
	@DataProvider(name="customer_customized_statement", indices= {0})
	public Object[][] getDataCustomerCustomizedStatement() throws IOException {
		return Util.getData(1, "CustomerCustomizedStatement");
	}
	
	@BeforeTest
	public void setUp() {
		driver = Util.customerLogInSuccess(driver);
	}
	
	@Test(priority=1, dataProvider="fund_transfer")
	public void verifyFundTransferSuccess(String payersAccountNo, String payeesAccountNo, 
			String amount, String description, String firstWrongAcc) {
		customerHomePage = new CustomerHomePage(driver);
		customerHomePage.clickLinkFundTransfer();
		
		customerFundTransferPage = new CustomerFundTransferPage(driver);
		customerFundTransferPage.fillInFundTransferInforAndSubmit(payersAccountNo, 
				payeesAccountNo, amount, description);
		
		customerFundTransferDetailsPage = new CustomerFundTransferDetailsPage(driver);
		String actualHeading = customerFundTransferDetailsPage.getHeading().getText();
		String expectedHeading = "Fund Transafer Details for Account No: " + payersAccountNo;
		Assert.assertEquals(actualHeading, expectedHeading);
		
		String[] expectedLabels = new String[] {"Transaction Id", "Amount", "FROM ACCOUNT NUMBER",
				"TO ACCOUNT NUMBER", "Description"};
		Assert.assertEquals(customerFundTransferDetailsPage.getLabels(), expectedLabels);
		
		// Assert Amount, FROM ACCOUNT NUMBER, TO ACCOUNT NUMBER, Description
		Assert.assertEquals(customerFundTransferDetailsPage.getValues()[1], amount);
		Assert.assertEquals(customerFundTransferDetailsPage.getValues()[2], payersAccountNo);
		Assert.assertEquals(customerFundTransferDetailsPage.getValues()[3], payeesAccountNo);
		Assert.assertEquals(customerFundTransferDetailsPage.getValues()[4], description);
	}
	
	@Test(priority=2, dataProvider="fund_transfer")
	public void verifyFundTransferNotDoneWhenReload(String payersAccountNo, String payeesAccountNo, 
			String amount, String description, String firstWrongAcc) throws InterruptedException {
		customerHomePage = new CustomerHomePage(driver);
		customerHomePage.clickLinkFundTransfer();
		
		customerFundTransferPage = new CustomerFundTransferPage(driver);
		customerFundTransferPage.fillInFundTransferInforAndSubmit(payersAccountNo, 
				payeesAccountNo, amount, description);
		Thread.sleep(2000);
		driver.navigate().refresh();
		Thread.sleep(2000);
		String currentURL = driver.getCurrentUrl();
		String expectedURL = "http://www.demo.guru99.com/V4/customer/customerfundinput.php";
		Assert.assertEquals(currentURL, expectedURL);
	}	
	
	@Test(priority=3, dataProvider="customer_customized_statement")
	public void verifyTransferDetailsOnCustomizedStatement(String accountNO, String fromDate, 
			String toDate, String minimumTransactionValue, String numberOfTransaction) throws InterruptedException, IOException {
		customerHomePage = new CustomerHomePage(driver);
		customerHomePage.clickLinkCustomisedStatement();
		
		customizedStatementInputPage = new CustomizedStatementInputPage(driver);
		customizedStatementInputPage.fillDataAndSubmitCustomisedStatementForm(accountNO, 
				Util.yesterday(driver), Util.now(driver), minimumTransactionValue, numberOfTransaction);
		Thread.sleep(2000);
		Util.takeScreenShot(driver, "CustomerCustomizedStatementDetails.png");
		System.out.println("Check screenshot /data/CustomerCustomizedStatementDetails.png");
	}
	
	@Test(priority=4, dataProvider="fund_transfer_authorization")
	public void verifyFundTransferViolatingPayerAuthorization(String payersAccountNo, 
			String payeesAccountNo, String amount, String description, String firstWrongAcc)
					throws InterruptedException {
		customerHomePage = new CustomerHomePage(driver);
		customerHomePage.clickLinkFundTransfer();
		
		customerFundTransferPage = new CustomerFundTransferPage(driver);
		customerFundTransferPage.fillInFundTransferInforAndSubmit(payersAccountNo, 
				payeesAccountNo, amount, description);
		Thread.sleep(2000);
		Alert alert = driver.switchTo().alert();
		String actualAlert = alert.getText();
		String expectedAlert = "You are not authorize to Transfer Funds from this account!!";
		Assert.assertEquals(actualAlert, expectedAlert);
		
		alert.accept();
		Thread.sleep(2000);
		String currentURL = driver.getCurrentUrl();
		String expectedRedirectURL = "http://www.demo.guru99.com/V4/customer/customerfundinput.php";
		Assert.assertEquals(currentURL, expectedRedirectURL);
	}	
	
	@Test(priority=5, dataProvider="fund_transfer_not_existing_payer_and_payee")
	public void verifyFundTransferPayerOrPayeeAccountNotExist(String payersAccountNo, 
			String payeesAccountNo, String amount, String description, String firstWrongAcc)
					throws InterruptedException {
		customerHomePage = new CustomerHomePage(driver);
		customerHomePage.clickLinkFundTransfer();
		
		customerFundTransferPage = new CustomerFundTransferPage(driver);
		customerFundTransferPage.fillInFundTransferInforAndSubmit(payersAccountNo, 
				payeesAccountNo, amount, description);
		Thread.sleep(2000);
		Alert alert = driver.switchTo().alert();
		String actualAlert = alert.getText();
		String expectedAlert = "Account " + firstWrongAcc + " does not exist!!!";
		Assert.assertEquals(actualAlert, expectedAlert);
		
		alert.accept();
		Thread.sleep(2000);
		String currentURL = driver.getCurrentUrl();
		String expectedRedirectURL = "http://www.demo.guru99.com/V4/customer/customerfundinput.php";
		Assert.assertEquals(currentURL, expectedRedirectURL);
	}
	
	@Test(priority=6, dataProvider="fund_transfer_same_payer_and_payee")
	public void verifyFundTransferSamePayerAndPayee(String payersAccountNo, 
			String payeesAccountNo, String amount, String description, String firstWrongAcc)
					throws InterruptedException {
		customerHomePage = new CustomerHomePage(driver);
		customerHomePage.clickLinkFundTransfer();
		
		customerFundTransferPage = new CustomerFundTransferPage(driver);
		customerFundTransferPage.fillInFundTransferInforAndSubmit(payersAccountNo, 
				payeesAccountNo, amount, description);
		Thread.sleep(2000);
		Alert alert = driver.switchTo().alert();
		String actualAlert = alert.getText();
		String expectedAlert = "Payers account No and Payees account No Must Not be Same!!!";
		Assert.assertEquals(actualAlert, expectedAlert);
		
		alert.accept();
		Thread.sleep(2000);
		String currentURL = driver.getCurrentUrl();
		String expectedRedirectURL = "http://www.demo.guru99.com/V4/customer/customerfundinput.php";
		Assert.assertEquals(currentURL, expectedRedirectURL);
	
	}
					
	@AfterTest
	public void tearDown() {
		driver.quit();
	}
}
