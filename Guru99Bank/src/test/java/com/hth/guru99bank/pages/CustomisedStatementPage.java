package com.hth.guru99bank.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class CustomisedStatementPage {
	private WebDriver driver;
	
	@FindBy(how=How.CLASS_NAME, using="heading3")
	@CacheLookup
	private WebElement heading; 
	
	public CustomisedStatementPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	public String getHeading() {
		return heading.getText();
	}
}
