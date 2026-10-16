package com.orangehrm.test;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.orangehrm.base.BaseClass;
import com.orangehrm.pages.HomePage;
import com.orangehrm.pages.LoginPage;
import com.orangehrm.pages.AddAdminPage;
import com.orangehrm.utilities.DataProviders;

public class AddNewAdMinTest extends BaseClass {
    private LoginPage loginPage;
    private HomePage homePage;
    private AddAdminPage addAdminPage;

    @BeforeMethod
    public void setup() {
        loginPage = new LoginPage(getDriver());
        homePage = new HomePage(getDriver());
        addAdminPage = new AddAdminPage(getDriver());
        loginPage.login("Admin", "admin123");
    }

    @Test(dataProvider = "addNewUser", dataProviderClass = DataProviders.class)
    public void testAddNewAdmin(String empName, String usrName, String pwd, String confirmPwd) {
        homePage.clickAdminTab();
        homePage.navigateToAddAdmin();
        addAdminPage.addNewAdmin(empName, usrName, pwd, confirmPwd);
        // Assert.assertTrue()
    }

}