package com.hth.guru99bank;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

public class LogInDay1 {
	public static void verifyLogIn() {
		WebDriver driver = SetUp.setUp();
		driver.get("http://www.demo.guru99.com/V4/");
		
		WebElement userID = driver.findElement(By.name("uid"));
		WebElement password = driver.findElement(By.name("password"));
		WebElement buttonLogin = driver.findElement(By.name("btnLogin"));
	
		userID.sendKeys("mngr259223");
		password.sendKeys("mavehaz");
		buttonLogin.click();
		
		String actualWelcomeText = driver.findElement(By.tagName("marquee")).getText();
		String expectedWelcomeText = "Welcome To Manager's Page of Guru99 Bank";
		Assert.assertEquals(actualWelcomeText, expectedWelcomeText);
		
		driver.close();
		System.out.println("1. Test case Login Day 1: PASSED");
		
	}
}
