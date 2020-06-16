package com.hth.guru99bank.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class CustomerFundTransferPage {
	private WebDriver driver;
	
	@FindBy(how=How.NAME, using="payersaccount")
	@CacheLookup
	private WebElement payersAccountNo;
	
	@FindBy(how = How.NAME, using = "payeeaccount")
	@CacheLookup
	private WebElement payeesAccountNo;
	
	@FindBy(how = How.NAME, using = "ammount")
	@CacheLookup
	private WebElement amount;
	
	@FindBy(how = How.NAME, using = "desc")
	@CacheLookup
	private WebElement description;
	
	@FindBy(how = How.NAME, using = "AccSubmit")
	@CacheLookup
	private WebElement buttonSubmit;

	public CustomerFundTransferPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	public void fillInFundTransferInforAndSubmit(String payersAccountNo, String payeesAccountNo, 
			String amount, String description) {
		this.payersAccountNo.sendKeys(payersAccountNo);
		this.payeesAccountNo.sendKeys(payeesAccountNo);
		this.amount.sendKeys(amount);
		this.description.sendKeys(description);
		buttonSubmit.click();
	}
}
