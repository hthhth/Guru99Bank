package com.hth.guru99bank.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class MiniStatementDetailsPage {
	private WebDriver driver;
	
	@FindBy(how=How.XPATH, using="/html/body/table/tbody/tr[1]/td/p")
	@CacheLookup
	private WebElement displayedText;
	
	public MiniStatementDetailsPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
}
