package com.hth.guru99bank.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class MiniStatementForm {
	private WebDriver driver;
	
	@FindBy(how=How.NAME, using="accountno")
	@CacheLookup
	private WebElement accountno;
	
	@FindBy(how=How.NAME, using="AccSubmit")
	@CacheLookup
	private WebElement buttonSubmit;

	public MiniStatementForm(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	public void fillAccountNoAndSubmit(String accountno) {
		this.accountno.sendKeys(accountno);
		buttonSubmit.click();
	}
	
	
	
}
