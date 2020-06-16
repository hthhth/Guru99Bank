package com.hth.guru99bank.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class BalancePage {
	WebDriver driver;
	
	@FindBy(how=How.NAME, using="accountno")
	@CacheLookup
	private WebElement accountNo;
	
	@FindBy(how=How.NAME, using="AccSubmit")
	@CacheLookup
	private WebElement buttonSubmit;
	
	public BalancePage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	public void fillAccountNoAndSubmit(String accountNo) {
		this.accountNo.sendKeys(accountNo);
		buttonSubmit.click();
	}
}
