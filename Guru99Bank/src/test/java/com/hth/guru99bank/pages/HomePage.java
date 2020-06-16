package com.hth.guru99bank.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage {
	private WebDriver driver;
	
	@FindBy(name="uid")
	@CacheLookup
	private WebElement userID;
	
	@FindBy(name="password")
	@CacheLookup
	private WebElement password;
	
	@FindBy(name="btnLogin")
	@CacheLookup
	private WebElement buttonLogin;
	
	public HomePage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	public void setUserID(String userID) {
		this.userID.sendKeys(userID);
	}
	public void setPassword(String password) {
		this.password.sendKeys(password);
	}
	public void clickLogIn() {
		buttonLogin.click();
	}
	public void logInSuccess(String userID, String password) {
		setUserID(userID);
		setPassword(password);
		clickLogIn();
	}
	
	
}
