package com.hth.guru99bank.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class EditCustomerForm {
	private WebDriver driver;
	
	@FindBy(name="cusid")
	@CacheLookup
	private WebElement customerID;
	
	@FindBy(name="AccSubmit")
	@CacheLookup
	private WebElement buttonSubmit;
	
	public EditCustomerForm(WebDriver driver) {
		this.driver=driver;
		PageFactory.initElements(driver, this);
	}
	
	public void fillCustomerIDAndSubmit(String customerID) {
		this.customerID.sendKeys(customerID);
		buttonSubmit.click();
	}
	
}
