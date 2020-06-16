package com.hth.guru99bank.pages;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class BalanceDetailsPage {
	WebDriver driver;

	@FindBy(how=How.XPATH, using="//*[@id=\"balenquiry\"]/tbody/tr/td[1]")
	@CacheLookup
	private List<WebElement> labels;
	
	@FindBy(how=How.XPATH, using="//*[@id=\"balenquiry\"]/tbody/tr/td[2]")
	@CacheLookup
	private List<WebElement> values;
	
	public BalanceDetailsPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	// get labels (1st column)
	public String[] getLabels() {
		labels.remove(0);
		labels.remove(labels.size()-1);
		String[] labelsString = new String[labels.size()];
		for (int i=0; i<labels.size(); i++) {
			labelsString[i]=labels.get(i).getText();
		}
		return labelsString;
	}
	
	// get values (2nd column)
	public String[] getValues() {
		String[] valuesString = new String[values.size()];
		for (int i=0; i<values.size(); i++) {
			valuesString[i]=values.get(i).getText();
		}
		return valuesString;
	}
	
	public String getBalance() {
		return getValues()[2];
	}

}
