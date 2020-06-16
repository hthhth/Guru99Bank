package com.hth.guru99bank;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.hth.guru99bank.pages.CustomizedStatementInputPage;
import com.hth.guru99bank.pages.FundTransferInputPage;
import com.hth.guru99bank.pages.HomePage;
import com.hth.guru99bank.pages.ManagerHomePage;

import static com.hth.guru99bank.Util.*;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List; 

public class FundTransferAndCustomizedStatement {
	WebDriver driver;
	
	@BeforeTest
	public void setUp() {
		driver = SetUp.setUp();
		driver.get(BASE_URL);
		driver.manage().window().maximize();
		HomePage homePage = new HomePage(driver);
		homePage.logInSuccess(USER_NAME, PASSWORD);
	}
	
	@DataProvider(name="fund_transfer", indices={0})
	public Object[][] getDataFundTransfer() throws IOException{
		String projectPath = System.getProperty("user.dir");
		ExcelUtils excel = new ExcelUtils(projectPath + "/Data/Data.xlsx", "FundTransfer");
		Object[][] data = new Object[excel.getRowCount()-1][excel.getColCount()];
		for (int i=0; i<excel.getRowCount()-1; i++) {
			for (int j=0; j<excel.getColCount(); j++) {
				data[i][j]=excel.getCellDataString(i+1, j);
			}
		}
		return data;
	}
	
	@DataProvider(name="fund_transfer_with_wrong_account_no", indices={1})
	public Object[][] getDataFundTransferWrongAccount() throws IOException{
		return getDataFundTransfer();
	}
	
	@DataProvider(name="fund_transfer_from_not_authorized_account_no", indices={2})
	public Object[][] getDataFundTransferFromNotAuthorizedAccount() throws IOException{
		return getDataFundTransfer();
	}
	
	@DataProvider(name="fund_transfer_for_same_account_no", indices={3})
	public Object[][] getDataFundTransferForSameAccount() throws IOException{
		return getDataFundTransfer();
	}
	
	@DataProvider(name="fund_transfer_when_account_balance_is_low", indices={4})
	public Object[][] getDataFundTransferWhenAccountBalanceLow() throws IOException{
		return getDataFundTransfer();
	}
	
	
	@DataProvider(name="customisedstatement")
	public Object[][] getDataCustomisedStatement() throws IOException{
		String projectPath = System.getProperty("user.dir");
		ExcelUtils excel = new ExcelUtils(projectPath + "/Data/Data.xlsx", "CustomisedStatement");
		Object[][] data = new Object[excel.getRowCount()-1][excel.getColCount()];
		for (int i=0; i<excel.getRowCount()-1; i++) {
			for (int j=0; j<excel.getColCount(); j++) {
				data[i][j]=excel.getCellDataString(i+1, j);
			}
		}
		return data;
	}
	
	@Test(priority=0, dataProvider="fund_transfer")
	public void verifyFundTransferSuccess(String payerAccount, String payeeAccount, String amount,
			String descscription) throws InterruptedException {
		ManagerHomePage managerHomePage = new ManagerHomePage(driver);
		managerHomePage.clickLinkFundTransfer();
		FundTransferInputPage fundTransferInputPage = new FundTransferInputPage(driver);
		fundTransferInputPage.fillDataAndSubmitFundTransfer(payerAccount, payeeAccount, amount, descscription);
		
		Assert.assertTrue(driver.getPageSource().contains("Fund Transfer Details"));
		Assert.assertTrue(driver.getPageSource().contains("From Account Number"));
		Assert.assertTrue(driver.getPageSource().contains(payerAccount));
		Assert.assertTrue(driver.getPageSource().contains("To Account Number"));
		Assert.assertTrue(driver.getPageSource().contains(payeeAccount));
		Assert.assertTrue(driver.getPageSource().contains("Amount"));
		Assert.assertTrue(driver.getPageSource().contains(amount));
		Assert.assertTrue(driver.getPageSource().contains("Description"));
		Assert.assertTrue(driver.getPageSource().contains(descscription));
	}
	
	@Test(priority=1)
	public void veriryFundTransferNotDoneAgainWhenPageReload() {
		driver.navigate().refresh();
		String currentURL = driver.getCurrentUrl();
		String expectedURL = "http://www.demo.guru99.com/V4/manager/FundTransInput.php";
		Assert.assertEquals(currentURL, expectedURL);
	}
	
	@Test(priority=2, dataProvider="customisedstatement")
	public void verifyTransferDetailsAppearOnCustomizedStatement(String accountNO, 
			String minimumTransactionValue, String numberOfTransaction) throws InterruptedException, IOException {
		ManagerHomePage managerHomePage = new ManagerHomePage(driver);
		managerHomePage.clickLinkCustomisedStatement();
		CustomizedStatementInputPage customisedStatementInputPage = new CustomizedStatementInputPage(driver);
	
		String fromDate = Util.yesterday(driver);
		String toDate = Util.now(driver);
		customisedStatementInputPage.fillDataAndSubmitCustomisedStatementForm(accountNO, fromDate, toDate, minimumTransactionValue, numberOfTransaction);
		
		//Verify heading text "Transaction Details for Account No..."
		String displayText = driver.findElement(By.className("heading3")).getText();
		String expectedText="";
		DateTimeFormatter formatterChrome = DateTimeFormatter.ofPattern("MMddyyyy"); 
		if (driver instanceof FirefoxDriver) {
			expectedText = "Transaction Details for Account No: " + accountNO + " from Date: " + fromDate + " to: " + toDate;
		} else if (driver instanceof ChromeDriver) {
			expectedText = "Transaction Details for Account No: " + accountNO + " from Date: " + LocalDate.parse(fromDate, formatterChrome) + " to: " + LocalDate.parse(toDate, formatterChrome);
		}
		Assert.assertEquals(displayText, expectedText);
		
		//Print table content to console 
		WebElement table = driver.findElement(By.xpath("//table[@id='customstmt']"));
		List<WebElement> rows = table.findElements(By.tagName("tr"));
		for (int i=0; i<rows.size()-1; i++) {
			List<WebElement> cells = rows.get(i).findElements(By.xpath("./*"));
			for (WebElement cell : cells) {
				System.out.print(cell.getText() + " | ");
			}
			System.out.println();
		}

		//Take screenshot
		String projectPath = System.getProperty("user.dir");
		File screenShotFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(screenShotFile, new File(projectPath + "/data/CustomisedStatement.png"));
	}
	
	@Test(priority=3, dataProvider="fund_transfer_with_wrong_account_no")
	public void verifySystemWhenWrongAccountEnteredDuringFundTransfer(String payerAccount, 
			String payeeAccount, String amount,
			String descscription) throws InterruptedException {
		ManagerHomePage managerHomePage = new ManagerHomePage(driver);
		managerHomePage.clickLinkFundTransfer();
		FundTransferInputPage fundTransferInputPage = new FundTransferInputPage(driver);
		fundTransferInputPage.fillDataAndSubmitFundTransfer(payerAccount, payeeAccount, amount, descscription);
		
		Alert alert = driver.switchTo().alert();
		String alertMessage = alert.getText();
		String expectedMessage = "Account " + payerAccount + " does not exist!!!";
		alert.accept();
		Assert.assertEquals(alertMessage, expectedMessage);
	}
	
	@Test(priority=4, dataProvider="fund_transfer_from_not_authorized_account_no")
	public void verifySystemWhenFundTransferFromNotAuthorizedAccount(String payerAccount, 
			String payeeAccount, String amount,
			String descscription) throws InterruptedException {
		ManagerHomePage managerHomePage = new ManagerHomePage(driver);
		managerHomePage.clickLinkFundTransfer();
		FundTransferInputPage fundTransferInputPage = new FundTransferInputPage(driver);
		fundTransferInputPage.fillDataAndSubmitFundTransfer(payerAccount, payeeAccount, amount, descscription);
		
		Alert alert = driver.switchTo().alert();
		String alertMessage = alert.getText();
		String expectedMessage = "You are not authorize to Transfer Funds from this account!!";
		alert.accept();
		Assert.assertEquals(alertMessage, expectedMessage);
	}
	
	@Test(priority=5, dataProvider="fund_transfer_for_same_account_no")
	public void verifyFundTransferForSameAccountNumbers(String payerAccount, 
			String payeeAccount, String amount,
			String descscription) throws InterruptedException {
		ManagerHomePage managerHomePage = new ManagerHomePage(driver);
		managerHomePage.clickLinkFundTransfer();
		FundTransferInputPage fundTransferInputPage = new FundTransferInputPage(driver);
		fundTransferInputPage.fillDataAndSubmitFundTransfer(payerAccount, payeeAccount, amount, descscription);
		
		Alert alert = driver.switchTo().alert();
		String alertMessage = alert.getText();
		String expectedMessage = "Payers account No and Payees account No Must Not be Same!!!";
		alert.accept();
		Assert.assertEquals(alertMessage, expectedMessage);
	}
	
	@Test(priority=6, dataProvider="fund_transfer_when_account_balance_is_low")
	public void verifyFundTransferWhenAccountBalanceLow(String payerAccount, 
			String payeeAccount, String amount,
			String descscription) throws InterruptedException {
		ManagerHomePage managerHomePage = new ManagerHomePage(driver);
		managerHomePage.clickLinkFundTransfer();
		FundTransferInputPage fundTransferInputPage = new FundTransferInputPage(driver);
		fundTransferInputPage.fillDataAndSubmitFundTransfer(payerAccount, payeeAccount, amount, descscription);
		
		Alert alert = driver.switchTo().alert();
		String alertMessage = alert.getText();
		String expectedMessage = "Transfer Failed. Account Balance low!!";
		alert.accept();
		Assert.assertEquals(alertMessage, expectedMessage);
	}
	
	@AfterTest
	public void tearDown() {
		driver.quit();
	}
}
