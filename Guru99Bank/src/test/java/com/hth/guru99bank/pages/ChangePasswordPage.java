package com.hth.guru99bank.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ChangePasswordPage {
	private WebDriver driver;
	
	@FindBy(name="oldpassword")
	@CacheLookup
	private WebElement oldPassword;
	
	@FindBy(name="newpassword")
	@CacheLookup
	private WebElement newPassword;
	
	@FindBy(name="confirmpassword")
	@CacheLookup
	private WebElement confirmPassword;
	
	@FindBy(name="sub")
	@CacheLookup
	private WebElement buttonSubmit;
	
	public ChangePasswordPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	public void fillAndSubmitNewPassword(String oldPassword, String newPassword, String confirmPassword) {
		this.oldPassword.sendKeys(oldPassword);
		this.newPassword.sendKeys(newPassword);
		this.confirmPassword.sendKeys(confirmPassword);
		this.buttonSubmit.click();
	}
}
