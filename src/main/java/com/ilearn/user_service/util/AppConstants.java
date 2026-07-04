package com.ilearn.user_service.util;

public class AppConstants {

	// API Response Code
	public static final int SUCCESS = 200;
	public static final int FAILURE = 400;
	public static final int NOT_FOUND = 404;
	public static final int SERVER_ERROR = 500;

	// Message Value Constant
	public static final String CREATED = "Successfully Created Account";
	public static final String NOT_CREATED = "Failure To Create Account";
	public static final String UPDATED = "Account Updated Successfully";
	public static final String NOT_UPDATED = "Failure To Update Account";
	public static final String DELETED = "Account Deleted Successfully";
	public static final String USER_NAME = "UserName is Required";
	public static final String PASSWORD = "Password is Required";
	public static final String FIRST_NAME = "FirstName is Required";
	public static final String LAST_NAME = "LastName is Required";
	public static final String EMAIL_ID = "EmailId is Required";
	public static final String MOBILE_NO = "Mobile Number is Required";
	public static final String ROLE = "Role is Required";
	public static final String USER_NAME_DUPLICATE = "UserName is Duplicate.Please entry new one";
	public static final String EMAIL_ID_DUPLICATE = "EmailId is Duplicate.Please entry new one";
	public static final String MOBILE_DUPLICATE = "Mobile is Duplicate.Please entry new one";
	public static final String INVALID_MOBILE = "Mobile number must be 10 digits and start with 6, 7, 8, or 9";
	public static final String INVALID_EMAIL = "Please enter a valid email address";
}
