package com.hth.guru99bank;

import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.hth.guru99bank.pages.*;

public class CustomerChangePasswordAndLogin {
	private WebDriver driver;
	private CustomerHomePage customerHomePage;
	private ChangePasswordPage changePasswordPage;
	private HomePage homePage;
	private String newPassword="123@7";
	
	
	@BeforeTest
	public void setUp() {
		driver = Util.customerLogInSuccess(driver);
	}
	
	@Test(priority=1)
	public void enterIncorrectOldPassword() throws InterruptedException {
		customerHomePage = new CustomerHomePage(driver);
		customerHomePage.clickLinkChangePassword();
		
		changePasswordPage = new ChangePasswordPage(driver);
		changePasswordPage.fillAndSubmitNewPassword("1", newPassword, newPassword);
		
		Alert alert = driver.switchTo().alert();
		String actualMessaage = alert.getText();
		String expectedMessage = "Old Password is incorrect";
		Assert.assertEquals(actualMessaage, expectedMessage);
		
		alert.accept();
		Thread.sleep(2000);
		String actualRedirectURL = driver.getCurrentUrl();
		String expectedURL = "http://www.demo.guru99.com/V4/customer/PasswordInput.php";
		Assert.assertEquals(actualRedirectURL, expectedURL);
	}
	@Test(priority=2)
	public void verifyAfterChangePasswordPageIsRedirectedToLoginScreenAndCustomerCanLoginWithNewPassword() 
			throws InterruptedException {
		customerHomePage = new CustomerHomePage(driver);
		customerHomePage.clickLinkChangePassword();
		
		changePasswordPage = new ChangePasswordPage(driver);
		changePasswordPage.fillAndSubmitNewPassword(Util.CUSTOMER_PASSWORD, newPassword, newPassword);

		Alert alert = driver.switchTo().alert();
		String actualMessaage = alert.getText();
		String expectedMessage = "Password is Changed";
		Assert.assertEquals(actualMessaage, expectedMessage);
		
		alert.accept();
		Thread.sleep(2000);
		String actualRedirectURL = driver.getCurrentUrl();
		String expectedURL = "http://www.demo.guru99.com/V4/index.php";
		Assert.assertEquals(actualRedirectURL, expectedURL);
		
		homePage = new HomePage(driver);
		homePage.logInSuccess(Util.CUSTOMER_ID, newPassword);
		
		Assert.assertTrue(driver.getPageSource().contains("Log out"));
		String redirectURL = driver.getCurrentUrl();
		String expectedRedirectURL = "http://www.demo.guru99.com/V4/customer/Customerhomepage.php";
		Assert.assertEquals(redirectURL, expectedRedirectURL);
	}
	@AfterTest
	public void tearDown() {
		driver.quit();
	}
}
