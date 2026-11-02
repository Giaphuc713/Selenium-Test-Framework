package com.orangehrm.pages;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.orangehrm.actiondriver.ActionDriver;
import com.orangehrm.base.BaseClass;

public class AddAdminPage {
    private final ActionDriver actionDriver;
    private final WebDriverWait wait;

    private final By userRoleDropdown = dropdown("User Role");
    private final By statusDropdown = dropdown("Status");
    private final By employeeNameField = field("Employee Name");
    private final By usernameField = field("Username");
    private final By passwordField = field("Password");
    private final By confirmPasswordField = field("Confirm Password");
    private final By saveButton = By.xpath("//button[normalize-space()='Save']");
    private final By searchButton = By.xpath("//button[normalize-space()='Search']");
    private final By successToast = By.cssSelector(".oxd-toast--success");

    public AddAdminPage(WebDriver driver) {
        this.actionDriver = BaseClass.getActionDriver();
        this.wait = new WebDriverWait(driver,
                Duration.ofSeconds(Long.parseLong(BaseClass.getProp().getProperty("explicitWait", "30"))));
    }

    public void addNewAdmin(String employeeName, String username, String password, String confirmPassword) {
        selectOption(userRoleDropdown, "Admin");
        enterEmployeeName(employeeName);
        selectOption(statusDropdown, "Enabled");
        actionDriver.enterText(usernameField, username);
        actionDriver.enterText(passwordField, password);
        actionDriver.enterText(confirmPasswordField, confirmPassword);
        actionDriver.click(saveButton);
    }

    public String waitForSuccessfulSave() {
        WebElement toast = wait.until(ExpectedConditions.visibilityOfElementLocated(successToast));
        return toast.getText();
    }

    public boolean verifyAdminInUserList(String username) {
        actionDriver.enterText(usernameField, username);
        actionDriver.click(searchButton);

        By matchingRows = By.xpath("//div[contains(@class,'oxd-table-card')][.//div[contains(@class,'oxd-table-cell') and normalize-space()="
                + xpathLiteral(username) + "]]");
        List<WebElement> rows = wait.until(driver -> {
            List<WebElement> results = driver.findElements(matchingRows);
            return results.size() == 1 ? results : null;
        });
        String rowText = rows.get(0).getText();
        return rowText.contains("Admin") && rowText.contains("Enabled");
    }

    private void selectOption(By dropdown, String option) {
        actionDriver.click(dropdown);

        By optionLocator = By.xpath("//div[contains(@class,'oxd-select-option')]/span[normalize-space()="
                + xpathLiteral(option) + "]");
        actionDriver.click(optionLocator);
    }

    private void enterEmployeeName(String employeeName) {
        actionDriver.enterText(employeeNameField, employeeName);

        By suggestion = By.xpath("//*[contains(@class,'oxd-autocomplete-option') and normalize-space()="
                + xpathLiteral(employeeName) + "]");
        actionDriver.click(suggestion);
    }

    private static By dropdown(String label) {
        return By.xpath("//label[normalize-space()='" + label
                + "']/../..//div[contains(concat(' ', normalize-space(@class), ' '), ' oxd-select-text ')]");
    }

    private static By field(String label) {
        return By.xpath("//label[normalize-space()='" + label + "']/../..//input");
    }

    private String xpathLiteral(String value) {
        if (!value.contains("'")) {
            return "'" + value + "'";
        }
        if (!value.contains("\"")) {
            return "\"" + value + "\"";
        }
        String[] parts = value.split("'", -1);
        return "concat('" + String.join("',\"'\",'", parts) + "')";
    }
}
