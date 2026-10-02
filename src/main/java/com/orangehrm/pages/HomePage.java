package com.orangehrm.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.orangehrm.actiondriver.ActionDriver;
import com.orangehrm.base.BaseClass;

public class HomePage {

	private ActionDriver actionDriver;
	private AddAdminPage addAdminPage;

	// Define locators using By class
	private By adminTab = By.xpath("//span[text()='Admin']");
	private By userIDButton = By.className("oxd-userdropdown-name");
	private By logoutButton = By.xpath("//a[text()='Logout']");
	private By oranageHRMlogo = By.xpath("//div[@class='oxd-brand-banner']//img");
	private By pimTab = By.xpath("//span[text()='PIM']");
	private By employeeSearch = By
			.xpath("//label[text()='Employee Name']/parent::div/following-sibling::div/div/div/input");
	private By searchButton = By.xpath("//button[@type='submit']");
	private By emplFirstAndMiddleName = By.xpath("//div[@class='oxd-table-card']/div/div[3]");
	private By emplLastName = By.xpath("//div[@class='oxd-table-card']/div/div[4]");
	private By closeSideBar = By.xpath("//i[@class='oxd-icon bi-chevron-left']");
	private By openSideBar = By.xpath("//i[@classs ='oxd-icon bi-chevron-right']");
	private By usernameInput = By.xpath("(//input[contains(@class,'oxd-input')])[2]");
	private By roleSelect = By.xpath("(//div[@class = 'oxd-select-wrapper'])[1]");
	private By statusSelect = By.xpath("(//div[@class = 'oxd-select-wrapper'])[2]");
	private By role_Admin = By.xpath("(//div[@class = 'oxd-select-text--after'])[1]");
	private By status_Enabled = By.xpath("(//div[@class = 'oxd-select-text--after'])[2]");
	private By searchButton2 = By.xpath("//button[@type='submit']");
	private By noRecordFouns = By.xpath("//span[text()='No Records Found']");
	private By deleteAdminButton = By.xpath("//i[@class='oxd-icon bi-trash']");
	private By confirmDelete = By.xpath("//button[text() = ' Yes, Delete ']");
	private By addButton = By.xpath("//button[text() = ' Add ']");
	private By pimTabButton = By.xpath("//span[text() = 'PIM']");

	// Initialize the ActionDriver object by passing WebDriver instance
	/*
	 * public HomePage(WebDriver driver) { this.actionDriver= new
	 * ActionDriver(driver); }
	 */

	public HomePage(WebDriver driver) {
		this.actionDriver = BaseClass.getActionDriver();
	}

	// Method to verify if Admin tab is visible
	public boolean isAdminTabVisible() {
		return actionDriver.isDisplayed(adminTab);
	}

	public boolean verifyOrangeHRMlogo() {
		return actionDriver.isDisplayed(oranageHRMlogo);
	}

	// Method to Navigate to PIM tab
	public void clickOnPIMTab() {
		actionDriver.click(pimTab);
	}

	// Employee Search
	public void employeeSearch(String value) {
		actionDriver.enterText(employeeSearch, value);
		actionDriver.click(searchButton);
		actionDriver.scrollToElement(emplFirstAndMiddleName);
	}

	// Verify employee first and middle name
	public boolean verifyEmployeeFirstAndMiddleName(String emplFirstAndMiddleNameFromDB) {
		return actionDriver.compareText(emplFirstAndMiddleName, emplFirstAndMiddleNameFromDB);
	}

	// Verify employee first and middle name
	public boolean verifyEmployeeLastName(String emplLastFromDB) {
		return actionDriver.compareText(emplLastName, emplLastFromDB);
	}

	// Method to perform logout operation
	public void logout() {
		actionDriver.click(userIDButton);
		actionDriver.click(logoutButton);
	}

	public void openSidebar() {
		actionDriver.click(openSideBar);
	}

	public void closeSidebar() {
		actionDriver.click(closeSideBar);
	}

	public void openAdminTab() {
		actionDriver.click(adminTab);
	}

	public void searchInvalidAdmin(String name) {
		actionDriver.enterText(usernameInput, name);
		actionDriver.click(roleSelect);
		actionDriver.click(role_Admin);
		actionDriver.click(statusSelect);
		actionDriver.click(status_Enabled);
		actionDriver.click(searchButton2);
	}

	public boolean verifyInvalidSearch(String name) {
		openAdminTab();
		searchInvalidAdmin(name);
		return actionDriver.isDisplayed(noRecordFouns);
	}

	public boolean verifyDeleteAdminSuccessfully(String name) {
		actionDriver.click(deleteAdminButton);
		actionDriver.click(confirmDelete);
		return verifyInvalidSearch(name);

	}

	public void clickAdminTab() {
		actionDriver.click(adminTab);
	}

	public void navigateToAddAdmin() {
		actionDriver.click(addButton);
	}

	public void navigateToPimTab() {
		actionDriver.click(pimTab);
	}

}
