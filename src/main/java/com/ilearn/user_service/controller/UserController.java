package com.ilearn.user_service.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ilearn.user_service.model.UserModel;
import com.ilearn.user_service.service.UserService;
import com.ilearn.user_service.util.ApiResponse;
import jakarta.validation.Valid;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/userservice")
public class UserController {

	@Autowired
	UserService userService;

	@PostMapping("/create")
	public ApiResponse createUser(@Valid @RequestBody UserModel userModel) {
		return userService.createUser(userModel);
	}

	@GetMapping("/list/{role}")
	public List<UserModel> getUsersByRole(@PathVariable String role) {
		return userService.getUsersByRole(role);
	}
	
	@PostMapping("/updatePassword")
	public ApiResponse updateUser(@RequestBody UserModel userModel) {
		return userService.updateUser(userModel);
	}
	
}
