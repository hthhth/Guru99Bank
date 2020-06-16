package com.hth.guru99bank.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

public class AddAccountPage {
	private WebDriver driver;
	
	@FindBy(name="cusid")
	@CacheLookup
	private WebElement customerID;
	
	@FindBy(name="selaccount")
	@CacheLookup
	private WebElement accountType;
	
	@FindBy(name="inideposit")
	@CacheLookup
	private WebElement initialDeposit;
	
	@FindBy(name="button2")
	@CacheLookup
	private WebElement buttonSubmit;
	
	public AddAccountPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	public void fillDataAndSubmitNewAccount(String customerID, String accountType, String initialDeposit) {
		this.customerID.sendKeys(customerID);
		
		Select dropdownAccountType = new Select(this.accountType);
		if (accountType.equalsIgnoreCase("savings")) {
			dropdownAccountType.selectByVisibleText("Savings");
		} else if (accountType.equalsIgnoreCase("current")) {
			dropdownAccountType.selectByVisibleText("Current");
		}
		
		this.initialDeposit.sendKeys(initialDeposit);
		this.buttonSubmit.click();
	}
}
