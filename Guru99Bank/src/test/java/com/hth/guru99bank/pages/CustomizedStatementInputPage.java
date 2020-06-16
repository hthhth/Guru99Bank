package com.hth.guru99bank.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CustomizedStatementInputPage {
	private WebDriver driver;
	
	@FindBy(name="accountno")
	@CacheLookup
	private WebElement accountNO; 
	
	@FindBy(name="fdate")
	@CacheLookup
	private WebElement fromDate; 
	
	@FindBy(name="tdate")
	@CacheLookup
	private WebElement toDate; 
	
	@FindBy(name="amountlowerlimit")
	@CacheLookup
	private WebElement minimumTransactionValue; 
	
	@FindBy(name="numtransaction")
	@CacheLookup
	private WebElement numberOfTransaction; 
	
	@FindBy(name="AccSubmit")
	@CacheLookup
	private WebElement buttonSubmit;
	
	public CustomizedStatementInputPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	public WebElement getFromDate() {
		return fromDate;
	}

	public WebElement getToDate() {
		return toDate;
	}


	public void fillDataAndSubmitCustomisedStatementForm(String accountNO, String fromDate,
			 String toDate, String minimumTransactionValue, String numberOfTransaction) {
		this.accountNO.sendKeys(accountNO);
		this.fromDate.sendKeys(fromDate);
		this.toDate.sendKeys(toDate);
		this.minimumTransactionValue.sendKeys(minimumTransactionValue);
		this.numberOfTransaction.sendKeys(numberOfTransaction);
		this.buttonSubmit.click();
		
	}
	
}
