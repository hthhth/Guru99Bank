package com.hth.guru99bank.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class EditCustomerPage {
	private WebDriver driver;
	
	@FindBy(name="name")
	@CacheLookup
	private WebElement customerName; 
	
	@FindBy(name="gender")
	@CacheLookup
	private WebElement gender; 
	
	@FindBy(name="dob")
	@CacheLookup
	private WebElement dateOfBirth; 
	
	@FindBy(name="addr")
	@CacheLookup
	private WebElement address; 
	
	@FindBy(name="city")
	@CacheLookup
	private WebElement city; 
	
	@FindBy(name="state")
	@CacheLookup
	private WebElement state; 
	
	@FindBy(name="pinno")
	@CacheLookup
	private WebElement PIN; 
	
	@FindBy(name="telephoneno")
	@CacheLookup
	private WebElement mobileNumber; 
	
	@FindBy(name="emailid")
	@CacheLookup
	private WebElement email; 
	
	@FindBy(name="sub")
	@CacheLookup
	private WebElement buttonSubmit; 
	
	public EditCustomerPage(WebDriver driver) {
		this.driver=driver;
		PageFactory.initElements(driver, this);
	}
	
	public String getCustomerName() {
		return customerName.getText();
	}

	public void fillDataEditCustomerAndSubmit(String address, String city, String state,
			String PIN, String mobileNumber, String email) throws InterruptedException {
		this.address.clear();
		Thread.sleep(2000);
		this.address.sendKeys(address);
		Thread.sleep(2000);
		this.city.clear();
		Thread.sleep(2000);
		this.city.sendKeys(city);
		Thread.sleep(2000);
		this.state.clear();
		Thread.sleep(2000);
		this.state.sendKeys(state);
		Thread.sleep(2000);
		this.PIN.clear();
		Thread.sleep(2000);
		this.PIN.sendKeys(PIN);
		Thread.sleep(2000);
		this.mobileNumber.clear();
		Thread.sleep(2000);
		this.mobileNumber.sendKeys(mobileNumber);
		Thread.sleep(2000);
		this.email.clear();
		Thread.sleep(2000);
		this.email.sendKeys(email);
		Thread.sleep(2000);
		buttonSubmit.click();
		Thread.sleep(2000);
	}
}
