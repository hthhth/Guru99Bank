package com.hth.guru99bank;

import static com.hth.guru99bank.Util.*;

import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.hth.guru99bank.pages.ChangePasswordPage;
import com.hth.guru99bank.pages.HomePage;
import com.hth.guru99bank.pages.ManagerHomePage;

public class ChangePassword {
	WebDriver driver;
	String currentPassword = "abc@12345";
	String newPasswordString = "abc@123456";
	ChangePasswordPage changePasswordPage;
	HomePage homePage;
	@BeforeTest
	public void setUp() throws InterruptedException {
		driver = SetUp.setUp();
		driver.get(BASE_URL);
		
		//Login
		homePage = new HomePage(driver);
		homePage.logInSuccess(USER_NAME, currentPassword);

		//Navigate to screen Change Password
		ManagerHomePage managerHomePage = new ManagerHomePage(driver);
		managerHomePage.clickLinkChangePassword();
	}
	
	@Test(priority=0)
	public void changePasswordWithIncorrectOldPassword() throws InterruptedException {
		changePasswordPage = new ChangePasswordPage(driver);
		changePasswordPage.fillAndSubmitNewPassword("1", newPasswordString, newPasswordString);
		
		Alert alert = driver.switchTo().alert();
		String expectedAlert = "Old Password is incorrect";
		Assert.assertEquals(alert.getText(), expectedAlert);
		alert.accept();
		Thread.sleep(2000);
		String expectedURL = "http://www.demo.guru99.com/V4/manager/PasswordInput.php";
		Assert.assertEquals(driver.getCurrentUrl(), expectedURL);
	}
	@Test(priority=1)
	public void changePasswordSuccess() throws InterruptedException {
		changePasswordPage = new ChangePasswordPage(driver);
		changePasswordPage.fillAndSubmitNewPassword(currentPassword, newPasswordString, newPasswordString);
		
		Alert alert = driver.switchTo().alert();
		String expectedAlert = "Password is Changed";
		Assert.assertEquals(alert.getText(), expectedAlert);
		alert.accept();
		Thread.sleep(2000);
		String expectedURL = "http://www.demo.guru99.com/V4/index.php";
		Assert.assertEquals(driver.getCurrentUrl(), expectedURL);
	}
	@Test(priority=2)
	public void verifyUserCanLogInWithNewPassword() {
		homePage = new HomePage(driver);
		homePage.setUserID(USER_NAME);
		homePage.setPassword(newPasswordString);
		homePage.clickLogIn();
		
		String expectedURL = "http://www.demo.guru99.com/V4/manager/Managerhomepage.php";
		Assert.assertEquals(driver.getCurrentUrl(), expectedURL);
	}
	@AfterTest
	public void tearDown() {
		driver.quit();
	}
}
