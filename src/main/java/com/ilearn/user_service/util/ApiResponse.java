package com.ilearn.user_service.util;

public class ApiResponse {

	private int statusCode;
	private String message;

	public ApiResponse(int statusCode, String message) {
		super();
		this.statusCode = statusCode;
		this.message = message;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "ApiResponse [statusCode=" + statusCode + ", message=" + message + "]";
	}

}
