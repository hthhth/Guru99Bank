package com.hth.guru99bank.pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class EditCustomerDetailsResultPage {
	private WebDriver driver;
	
	//column labels
	@FindBy(how=How.XPATH, using="//*[@id=\"customer\"]/tbody/tr/td[1]")
	@CacheLookup
	private List<WebElement> labels;
	
	//column values
	@FindBy(how=How.XPATH, using="//*[@id=\"customer\"]/tbody/tr/td[2]")
	@CacheLookup
	private List<WebElement> values;
	
	public EditCustomerDetailsResultPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	public List<String> getLabels() {
		// remove the "Customer details updated Successfully!!!"...(1st, 2nd, 3rd element) from list
		for (int i=0; i<3; i++) {
			labels.remove(0);
		}	
		// remove the "Continue" at the bottom of table
		labels.remove(labels.size()-1);
		
		List<String> labelsString = new ArrayList<String>();
		for (WebElement label : labels) {
			labelsString.add(label.getText());
		}
		return labelsString;
	}
	public List<String> getValues() {
		// remove values of Customer Name, Gender, Birthdate
		for (int i=0; i<3; i++) {
			values.remove(1);
		}
		List<String> valuesString = new ArrayList<String>();
		for (WebElement value : values) {
			valuesString.add(value.getText());
		}
		return valuesString;
	}
	
	
}
