package com.hth.guru99bank;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.hth.guru99bank.pages.*;

public class EditAccount {
	private WebDriver driver;
	private ManagerHomePage managerHomePage;
	private EditAccountForm editAccountForm;
	private EditAccountPage editAccountPage; 
	private AccountDetailsUpdatePage accountDetailsUpdatePage;
	
	@DataProvider(name="edit_account")
	public Object[][] getData() throws IOException {
		return Util.getData(1, "EditAccount");
	}
	@BeforeTest
	public void setUp() {
		driver = Util.logInSuccess(driver);
	}
	@Test(dataProvider="edit_account")
	public void editAccount(String accountNo) throws InterruptedException {
		managerHomePage = new ManagerHomePage(driver);
		managerHomePage.clickLinkEditAccount();
		
		editAccountForm = new EditAccountForm(driver);
		editAccountForm.fillAccountNoAndSubmit(accountNo);
		
		editAccountPage = new EditAccountPage(driver);
		String updateNewAccountType = editAccountPage.editDataAndSubmit();
		
		Assert.assertTrue(driver.getPageSource().contains("Account details updated Successfully!!!"));
		
		List<String> expectedLabels = new ArrayList<>();
		String[] labels = new String[] {"Account ID", "Customer ID", "Customer Name",
				"Email", "Account Type", "Date of Opening", "Current Amount"};
		expectedLabels.addAll(Arrays.asList(labels));
		
		accountDetailsUpdatePage = new AccountDetailsUpdatePage(driver);
		Assert.assertEquals(accountDetailsUpdatePage.getLabels(), expectedLabels);
		
		Assert.assertEquals(accountDetailsUpdatePage.getAccountID(), accountNo);
		Assert.assertEquals(accountDetailsUpdatePage.getAccountType(), updateNewAccountType);
		
		
	}
	@AfterTest
	public void tearDown() {
		driver.quit();
	}
}
