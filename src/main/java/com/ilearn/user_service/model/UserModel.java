package com.ilearn.user_service.model;

import java.time.LocalDateTime;

import com.ilearn.user_service.util.AppConstants;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

@Entity
@Table(name = "userdetail",
uniqueConstraints = {
    @UniqueConstraint(columnNames = "user_name"),
    @UniqueConstraint(columnNames = "email_id"),
    @UniqueConstraint(columnNames = "mobile_no")
})
public class UserModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "user_name")
	@NotBlank(message = AppConstants.USER_NAME)
	private String userName;

	@NotBlank(message = AppConstants.FIRST_NAME)
	@Column(name = "first_name")
	private String firstName;

	@NotBlank(message = AppConstants.LAST_NAME)
	@Column(name = "last_name")
	private String lastName;

	@NotBlank(message = AppConstants.PASSWORD)
	@Column(name = "password")
	private String password;

	@NotBlank(message = AppConstants.ROLE)
	@Column(name = "role")
	private String role;

	@NotBlank(message = AppConstants.EMAIL_ID)
	@Column(name = "email_id")
	@Email(message = AppConstants.INVALID_EMAIL)
	private String emailId;

	@NotBlank(message = AppConstants.MOBILE_NO)
	@Column(name = "mobile_no")
	@Pattern(regexp = "^[6-9]\\d{9}$",message = AppConstants.INVALID_MOBILE)
	private String mobileNo;

	@Column(name = "created_date_time")
	private LocalDateTime createdDateTime;
	
	@Column(name = "modified_date_time")
	private LocalDateTime modifiedDateTime;
	
	@Transient
	private String oldPassword;

	@Transient
	private String newPassword;
	
	public UserModel() {
	}	

	public UserModel(Long id,String userName, String firstName, String lastName, String password, String role, String emailId,
			String mobileNo,LocalDateTime createdDateTime, LocalDateTime modifiedDateTime) {
		super();
		this.id = id;
		this.userName = userName;
		this.firstName = firstName;
		this.lastName = lastName;
		this.password = password;
		this.role = role;
		this.emailId = emailId;
		this.mobileNo = mobileNo;
		this.createdDateTime = createdDateTime;
		this.modifiedDateTime = modifiedDateTime;
	}

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public LocalDateTime getCreatedDateTime() {
		return createdDateTime;
	}

	public void setCreatedDateTime(LocalDateTime createdDateTime) {
		this.createdDateTime = createdDateTime;
	}

	public LocalDateTime getModifiedDateTime() {
		return modifiedDateTime;
	}

	public void setModifiedDateTime(LocalDateTime modifiedDateTime) {
		this.modifiedDateTime = modifiedDateTime;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	@Override
	public String toString() {
		return "UserModel [id=" + id + ", userName=" + userName + ", firstName=" + firstName + ", lastName=" + lastName
				+ ", password=" + password + ", role=" + role + ", emailId=" + emailId + ", mobileNo=" + mobileNo
				+ ", createdDateTime=" + createdDateTime + ", modifiedDateTime=" + modifiedDateTime + "]";
	}

}
