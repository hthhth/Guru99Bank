package com.hth.guru99bank;

import static com.hth.guru99bank.Util.BASE_URL;
import static com.hth.guru99bank.Util.PASSWORD;
import static com.hth.guru99bank.Util.USER_NAME;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.hth.guru99bank.pages.HomePage;

public class Util {
	public static final String BASE_URL = "http://www.demo.guru99.com/V4/";
	public static final String MANAGER_HOMEPAGE_URL = "http://www.demo.guru99.com/V4/manager/Managerhomepage.php";
	public static final String USER_NAME = "mngr262659"; //old: mngr259223
	public static final String PASSWORD = "Avazanu"; //"mavehaz"; old abc@12345
	public static final String CUSTOMER_ID = "40099";
	public static final String CUSTOMER_PASSWORD = "123@6";
	
	/*
	 * Below method is for methods now(WebDriver driver) and yesterday(WebDriver driver)
	 */
	private static String date(String str, WebDriver driver) {
		LocalDate date=null;
		switch (str) {
			case "now":
				date = LocalDate.now();
				break;
			case "yesterday":
				date = LocalDate.now().minusDays(1);
				break;
		}
		
		String dateFormatted="";
		if (driver instanceof FirefoxDriver) {
			dateFormatted = date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		} else if (driver instanceof ChromeDriver) {
			dateFormatted = date.format(DateTimeFormatter.ofPattern("MMddyyyy"));
		}
		return dateFormatted;
	}

	/*
	 * Below method return current date formatted as mmddyyyy for Chrome, yyyy-mm-dd for Firefox
	 * Example: 24122010 for Chrome, 2010-12-24 for Firefox
	 */
	public static String now(WebDriver driver) {
		return date("now", driver);
	}
	/*
	 * Below method return yesterday formatted as mmddyyyy for Chrome, yyyy-mm-dd for Firefox
	 * Example: 24122010 for Chrome, 2010-12-24 for Firefox
	 */
	public static String yesterday(WebDriver driver) {
		return date("yesterday", driver);
	}
	
	/********************************************************************************/
	
	// enter str with format yyyy-mm-dd (for example: 2019-12-20)
	// If driver is Firefox, return yyyy-mm-dd. If driver is Chrome, return mmddyyyy
	public static String formatDate(String str, WebDriver driver) {
		String date = str;
		if (driver instanceof ChromeDriver) {
			date = str.substring(5, 7) + str.substring(8, 10) + str.substring(0, 4);
		}
		return date;
	}
	/********************************************************************************/
	
	/*
	 * Used for @BeforeTest. Login successfully.
	 */
	public static WebDriver logInSuccess(WebDriver driver) {
		driver = SetUp.setUp();
		driver.get(BASE_URL);
		driver.manage().window().maximize();
		HomePage homePage = new HomePage(driver);
		homePage.logInSuccess(USER_NAME, PASSWORD);
		return driver;
	}
	public static WebDriver customerLogInSuccess(WebDriver driver) {
		driver = SetUp.setUp();
		driver.get(BASE_URL);
		driver.manage().window().maximize();
		HomePage homePage = new HomePage(driver);
		homePage.logInSuccess(CUSTOMER_ID, CUSTOMER_PASSWORD);
		return driver;
	}
	/*
	 * Use for @DataProvider. Get data from file excel.
	 */
	public static Object[][] getData(int numberOfHeaderRows, String sheetName) throws IOException {
		String projectPath = System.getProperty("user.dir");
		ExcelUtils excel = new ExcelUtils(projectPath + "/Data/data.xlsx", sheetName);
		Object[][] data = new Object[excel.getRowCount()-numberOfHeaderRows][excel.getColCount()];
		for (int i=0; i<excel.getRowCount()-numberOfHeaderRows; i++) {
			for (int j=0; j<excel.getColCount(); j++) {
				data[i][j]=excel.getCellDataString(i+numberOfHeaderRows, j);
			}
		}
		return data;
	}
	
	// Take screenshot, the screenshot is saved in path: projectPath/data/fileName
	public static void takeScreenShot(WebDriver driver, String fileName) throws IOException {
		String projectPath = System.getProperty("user.dir");
		File screenShotFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(screenShotFile, new File(projectPath + "/data/" + fileName));
	}
}
