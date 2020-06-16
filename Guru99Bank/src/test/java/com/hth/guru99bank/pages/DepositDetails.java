package com.hth.guru99bank.pages;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterTest;

public class DepositDetails {
	WebDriver driver;
	
	@FindBy(how=How.XPATH, using="//*[@id=\"deposit\"]/tbody/tr/td[1]")
	@CacheLookup
	private List<WebElement> labels;
	
	public DepositDetails(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	// get labels (1st column on page)
	public String[] getActualLabels() {
		labels.remove(0);
		labels.remove(labels.size()-1);
		String[] actualLabels=new String[labels.size()];
		for (int i = 0; i < labels.size(); i++) {
			actualLabels[i] = labels.get(i).getText();
		}
		return actualLabels;
	}
}
