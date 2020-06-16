package com.hth.guru99bank;

import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.hth.guru99bank.pages.ManagerHomePage;

public class LogOut {
	private WebDriver driver;
	private ManagerHomePage managerHomePage;
	
	@BeforeTest
	public void setUp() {
		driver = Util.logInSuccess(driver);
	}
	@Test
	public void logOut() throws InterruptedException {
		managerHomePage = new ManagerHomePage(driver);
		managerHomePage.clickLinkLogOut();
		
		Alert alert = driver.switchTo().alert();
		String expectedMessage = "You Have Succesfully Logged Out!!";
		String actualMessage = alert.getText();
		Assert.assertEquals(actualMessage, expectedMessage);
		
		alert.accept();
		Thread.sleep(2000);
		String expectedRedirectURL = "http://www.demo.guru99.com/V4/index.php";
		Assert.assertEquals(driver.getCurrentUrl(), expectedRedirectURL);
	}
	
	@AfterTest
	public void tearDown() {
		driver.quit();
	}
}
