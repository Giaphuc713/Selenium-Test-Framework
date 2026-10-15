package com.orangehrm.listeners;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import org.testng.IAnnotationTransformer;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.annotations.ITestAnnotation;
import org.apache.commons.lang3.exception.ExceptionUtils;
import com.orangehrm.base.BaseClass;
import com.orangehrm.utilities.ExtentManager;
import com.orangehrm.utilities.JiraCreateIssue;
import com.orangehrm.utilities.JiraServiceProvider;
import com.orangehrm.utilities.RetryAnalyzer;

public class TestListener implements ITestListener, IAnnotationTransformer {

	@Override
	public void transform(ITestAnnotation annotation, Class testClass, Constructor testConstructor, Method testMethod) {
		annotation.setRetryAnalyzer(RetryAnalyzer.class);
	}

	// Triggered when a test starts
	@Override
	public void onTestStart(ITestResult result) {
		String testName = result.getMethod().getMethodName();
		// Start logging in Extent Reports
		ExtentManager.startTest(testName);
		ExtentManager.logStep("Test Started: " + testName);
	}

	// Triggered when a Test succeeds
	@Override
	public void onTestSuccess(ITestResult result) {
		String testName = result.getMethod().getMethodName();

		if (!result.getTestClass().getName().toLowerCase().contains("api")) {
			ExtentManager.logStepWithScreenshot(BaseClass.getDriver(), "Test Passed Successfully!",
					"Test End: " + testName + " - ✔ Test Passed");
		} else {
			ExtentManager.logStepValidationForAPI("Test End: " + testName + " - ✔ Test Passed");
		}

	}

	// Triggered when a Test Fails
	@Override
	public void onTestFailure(ITestResult result) {
		String testName = result.getMethod().getMethodName();
		String failureMessage = result.getThrowable().getMessage();
		ExtentManager.logStep(failureMessage);
		if (!result.getTestClass().getName().toLowerCase().contains("api")) {
			ExtentManager.logFailure(BaseClass.getDriver(), "Test Failed!",
					"Test End: " + testName + " - ❌ Test Failed");
		} else {
			ExtentManager.logFailureAPI("Test End: " + testName + " - ❌ Test Failed");
		}
		JiraCreateIssue jiraAnnotation = result.getMethod().getConstructorOrMethod().getMethod()
				.getAnnotation(JiraCreateIssue.class);
		if (jiraAnnotation != null && jiraAnnotation.isCreateIssue()) {
			String jiraUrl = BaseClass.getProp().getProperty("url_project_jira").trim();
			String jiraUser = BaseClass.getProp().getProperty("jira_username").trim();
			String jiraPassword = BaseClass.getProp().getProperty("jira_password").trim();
			String jiraProject = BaseClass.getProp().getProperty("jira_projectkey").trim();
			JiraServiceProvider jiraServiceProvider = new JiraServiceProvider(jiraUrl, jiraUser, jiraPassword,
					jiraProject);
			String issueDescription = "Failure Reason from Automation Testing\n\n" + result.getThrowable().getMessage()
					+ "\n";

			issueDescription = issueDescription + ExceptionUtils.getStackTrace(result.getThrowable());

			String issueSummary = result.getMethod().getConstructorOrMethod().getMethod().getName()

					+ " Failed in Automation Testing";
			jiraServiceProvider.createJiraIssue("Bug", issueSummary, issueDescription, "Automated Testing");
		}
	}

	// Triggered when a Test skips
	@Override
	public void onTestSkipped(ITestResult result) {
		String testName = result.getMethod().getMethodName();
		ExtentManager.logSkip("Test Skipped " + testName);
	}

	// Triggered when a suite Starts
	@Override
	public void onStart(ITestContext context) {
		// Initialize the Extent Reports
		ExtentManager.getReporter();
	}

	// Triggered when the suite ends
	@Override
	public void onFinish(ITestContext context) {
		// Flush the Extent Reports
		ExtentManager.endTest();

	}

}
