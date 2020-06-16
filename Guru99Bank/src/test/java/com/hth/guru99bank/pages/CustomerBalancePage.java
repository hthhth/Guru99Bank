package com.hth.guru99bank.pages;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import com.hth.guru99bank.Util;

public class CustomerBalancePage {
	WebDriver driver;
	
	@FindBy(how=How.NAME, using="accountno")
	@CacheLookup
	private WebElement dropdownAccountNo;
	
	@FindBy(how=How.NAME, using="AccSubmit")
	@CacheLookup
	private WebElement buttonSubmit;
	
	public CustomerBalancePage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	public List<String> getDropdownOptions() {
		Select select = new Select(dropdownAccountNo);
		List<WebElement> optionsList = select.getOptions();
		optionsList.remove(0);
		List<String> optionsVisibleText = new ArrayList<>();
		for (WebElement option : optionsList) {
			optionsVisibleText.add(option.getText());
		}
		return optionsVisibleText;
	}
	
	public void fillAccountNoAndSubmit(String accountNo) 
			throws InterruptedException, IOException {
		Select select = new Select(dropdownAccountNo);
		select.selectByVisibleText(accountNo);
		Thread.sleep(1000);
		buttonSubmit.click();
		Thread.sleep(2000);
		
	}
}
