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

public class WithdrawalAndMiniStatementAndBalanceEnquiry {
	private WebDriver driver;
	private ManagerHomePage managerHomePage;
	private BalancePage balancePage;
	private BalanceDetailsPage balanceDetailsPage;
	private WithdrawalInputPage withdrawalInputPage;
	private WithdrawalDetailsPage withdrawalDetailsPage;
	private MiniStatementForm miniStatementForm;
	private DepositInputPage depositInputPage;
	@BeforeTest
	public void setUp() {
		driver = Util.logInSuccess(driver);
	}
	
	@DataProvider(name="withdrawal", indices= {0})
	public Object[][] getData() throws IOException {
		return Util.getData(1, "Withdrawal");
	}
	
	@DataProvider(name="withdrawal_amount_more_than_current_amount", indices= {1})
	public Object[][] getDataWithdrawLargeAmount() throws IOException {
		return Util.getData(1, "Withdrawal");
	}
	
	@DataProvider(name="mini_statement")
	public Object[][] getDataMiniStatement() throws IOException {
		return Util.getData(1, "MiniStatement");
	}
	
	@DataProvider(name="balance")
	public Object[][] getDataBalance() throws IOException {
		return Util.getData(1, "Balance");
	}
	
	@Test(priority=0, dataProvider="withdrawal")
	public void verifyWithdrawal(String accountNo, String amount, String description) throws InterruptedException {
		managerHomePage = new ManagerHomePage(driver);
		managerHomePage.clickLinkBalanceEnquiry();
		
		balancePage = new BalancePage(driver);
		balancePage.fillAccountNoAndSubmit(accountNo);
		
		// calculate expected balance after withdrawal
		balanceDetailsPage = new BalanceDetailsPage(driver);
		String balance = balanceDetailsPage.getBalance();
		double balanceAfterWithdrawal = Double.parseDouble(balance) - Double.parseDouble(amount);
		
		managerHomePage.clickLinkWithdrawal();
		withdrawalInputPage = new WithdrawalInputPage(driver);
		withdrawalInputPage.fillDataAndSubmit(accountNo, amount, description);
		
		withdrawalDetailsPage = new WithdrawalDetailsPage(driver);
		String expectedText = "Transaction details of Withdrawal for Account " + accountNo;
		String actualText = withdrawalDetailsPage.getSuccessText();
		Assert.assertEquals(actualText, expectedText);
		
		String[] expectedLabels = {"Transaction ID", "Account No", "Amount Debited", 
				"Type of Transaction", "Description", "Current Balance"};
		String[] actualLabels = withdrawalDetailsPage.getLabels();
		Assert.assertEquals(actualLabels, expectedLabels);
		
		String[] actualValues = withdrawalDetailsPage.getValues();
		// Assert Account No, Amount Debited, Type of Transaction, Description, Current Balance
		Assert.assertEquals(actualValues[1], accountNo);
		Assert.assertEquals(actualValues[2], amount);
		Assert.assertEquals(actualValues[3], "Withdrawal"); 
		Assert.assertEquals(actualValues[4], description);
		Assert.assertEquals(Double.parseDouble(actualValues[5]), balanceAfterWithdrawal); 
		
	}
	
	@Test(priority=1, dataProvider="withdrawal_amount_more_than_current_amount")
	public void withdrawAmountMoreThanCurrentAmount(String accountNo, String amount, 
			String description) throws InterruptedException {
		managerHomePage = new ManagerHomePage(driver);
		managerHomePage.clickLinkWithdrawal();
		
		withdrawalInputPage = new WithdrawalInputPage(driver);
		withdrawalInputPage.fillDataAndSubmit(accountNo, amount, description);
		
		Alert alert = driver.switchTo().alert();
		String expectedAlert = "Transaction Failed. Account Balance Low!!!";
		Assert.assertEquals(alert.getText(), expectedAlert);
		
		alert.accept();
		Thread.sleep(2000);
		Assert.assertEquals(driver.getCurrentUrl(), "http://www.demo.guru99.com/V4/manager/WithdrawalInput.php");
		
	}
	
	@Test(priority=2, dataProvider="mini_statement")
	public void verifyMiniStatement(String accountNo) throws InterruptedException, IOException {
		managerHomePage.clickLinkMiniStatement();
		
		miniStatementForm = new MiniStatementForm(driver);
		miniStatementForm.fillAccountNoAndSubmit(accountNo);
		
		Util.takeScreenShot(driver, "MiniStatement.png");
		System.out.println("Check screenshot at /data/MiniStatement.png");
	}
	
	@Test(priority=3, dataProvider="balance")
	public void verifyBalance(String accountNo, String depositAmt, String withdrawalAmt) throws InterruptedException {
		// get original balance
		managerHomePage = new ManagerHomePage(driver);
		managerHomePage.clickLinkBalanceEnquiry();
		
		balancePage = new BalancePage(driver);
		balancePage.fillAccountNoAndSubmit(accountNo);
		
		balanceDetailsPage = new BalanceDetailsPage(driver);
		String originalBalance = balanceDetailsPage.getBalance();
		
		// deposit
		managerHomePage.clickLinkDeposit();
		depositInputPage = new DepositInputPage(driver);
		depositInputPage.fillInDepositAndSubmit(accountNo, depositAmt, "deposit " + depositAmt);
		
		// get balance after deposit
		managerHomePage.clickLinkBalanceEnquiry();
		balancePage.fillAccountNoAndSubmit(accountNo);
		String afterDepositBalance = balanceDetailsPage.getBalance();
		
		// check result
		Assert.assertEquals(Double.parseDouble(afterDepositBalance), 
				Double.parseDouble(originalBalance)+Double.parseDouble(depositAmt));
		
		// withdraw
		managerHomePage.clickLinkWithdrawal();
		withdrawalInputPage = new WithdrawalInputPage(driver);
		withdrawalInputPage.fillDataAndSubmit(accountNo, withdrawalAmt, "withdraw " + withdrawalAmt);
		
		// get balance after withdraw
		managerHomePage.clickLinkBalanceEnquiry();
		balancePage.fillAccountNoAndSubmit(accountNo);
		String afterWithdrawalBalance = balanceDetailsPage.getBalance();
		
		// check result
		Assert.assertEquals(Double.parseDouble(afterWithdrawalBalance), 
				Double.parseDouble(afterDepositBalance)-Double.parseDouble(withdrawalAmt));
		
		
	}
	@AfterTest
	public void tearDown() {
		driver.quit();
	}
}
