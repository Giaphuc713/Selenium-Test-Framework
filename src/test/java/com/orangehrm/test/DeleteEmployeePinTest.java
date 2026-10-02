package com.orangehrm.test;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.orangehrm.base.BaseClass;
import com.orangehrm.pages.HomePage;
import com.orangehrm.pages.LoginPage;
import com.orangehrm.pages.PimPage;

public class DeleteEmployeePinTest extends BaseClass {
    private LoginPage loginPage;
    private HomePage homePage;
    private PimPage pimPage;

    @BeforeMethod
    public void setup() {
        loginPage = new LoginPage(getDriver());
        homePage = new HomePage(getDriver());
        pimPage = new PimPage(getDriver());
        loginPage.login("Admin", "admin123");
    }

    @Test
    void deleteEmployee() {
        homePage.navigateToPimTab();
        pimPage.deleteEmployee();
    }

}
