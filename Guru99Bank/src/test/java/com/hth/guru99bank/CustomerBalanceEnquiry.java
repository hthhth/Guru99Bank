package com.hth.guru99bank;

import java.io.IOException;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.hth.guru99bank.pages.CustomerBalancePage;
import com.hth.guru99bank.pages.CustomerHomePage;

public class CustomerBalanceEnquiry {
	private WebDriver driver;
	private CustomerHomePage customerHomePage;
	private CustomerBalancePage customerBalancePage;
	
	@BeforeTest
	public void setUp() {
		driver = Util.customerLogInSuccess(driver);
	}
	
	@Test
	public void verifyBalance() throws InterruptedException, IOException {
		customerHomePage = new CustomerHomePage(driver);
		customerHomePage.clickLinkBalanceEnquiry();
		
		customerBalancePage = new CustomerBalancePage(driver);
		List<String> dropdownOptions = customerBalancePage.getDropdownOptions();
		
		for (String option : dropdownOptions) {
			customerBalancePage.fillAccountNoAndSubmit(option);
			
			String fileName = "Balance_Cust_" + Util.CUSTOMER_ID + "_Acc_" + option + ".png";
			Util.takeScreenShot(driver, fileName);
			
			System.out.println("Check screenshot in /data/" + fileName);
			
			if (!option.equals(dropdownOptions.get(dropdownOptions.size()-1))) {
				customerHomePage = new CustomerHomePage(driver);
				customerHomePage.clickLinkBalanceEnquiry();
				customerBalancePage = new CustomerBalancePage(driver);
			}	
		}
	}
	
	@AfterTest
	public void tearDown() {
		driver.quit();
	}
}
