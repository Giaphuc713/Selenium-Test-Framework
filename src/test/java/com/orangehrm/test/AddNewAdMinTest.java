package com.orangehrm.test;

import java.util.UUID;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.orangehrm.base.BaseClass;
import com.orangehrm.pages.AddAdminPage;
import com.orangehrm.pages.HomePage;
import com.orangehrm.pages.LoginPage;
import com.orangehrm.utilities.ExtentManager;

public class AddNewAdMinTest extends BaseClass {
    private LoginPage loginPage;
    private HomePage homePage;
    private AddAdminPage addAdminPage;

    @BeforeMethod
    public void setupAddAdminTest() {
        loginPage = new LoginPage(getDriver());
        homePage = new HomePage(getDriver());
        addAdminPage = new AddAdminPage(getDriver());

        loginPage.login(getProp().getProperty("username"), getProp().getProperty("password"));
        Assert.assertTrue(homePage.isAdminTabVisible(), "Admin navigation should be available after login");
    }

    @Test
    public void testAddOneEnabledAdmin() {
        String employeeName = "John Michael Smith";
        String username = createUniqueUsername("john.smith");
        String password = "Qa1!" + UUID.randomUUID().toString().replace("-", "");

        ExtentManager.logStep("Open Admin and Add User form");
        homePage.clickAdminTab();
        homePage.navigateToAddAdmin();

        ExtentManager.logStep("Create one enabled user with the Admin role");
        addAdminPage.addNewAdmin(employeeName, username, password, password);

        String successMessage = addAdminPage.waitForSuccessfulSave();
        Assert.assertTrue(successMessage.contains("Successfully Saved"),
                "A successful-save confirmation should be displayed");
        Assert.assertTrue(addAdminPage.verifyAdminInUserList(username),
                "Exactly one matching user should appear with Admin role and Enabled status");
    }

    private String createUniqueUsername(String seed) {
        String normalizedSeed = seed.replaceAll("[^A-Za-z0-9_]", "_");
        if (normalizedSeed.length() > 16) {
            normalizedSeed = normalizedSeed.substring(0, 16);
        }
        return "qa_" + normalizedSeed + "_" + UUID.randomUUID().toString().substring(0, 8);
    }
}
