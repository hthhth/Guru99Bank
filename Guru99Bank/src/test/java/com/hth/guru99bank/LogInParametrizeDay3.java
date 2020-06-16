package com.hth.guru99bank;

import java.io.IOException;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class LogInParametrizeDay3 {
	private static WebDriver driver;
	private static WebElement userID, password, buttonLogin;

	public static void verifyLogIn() throws IOException {
		String projectPath = System.getProperty("user.dir");
		ExcelUtils excel = new ExcelUtils(projectPath + "/Data/LoginData.xlsx", "Sheet1");
		
		for (int i=1; i<excel.getRowCount(); i++) {
			String userName = excel.getCellDataString(i, 0);
			String passwordString = excel.getCellDataString(i, 1);

			driver = SetUp.setUp();
			driver.get(Util.BASE_URL);
			userID = driver.findElement(By.name("uid"));
			password = driver.findElement(By.name("password"));
			buttonLogin = driver.findElement(By.name("btnLogin"));
			
			userID.sendKeys(userName);
			password.sendKeys(passwordString);
			buttonLogin.click();
			try {
				Alert alert = driver.switchTo().alert();
				String message = alert.getText();
				alert.accept();
				if (message.contains("User or Password is not valid")) {
					System.out.println("3. Test case Login Parametrize Day 3 - Negative case " + i + " : PASSED");
				} else {
					System.out.println("3. Test case Login Parametrize Day 3 - Negative case " + i + " : FAILED");
				}
			} catch (NoAlertPresentException e) {
				String title = driver.getTitle();
				String expectedTitle = "Guru99 Bank Manager HomePage";
				if (title.contains(expectedTitle)) {
					System.out.println("3. Test case Login Parametrize Day 3 - HappyPath: PASSED");
				} else {
					System.out.println("3. Test case Login Parametrize Day 3 - HappyPath: FAILED");
				}
			}
			driver.close();
		}
	}
}
