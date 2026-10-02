package com.orangehrm.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.orangehrm.actiondriver.ActionDriver;

import com.orangehrm.base.BaseClass;

public class PimPage {

    private ActionDriver actionDriver;
    private By trash = By.xpath("(//i[@class='oxd-icon bi-trash'])[1]");
    private By confirmDelete = By.xpath("//button[text() = ' Yes, Delete ']");

    public PimPage(WebDriver driver) {
        this.actionDriver = BaseClass.getActionDriver();
    }

    public void deleteEmployee() {
        actionDriver.click(trash);
        actionDriver.click(confirmDelete);
    }

}
