package com.hth.guru99bank.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CustomerHomePage {
	private WebDriver driver;
	
	@FindBy(linkText="Balance Enquiry")
	@CacheLookup
	private WebElement linkBalanceEnquiry;
	
	@FindBy(linkText="Fund Transfer")
	@CacheLookup
	private WebElement linkFundTransfer;
	
	@FindBy(linkText="Changepassword")
	@CacheLookup
	private WebElement linkChangePassword;
	
	@FindBy(linkText="Mini Statement")
	@CacheLookup
	private WebElement linkMiniStatement;
	
	@FindBy(linkText="Customised Statement")
	@CacheLookup
	private WebElement linkCustomisedStatement;
	
	@FindBy(linkText="Log out")
	@CacheLookup
	private WebElement linkLogOut;
	
	public CustomerHomePage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	public void clickLinkBalanceEnquiry() {
		linkBalanceEnquiry.click();
	}
	
	public void clickLinkFundTransfer() {
		linkFundTransfer.click();
	}
	
	public void clickLinkChangePassword() {
		linkChangePassword.click();
	}
	
	public void clickMiniStatement() {
		linkMiniStatement.click();
	}
	
	public void clickLinkCustomisedStatement() {
		linkCustomisedStatement.click();
	}
	
	public void clickLinkLogOut() {
		linkLogOut.click();
	}
	
}
