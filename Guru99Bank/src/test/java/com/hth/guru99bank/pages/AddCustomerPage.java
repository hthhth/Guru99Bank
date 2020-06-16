package com.hth.guru99bank.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class AddCustomerPage {
	private WebDriver driver;
	
	@FindBy(name="name")
	@CacheLookup
	private WebElement customerName;
	
	@FindBy(xpath="//input[@value='m']")
	@CacheLookup
	private WebElement genderMale;
	
	@FindBy(xpath="//input[@value='f']")
	@CacheLookup
	private WebElement genderFemale;
	
	@FindBy(id="dob")
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
	
	@FindBy(name="password")
	@CacheLookup
	private WebElement password;
	
	@FindBy(name="sub")
	@CacheLookup
	private WebElement buttonSubmit;
	
	public AddCustomerPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	public void fillDataAndSubmitNewCustomer(String customerName, String gender, 
			String dateOfBirth, String address, String city, String state,
			String PIN, String mobileNumber, String email, String password) throws InterruptedException {
		
		this.customerName.sendKeys(customerName);
		Thread.sleep(2000);
		if (gender.equalsIgnoreCase("male")) {
			this.genderMale.click();
		} else if (gender.equalsIgnoreCase("female")) {
			this.genderFemale.click();
		}
		Thread.sleep(2000);
		this.dateOfBirth.sendKeys(dateOfBirth);
//		this.dateOfBirth.click();
//		this.dateOfBirth.sendKeys(dateOfBirth.split("/")[0]); //11/04/2013 for mm/dd/yyyy
//		Thread.sleep(1000);
//		this.dateOfBirth.sendKeys(dateOfBirth.split("/")[1]);
//		Thread.sleep(1000);
//		this.dateOfBirth.sendKeys(dateOfBirth.split("/")[2]);
		Thread.sleep(2000);
		this.address.sendKeys(address);
		this.city.sendKeys(city);
		this.state.sendKeys(state);
		this.PIN.sendKeys(PIN);
		this.mobileNumber.sendKeys(mobileNumber);
		this.email.sendKeys(email);
		Thread.sleep(2000);
		this.password.sendKeys(password);
		Thread.sleep(2000);
		this.buttonSubmit.click();
		Thread.sleep(2000);
	}

	
	
	
}
