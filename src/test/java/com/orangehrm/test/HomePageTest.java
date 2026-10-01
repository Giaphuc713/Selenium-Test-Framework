package com.orangehrm.test;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.orangehrm.base.BaseClass;
import com.orangehrm.pages.HomePage;
import com.orangehrm.pages.LoginPage;
import com.orangehrm.utilities.DataProviders;
import com.orangehrm.utilities.ExtentManager;

public class HomePageTest extends BaseClass {

	private LoginPage loginPage;
	private HomePage homePage;

	@BeforeMethod
	public void setupPages() {
		loginPage = new LoginPage(getDriver());
		homePage = new HomePage(getDriver());
	}

	@Test(dataProvider = "validLoginData", dataProviderClass = DataProviders.class)
	public void verifyOrangeHRMLogo(String username, String password) {
		// ExtentManager.startTest("Home Page Verify Logo Test"); --This has been
		// implemented in TestListener
		ExtentManager.logStep("Navigating to Login Page entering username and password");
		loginPage.login(username, password);
		ExtentManager.logStep("Verify search function at Admin tab");
		Assert.assertTrue(homePage.verifyInvalidSearch(), "Invalid admin name search is not working");
		ExtentManager.logStep("Verify delete admin succesfully");
		Assert.assertTrue(homePage.verifyDeleteAdminSuccessfully(), "Admin is not deleted succesfully");

	}

}
