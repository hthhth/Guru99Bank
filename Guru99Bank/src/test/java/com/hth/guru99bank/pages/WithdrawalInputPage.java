package com.hth.guru99bank.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class WithdrawalInputPage {
	WebDriver driver;
	
	@FindBy(how=How.NAME, using="accountno")
	@CacheLookup
	private WebElement accountNo;
	
	@FindBy(how=How.NAME, using="ammount")
	@CacheLookup
	private WebElement amount;
	
	@FindBy(how=How.NAME, using="desc")
	@CacheLookup
	private WebElement description;
	
	@FindBy(how=How.NAME, using="AccSubmit")
	@CacheLookup
	private WebElement buttonSubmit;
	
	public WithdrawalInputPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	public void fillDataAndSubmit(String accountNo, String amount, String description) throws InterruptedException {
		this.accountNo.sendKeys(accountNo);
		this.amount.sendKeys(amount);
		this.description.sendKeys(description);
		buttonSubmit.click();
		Thread.sleep(2000);
	}
	
}
