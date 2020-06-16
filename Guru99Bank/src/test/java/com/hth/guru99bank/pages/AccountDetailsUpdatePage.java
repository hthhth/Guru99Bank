package com.hth.guru99bank.pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class AccountDetailsUpdatePage {
	private WebDriver driver;
	
	@FindBy(how=How.XPATH, using="//*[@id=\"account\"]/tbody/tr/td[1]")
	@CacheLookup
	private List<WebElement> labels;

	@FindBy(how=How.XPATH, using="//*[@id=\"account\"]/tbody/tr/td[2]")
	@CacheLookup
	private List<WebElement> values;
	
	public AccountDetailsUpdatePage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	public List<String> getLabels() {
		for (int i=0; i<3; i++) {
			labels.remove(0);
		}
		labels.remove(labels.size()-1);
		List<String> labelsString = new ArrayList<>();
		for (WebElement label : labels) {
			labelsString.add(label.getText());
		}
		return labelsString;
	}
	
	public String getAccountID() {
		return values.get(0).getText();
	}
	public String getAccountType() {
		return values.get(4).getText();
	}

}
