package com.ilearn.user_service.service;

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
			return new ApiResponse(AppConstants.FAILURE, AppConstants.USER_NAME_DUPLICATE);
		}

		if (userRepository.existsByEmailId(userModel.getEmailId())) {
			return new ApiResponse(AppConstants.FAILURE, AppConstants.EMAIL_ID_DUPLICATE);
		}

		if (userRepository.existsByMobileNo(userModel.getMobileNo())) {
			return new ApiResponse(AppConstants.FAILURE, AppConstants.MOBILE_DUPLICATE);
		}
		String encryptedPassword = passwordEncoder.encode(userModel.getPassword());
		userModel.setPassword(encryptedPassword);
		UserModel response = userRepository.save(userModel);
		if (response != null) {
			logger.info("User saved successfully with id {}", userModel.getId());
			return new ApiResponse(AppConstants.SUCCESS, AppConstants.CREATED);
		} else {
			return new ApiResponse(AppConstants.FAILURE, AppConstants.NOT_CREATED);
		}
	}

}
