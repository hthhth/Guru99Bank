package com.hth.guru99bank.pages;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

public class EditAccountPage {
	private WebDriver driver;
	
	@FindBy(how=How.NAME, using="a_type")
	@CacheLookup
	private WebElement dropdownTypeOfAccount;
	
	@FindBy(how=How.NAME, using="AccSubmit")
	@CacheLookup
	private WebElement buttonSubmit;
	
	public EditAccountPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	public String editDataAndSubmit() {
		Select select = new Select(dropdownTypeOfAccount);
		WebElement selectedOption = select.getFirstSelectedOption();
		List<WebElement> optionsList = select.getOptions();
		optionsList.remove(selectedOption);
		String newOption = optionsList.get(0).getText();
		select.selectByValue(newOption);
		
		buttonSubmit.click();
		return newOption;
	}
}
