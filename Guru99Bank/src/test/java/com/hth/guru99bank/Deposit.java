package com.hth.guru99bank;


import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.hth.guru99bank.pages.DepositInputPage;
import com.hth.guru99bank.pages.DepositDetails;
import com.hth.guru99bank.pages.ManagerHomePage;

public class Deposit {
	WebDriver driver;
	
	@BeforeTest
	public void setUp() {
		driver = Util.logInSuccess(driver);
	}
	
	@DataProvider(name="deposit")
	public Object[][] getData() throws IOException {
		return Util.getData(1, "Deposit");
	}
	
	@Test(dataProvider="deposit", priority=0)
	public void deposit(String accountNo, String amount, String description) throws InterruptedException {
		ManagerHomePage managerHomePage = new ManagerHomePage(driver);
		managerHomePage.clickLinkDeposit();
		
		DepositInputPage depositInputPage = new DepositInputPage(driver);
		depositInputPage.fillInDepositAndSubmit(accountNo, amount, description);
		
		DepositDetails depositTransactionDetails = new DepositDetails(driver);
		
		String expectedText = "Transaction details of Deposit for Account " + accountNo;
		Assert.assertTrue(driver.getPageSource().contains(expectedText));
		
		String[] expectedLabels = {"Transaction ID", "Account No", "Amount Credited", 
				"Type of Transaction", "Description", "Current Balance"};
		String[] actualLabels = depositTransactionDetails.getActualLabels();
		Assert.assertEquals(actualLabels, expectedLabels);
	}
	
	@Test(dataProvider="deposit", priority=1)
	public void reloadAfterDeposit(String accountNo, String amount, String description) throws InterruptedException {
		ManagerHomePage managerHomePage = new ManagerHomePage(driver);
		managerHomePage.clickLinkDeposit();
		
		DepositInputPage depositInputPage = new DepositInputPage(driver);
		depositInputPage.fillInDepositAndSubmit(accountNo, amount, description);
		
		WebDriverWait wait = new WebDriverWait(driver, 5);
		wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//*[contains(text(), 'Transaction details of Deposit for Account')]"))));
		driver.navigate().refresh();
		Assert.assertTrue(wait.until(ExpectedConditions.urlToBe("http://www.demo.guru99.com/V4/manager/DepositInput.php")));
				
	}

	@AfterTest
	public void tearDown() {
		driver.quit();
	}
}
