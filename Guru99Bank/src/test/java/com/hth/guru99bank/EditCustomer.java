package com.hth.guru99bank;

import static com.hth.guru99bank.Util.BASE_URL;
import static com.hth.guru99bank.Util.PASSWORD;
import static com.hth.guru99bank.Util.USER_NAME;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.hth.guru99bank.pages.EditCustomerDetailsResultPage;
import com.hth.guru99bank.pages.EditCustomerForm;
import com.hth.guru99bank.pages.EditCustomerPage;
import com.hth.guru99bank.pages.HomePage;
import com.hth.guru99bank.pages.ManagerHomePage;

public class EditCustomer {
	WebDriver driver;
	@BeforeTest
	public void setUp() {
		driver = SetUp.setUp();
		driver.get(BASE_URL);
		driver.manage().window().maximize();
		HomePage homePage = new HomePage(driver);
		homePage.logInSuccess(USER_NAME, PASSWORD);
	}
	
	@DataProvider(name="edit_customer")
	public Object[][] getData() throws IOException{
		String projectPath = System.getProperty("user.dir");
		ExcelUtils excel = new ExcelUtils(projectPath + "/Data/data.xlsx", "EditCustomer");
		Object[][] data = new Object[excel.getRowCount()-1][excel.getColCount()];
		for (int i=0; i<excel.getRowCount()-1; i++) {
			for (int j=0; j<excel.getColCount(); j++) {
				data[i][j]=excel.getCellDataString(i+1, j);
			}
		}
		return data;
	}
	
	@Test(dataProvider="edit_customer")
	public void editCustomer(String customerID, String address, String city, String state,
			String PIN, String mobileNumber, String email) throws InterruptedException {
		ManagerHomePage managerHomePage = new ManagerHomePage(driver);
		managerHomePage.clickLinkEditCustomer();
		EditCustomerForm editCustomerForm = new EditCustomerForm(driver);
		editCustomerForm.fillCustomerIDAndSubmit(customerID);
		
		EditCustomerPage editCustomerPage = new EditCustomerPage(driver);
		LocalDateTime date = LocalDateTime.now();
		String newAddress = address + date;
		editCustomerPage.fillDataEditCustomerAndSubmit(newAddress, city, state, PIN,
				 mobileNumber, email);
		
		EditCustomerDetailsResultPage editCustomerDetailsResultPage = new EditCustomerDetailsResultPage(driver);
		List<String> actualLabels = editCustomerDetailsResultPage.getLabels();
		List<String> expectedLabels = Arrays.asList("Customer ID", "Customer Name",
				"Gender", "Birthdate", "Address", "City", "State", "Pin",
				"Mobile No.", "Email");
		Assert.assertEquals(actualLabels, expectedLabels);
		
		List<String> actualValues = editCustomerDetailsResultPage.getValues();
		List<String> expectedValues = Arrays.asList(customerID, newAddress, city, state, PIN, mobileNumber, email);
		Assert.assertEquals(actualValues, expectedValues);
	}
	
	@AfterTest
	public void tearDown() {
		driver.quit();
	}
}
