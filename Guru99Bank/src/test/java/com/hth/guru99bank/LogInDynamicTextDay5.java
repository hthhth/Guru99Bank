package com.hth.guru99bank;

import java.io.IOException;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class LogInDynamicTextDay5 {
	
	private static WebDriver driver;
	private static WebElement userID, password, buttonLogin;

	@DataProvider
	public Object[][] getData() throws IOException {
		String projectPath = System.getProperty("user.dir");
		ExcelUtils excel = new ExcelUtils(projectPath + "/Data/LoginData.xlsx", "Sheet1");
		Object[][] data = new Object[excel.getRowCount()-1][2];
		for (int i=1; i<excel.getRowCount(); i++) {
			data[i-1][0] = excel.getCellDataString(i, 0);
			data[i-1][1] = excel.getCellDataString(i, 1);
		}
		return data;
	}
	
	@BeforeMethod
	public void setUp() {
		driver = SetUp.setUp();
		driver.get(Util.BASE_URL);
	}
	
	@Test(dataProvider="getData")
	public static void verifyLogIn(String userName, String userPassword) {
		userID = driver.findElement(By.name("uid"));
		password = driver.findElement(By.name("password"));
		buttonLogin = driver.findElement(By.name("btnLogin"));

		userID.sendKeys(userName);
		password.sendKeys(userPassword);
		buttonLogin.click();
		try {
			Alert alert = driver.switchTo().alert();
			String message = alert.getText();
			String expectedMessage = "User or Password is not valid";
			alert.accept();
			Assert.assertEquals(message, expectedMessage);
		} catch (NoAlertPresentException e) {
			WebElement textManagerID = driver.findElement(By.xpath("//td[contains(text(),'Manger Id : ')]"));
			Assert.assertTrue(textManagerID.getText().contains(userName));
		}
	}
	
	@AfterMethod
	public void tearDown() {
		driver.quit();
	}
}
