package com.hth.guru99bank.pages;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ManagerHomePage {
	private WebDriver driver;
	
	@FindBy(linkText="New Customer")
	@CacheLookup
	private WebElement linkNewCustomer;
	
	@FindBy(linkText="Edit Customer")
	@CacheLookup
	private WebElement linkEditCustomer;
	
	@FindBy(linkText="New Account")
	@CacheLookup
	private WebElement linkNewAccount;
	
	@FindBy(linkText="Edit Account")
	@CacheLookup
	private WebElement linkEditAccount;
	
	@FindBy(linkText="Deposit")
	@CacheLookup
	private WebElement linkDeposit;
	
	@FindBy(linkText="Withdrawal")
	@CacheLookup
	private WebElement linkWithdrawal;
	
	@FindBy(linkText="Fund Transfer")
	@CacheLookup
	private WebElement linkFundTransfer;
	
	@FindBy(linkText="Change Password")
	@CacheLookup
	private WebElement linkChangePassword;
	
	@FindBy(linkText="Balance Enquiry")
	@CacheLookup
	private WebElement linkBalanceEnquiry;
	
	@FindBy(linkText="Mini Statement")
	@CacheLookup
	private WebElement linkMiniStatement;
	
	@FindBy(linkText="Customised Statement")
	@CacheLookup
	private WebElement linkCustomisedStatement;
	
	@FindBy(linkText="Log out")
	@CacheLookup
	private WebElement linkLogOut;
	
	public ManagerHomePage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	public void clickLinkNewCustomer() {
		linkNewCustomer.click();
	}
	
	public void clickLinkEditCustomer() {
		linkEditCustomer.click();
	}
	
	public void clickLinkNewAccount() throws InterruptedException {
		JavascriptExecutor javascriptExecutor = (JavascriptExecutor)driver;
		javascriptExecutor.executeScript("arguments[0].click()", linkNewAccount);
		Thread.sleep(2000);
	}
	
	public void clickLinkEditAccount() throws InterruptedException {
		JavascriptExecutor javascriptExecutor = (JavascriptExecutor)driver;
		javascriptExecutor.executeScript("arguments[0].click()", linkEditAccount);
		Thread.sleep(2000);
	}
	
	public void clickLinkDeposit() throws InterruptedException {
		JavascriptExecutor javascriptExecutor = (JavascriptExecutor)driver;
		javascriptExecutor.executeScript("arguments[0].click()", linkDeposit);
		Thread.sleep(2000);
	}
	
	public void clickLinkWithdrawal() throws InterruptedException {
		JavascriptExecutor javascriptExecutor = (JavascriptExecutor)driver;
		javascriptExecutor.executeScript("arguments[0].click()", linkWithdrawal);
		Thread.sleep(2000);
	}
	
	public void clickLinkFundTransfer() throws InterruptedException {
		JavascriptExecutor javascriptExecutor = (JavascriptExecutor)driver;
		javascriptExecutor.executeScript("arguments[0].click()", linkFundTransfer);
		Thread.sleep(2000);
	}	
	
	public void clickLinkChangePassword() throws InterruptedException {
		JavascriptExecutor javascriptExecutor = (JavascriptExecutor)driver;
		javascriptExecutor.executeScript("arguments[0].click();", linkChangePassword);
		Thread.sleep(2000);
	}
	
	public void clickLinkBalanceEnquiry() throws InterruptedException {
		JavascriptExecutor javascriptExecutor = (JavascriptExecutor)driver;
		javascriptExecutor.executeScript("arguments[0].click();", linkBalanceEnquiry);
		Thread.sleep(2000);
	}
	
	public void clickLinkMiniStatement() throws InterruptedException {
		JavascriptExecutor javascriptExecutor = (JavascriptExecutor)driver;
		javascriptExecutor.executeScript("arguments[0].click();", linkMiniStatement);
		Thread.sleep(2000);
	}
	
	public void clickLinkCustomisedStatement() throws InterruptedException {
		JavascriptExecutor javascriptExecutor = (JavascriptExecutor)driver;
		javascriptExecutor.executeScript("arguments[0].click();", linkCustomisedStatement);
		Thread.sleep(2000);
	}
	
	public void clickLinkLogOut() throws InterruptedException {
		JavascriptExecutor javascriptExecutor = (JavascriptExecutor)driver;
		javascriptExecutor.executeScript("arguments[0].click();", linkLogOut);
		Thread.sleep(2000);
	}
	
}
