package com.hth.guru99bank.pages;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class CustomerFundTransferDetailsPage {
	private WebDriver driver;
	
	@FindBy(how=How.CLASS_NAME, using="heading3")
	@CacheLookup
	private WebElement heading;
	
	@FindBy(how=How.XPATH, using="/html/body/table/tbody/tr[2]/td/table/tbody/tr/td[1]")
	@CacheLookup
	private List<WebElement> labels;
	
	@FindBy(how=How.XPATH, using="/html/body/table/tbody/tr[2]/td/table/tbody/tr/td[2]")
	@CacheLookup
	private List<WebElement> values;
	
	public CustomerFundTransferDetailsPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	public WebElement getHeading() {
		return heading;
	}
	
	public String[] getLabels() {
		labels.remove(labels.size()-1);
		String[] labelsString = new String[labels.size()];
		for (int i=0; i<labels.size(); i++) {
			labelsString[i] = labels.get(i).getText();
		}
		return labelsString;
	}
	
	public String[] getValues() {
		String[] valuesString = new String[values.size()];
		for (int i=0; i<values.size(); i++) {
			valuesString[i] = values.get(i).getText();
		}
		return valuesString;
	}
	
}
