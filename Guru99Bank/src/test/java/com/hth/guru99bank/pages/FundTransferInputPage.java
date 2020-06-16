package com.hth.guru99bank.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class FundTransferInputPage {
	WebDriver driver;
	
	@FindBy(name="payersaccount")
	@CacheLookup
	private WebElement payerAccount;
	
	@FindBy(name="payeeaccount")
	@CacheLookup
	private WebElement payeeAccount;
	
	@FindBy(name="ammount")
	@CacheLookup
	private WebElement amount;
	
	@FindBy(name="desc")
	@CacheLookup
	private WebElement descscription;
	
	@FindBy(name="AccSubmit")
	@CacheLookup
	private WebElement buttonSubmit;
	
	public FundTransferInputPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	public void fillDataAndSubmitFundTransfer(String payerAccount, String payeeAccount, 
			String amount, String descscription) {
		this.payerAccount.sendKeys(payerAccount);
		this.payeeAccount.sendKeys(payeeAccount);
		this.amount.sendKeys(amount);
		this.descscription.sendKeys(descscription);
		this.buttonSubmit.click();
	}
}
