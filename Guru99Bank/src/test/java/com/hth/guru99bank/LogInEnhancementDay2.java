package com.hth.guru99bank;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import static com.hth.guru99bank.Util.*;

public class LogInEnhancementDay2 {
	public static void verifyLogIn() {
		WebDriver driver = SetUp.setUp();
		driver.get(BASE_URL);
		
		WebElement userID = driver.findElement(By.name("uid"));
		WebElement password = driver.findElement(By.name("password"));
		WebElement buttonLogin = driver.findElement(By.name("btnLogin"));
	
		userID.sendKeys(USER_NAME);
		password.sendKeys(PASSWORD);
		buttonLogin.click();
		
		String title = driver.getTitle();
		String expectedTitle = "Guru99 Bank Manager HomePage";
		Assert.assertEquals(title, expectedTitle);
		
		driver.close();
		System.out.println("2. Test case Login Enhancement Day 2: PASSED");
	}
}
