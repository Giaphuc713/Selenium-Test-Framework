package com.orangehrm.test;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.orangehrm.base.BaseClass;
import com.orangehrm.pages.HomePage;
import com.orangehrm.pages.LoginPage;
import com.orangehrm.utilities.DataProviders;
import com.orangehrm.utilities.ExtentManager;

public class SearchInvalidAdminTest extends BaseClass {

	private LoginPage loginPage;
	private HomePage homePage;

	@BeforeMethod
	public void setupPages() {
		loginPage = new LoginPage(getDriver());
		homePage = new HomePage(getDriver());
		loginPage.login("Admin", "admin123");
	}

	@Test
	public void verifySearchInvalidUser() {
		ExtentManager.logStep("Verify search function at Admin tab");
		Assert.assertTrue(homePage.verifyInvalidSearch("aaaaaa"), "Invalid admin name search is not working");
		// ExtentManager.logStep("Verify delete admin succesfully");
		// Assert.assertTrue(homePage.verifyDeleteAdminSuccessfully("FMLName1"), "Admin
		// is not deleted succesfully");
	}

}
