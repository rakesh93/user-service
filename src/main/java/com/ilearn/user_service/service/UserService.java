package com.ilearn.user_service.service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ilearn.user_service.model.UserModel;
import com.ilearn.user_service.repository.UserRepository;
import com.ilearn.user_service.util.ApiResponse;
import com.ilearn.user_service.util.AppConstants;

@Service
public class UserService {

	private static final Logger logger = LoggerFactory.getLogger(UserService.class);

	@Autowired
	UserRepository userRepository;

	@Autowired
	PasswordEncoder passwordEncoder;

	public ApiResponse createUser(UserModel userModel) {
		if (userRepository.existsByUserName(userModel.getUserName())) {
			return new ApiResponse(AppConstants.FAILURE_CODE, AppConstants.USER_NAME_DUPLICATE,
					Collections.emptyList());
		}

		if (userRepository.existsByEmailId(userModel.getEmailId())) {
			return new ApiResponse(AppConstants.FAILURE_CODE, AppConstants.EMAIL_ID_DUPLICATE, Collections.emptyList());
		}

		if (userRepository.existsByMobileNo(userModel.getMobileNo())) {
			return new ApiResponse(AppConstants.FAILURE_CODE, AppConstants.MOBILE_DUPLICATE, Collections.emptyList());
		}
		String encryptedPassword = passwordEncoder.encode(userModel.getPassword());
		userModel.setPassword(encryptedPassword);
		userModel.setCreatedDateTime(LocalDateTime.now());
		userModel.setModifiedDateTime(LocalDateTime.now());
		UserModel response = userRepository.save(userModel);
		if (response != null) {
			logger.info("User saved successfully with id {}", userModel.getId());
			return new ApiResponse(AppConstants.SUCCESS_CODE, AppConstants.CREATED, userModel);
		} else {
			return new ApiResponse(AppConstants.FAILURE_CODE, AppConstants.NOT_CREATED, Collections.emptyList());
		}
	}

	public List<UserModel> getUsersByRole(String role) {
		return userRepository.findByRole(role);
	}

	public ApiResponse updateUser(UserModel userModel) {
		boolean existingUser = userRepository.existsByUserName(userModel.getUserName());
		if (!existingUser) {
			return new ApiResponse(AppConstants.FAILURE_CODE, AppConstants.USER_NOT_FOUND, Collections.emptyList());
		}
		userRepository.updatePassword(passwordEncoder.encode(userModel.getPassword()), LocalDateTime.now(),
				userModel.getUserName());
		logger.info("User Password successfully userModel with userName {}", userModel.getUserName());
		return new ApiResponse(AppConstants.SUCCESS_CODE, AppConstants.UPDATED, Collections.emptyList());
	}

	public ApiResponse getUserNameByMobileNumber(String mobileNo) {
		UserModel userName = userRepository.findByMobileNo(mobileNo);
		if (userName != null) {
			return new ApiResponse(AppConstants.SUCCESS_CODE, userName.getUserName(), Collections.emptyList());
		} else {
			return new ApiResponse(AppConstants.FAILURE_CODE, AppConstants.MOBILE_NOT_REGISTER,
					Collections.emptyList());
		}
	}

	public ApiResponse getProfileDetail(String userName) {
		UserModel userModel = userRepository.findByUserName(userName);
		if (userModel != null) {
			logger.info("User Successfully got records for this userName {}", userName);
			return new ApiResponse(AppConstants.SUCCESS_CODE, AppConstants.RESULT_GOT_SUCCESS, userModel);
		} else {
			return new ApiResponse(AppConstants.FAILURE_CODE, AppConstants.USER_NOT_FOUND, Collections.emptyList());
		}
	}

	public ApiResponse updateProfile(String userName, UserModel userModel) {
		UserModel userToUpdate = userRepository.findByUserName(userName);

		if (userToUpdate == null) {
			return new ApiResponse(AppConstants.FAILURE_CODE, AppConstants.USER_NOT_FOUND, Collections.emptyList());
		}
		userToUpdate.setFirstName(userModel.getFirstName());
		userToUpdate.setLastName(userModel.getLastName());
		userToUpdate.setEmailId(userModel.getEmailId());
		userToUpdate.setMobileNo(userModel.getMobileNo());
		userToUpdate.setModifiedDateTime(LocalDateTime.now());
		userRepository.save(userToUpdate);
		logger.info("User Profile successfully Updated with userName {}", userName);
		return new ApiResponse(AppConstants.SUCCESS_CODE, AppConstants.UPDATED, userToUpdate);
	}

	public ApiResponse updateNewUser(UserModel userModel) {
	return null;
	}

}
