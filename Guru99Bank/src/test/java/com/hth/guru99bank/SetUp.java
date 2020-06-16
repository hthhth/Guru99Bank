package com.hth.guru99bank;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.hth.guru99bank.pages.HomePage;

import io.github.bonigarcia.wdm.WebDriverManager;

public class SetUp {
	public static WebDriver setUp() {
		WebDriverManager.firefoxdriver().setup();
		WebDriver driver = new FirefoxDriver();
		return driver;
//		WebDriverManager.chromedriver().setup();
//		WebDriver driver = new ChromeDriver();
//		return driver;
	}
}
