package com.orangehrm.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.orangehrm.actiondriver.ActionDriver;
import com.orangehrm.base.BaseClass;
import com.orangehrm.pages.HomePage;

public class AddAdminPage {
    private ActionDriver actionDriver;

    private By selectUserRole = By.xpath("(//div[text()='-- Select --'])[1]");
    private By selectStatus = By.xpath("(//div[text()='-- Select --'])[2]");
    private By employeeName = By.xpath("//input[contains(@placeholder, 'Type for hints')]");
    private By userName = By.xpath("(//input[@class='oxd-input oxd-input--active'])[1]");
    private By password = By.xpath("(//input[@type='password'])[1]");
    private By confirmPassword = By.xpath("(//input[@type='password'])[2]");
    private By saveButtonClick = By.xpath("//button[@type='submit']");

    public AddAdminPage() {
        this.actionDriver = BaseClass.getActionDriver();
    }

    public void addNewUser(String userRole, String status, String empName, String usrName, String pwd,
            String confirmPwd) {
        actionDriver.selectByVisibleText(selectUserRole, userRole);
        actionDriver.selectByVisibleText(selectStatus, status);
        actionDriver.enterText(employeeName, empName);
        actionDriver.enterText(userName, usrName);
        actionDriver.enterText(password, pwd);
        actionDriver.enterText(confirmPassword, confirmPwd);
        actionDriver.click(saveButtonClick);
    }

}
