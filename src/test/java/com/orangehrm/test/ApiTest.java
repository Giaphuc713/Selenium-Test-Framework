package com.orangehrm.test;

import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.aventstack.extentreports.util.Assert;
import com.orangehrm.utilities.ApiUtility;
import com.orangehrm.utilities.ExtentManager;
import org.testng.annotations.BeforeClass;

import io.restassured.response.Response;

public class ApiTest {

	// ─────────────────────────────────────────────────────────────────
	// @BeforeClass: chỉ login và lưu token — KHÔNG dùng ExtentManager
	// (ExtentTest chưa được khởi tạo ở thời điểm @BeforeClass chạy)
	// ─────────────────────────────────────────────────────────────────
	@BeforeClass
	public void loginOnce() {
		Response response = ApiUtility.sendPostRequest(
				"https://dummyjson.com/auth/login",
				"{\"username\":\"emilys\",\"password\":\"emilyspass\"}");

		// Lưu token vào ApiUtility.ThreadLocal để các @Test sau tái sử dụng
		ApiUtility.saveAccessToken(ApiUtility.getJsonValue(response, "accessToken"));
		ApiUtility.saveRefreshToken(ApiUtility.getJsonValue(response, "refreshToken"));
	}

	@Test
	public void getCurrentAuthUser() {
		SoftAssert softAssert = new SoftAssert();
		Response response = ApiUtility.sendGetRequestWithAuth("https://dummyjson.com/auth/me",
				ApiUtility.getAccessToken());
		ExtentManager.logStep("getCurrentUser");
		// System.out.println(response.getBody().asPrettyString());
		// Validate status code valid
		boolean isStatusCodeValid = ApiUtility.validateStatusCode(response, 200);
		if (isStatusCodeValid) {
			ExtentManager.logStepValidationForAPI("Status Code 200 - OK ✔");
		} else {
			ExtentManager.logFailureAPI("Status Code Validation Failed! Actual: " + response.getStatusCode());
		}
		softAssert.assertTrue(isStatusCodeValid, "Status code is invalid");
		String username = ApiUtility.getJsonValue(response, "username");
		boolean isUsernameValid = "emilyssss".equals(username);
		if (isUsernameValid) {
			ExtentManager.logStepValidationForAPI("Username Validation Passed! [" + username + "] ✔");
		} else {
			ExtentManager.logFailureAPI("Username Validation Failed! Expected: emilys | Actual: " + username);
		}
		softAssert.assertTrue(isUsernameValid, "Username is invalid");
		softAssert.assertAll();

	}

}
