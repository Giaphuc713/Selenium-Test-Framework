package com.orangehrm.utilities;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class ApiUtility {

	// ─────────────────────────────────────────────────────────────────
	// Token Storage (Thread-safe, per test run)
	// ─────────────────────────────────────────────────────────────────

	private static final ThreadLocal<String> accessTokenStore = new ThreadLocal<>();
	private static final ThreadLocal<String> refreshTokenStore = new ThreadLocal<>();

	/** Save access token for the current thread */
	public static void saveAccessToken(String token) {
		accessTokenStore.set(token);
	}

	/** Save refresh token for the current thread */
	public static void saveRefreshToken(String token) {
		refreshTokenStore.set(token);
	}

	/** Retrieve the saved access token for the current thread */
	public static String getAccessToken() {
		return accessTokenStore.get();
	}

	/** Retrieve the saved refresh token for the current thread */
	public static String getRefreshToken() {
		return refreshTokenStore.get();
	}

	// ─────────────────────────────────────────────────────────────────
	// Unauthenticated Requests
	// ─────────────────────────────────────────────────────────────────

	// Method to send the GET Request
	public static Response sendGetRequest(String endPoint) {
		return RestAssured.given()
				.header("Content-Type", "application/json")
				.get(endPoint);
	}

	// Method to send the POST Request
	public static Response sendPostRequest(String endPoint, String payLoad) {
		return RestAssured.given()
				.header("Content-Type", "application/json")
				.body(payLoad)
				.post(endPoint);
	}

	/**
	 * Send a GET request with a provided Bearer token.
	 */
	public static Response sendGetRequestWithAuth(String endPoint, String token) {
		return RestAssured.given()
				.header("Content-Type", "application/json")
				.header("Authorization", "Bearer " + token)
				.get(endPoint);
	}

	/**
	 * Send a POST request with a provided Bearer token.
	 */
	public static Response sendPostRequestWithAuth(String endPoint, String payLoad, String token) {
		return RestAssured.given()
				.header("Content-Type", "application/json")
				.header("Authorization", "Bearer " + token)
				.body(payLoad)
				.post(endPoint);
	}

	// ─────────────────────────────────────────────────────────────────
	// Response Helpers
	// ─────────────────────────────────────────────────────────────────

	// Method to validate the response status
	public static boolean validateStatusCode(Response response, int statusCode) {
		return response.getStatusCode() == statusCode;
	}

	// Method to extract value from JSON response
	public static String getJsonValue(Response response, String value) {
		return response.jsonPath().getString(value);
	}

}
