package com.ilearn.user_service.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.ilearn.user_service.model.UserModel;
import com.ilearn.user_service.repository.UserRepository;
import com.ilearn.user_service.util.ApiResponse;
import com.ilearn.user_service.util.AppConstants;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

	@Mock
    private UserRepository userRepository;
	
	@Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    private UserModel user;
    
    @BeforeEach
    void setUp() {
        user = new UserModel();
        user.setId(1L);
        user.setUserName("rakesh");
        user.setPassword("password");
        user.setFirstName("Rakesh");
        user.setLastName("Kumar");
        user.setEmailId("rakesh@gmail.com");
        user.setMobileNo("9876543210");
        user.setRole("USER");
    }
    
    @Test
    void testUpdateProfile_Success() {

        when(userRepository.findByUserName("rakesh")).thenReturn(user);

        UserModel request = new UserModel();
        request.setFirstName("John");
        request.setLastName("Doe");
        request.setEmailId("john@gmail.com");
        request.setMobileNo("9999999999");

        when(userRepository.save(any(UserModel.class))).thenReturn(user);

        ApiResponse response = userService.updateProfile("rakesh", request);

        assertEquals(AppConstants.SUCCESS_CODE, response.getStatusCode());
        assertEquals(AppConstants.UPDATED, response.getMessage());

        verify(userRepository).findByUserName("rakesh");
        verify(userRepository).save(any(UserModel.class));

        assertEquals("John", user.getFirstName());
        assertEquals("Doe", user.getLastName());
        assertEquals("john@gmail.com", user.getEmailId());
        assertEquals("9999999999", user.getMobileNo());
    }
    
    @Test
    void testUpdateProfile_UserNotFound() {

        when(userRepository.findByUserName("rakesh")).thenReturn(null);

        ApiResponse response = userService.updateProfile("rakesh", user);

        assertEquals(AppConstants.FAILURE_CODE, response.getStatusCode());
        assertEquals(AppConstants.USER_NOT_FOUND, response.getMessage());

        verify(userRepository).findByUserName("rakesh");
        verify(userRepository, never()).save(any());
    }
    
    @Test
    void testCreateUser_Success() {

        when(userRepository.existsByUserName(anyString())).thenReturn(false);
        when(userRepository.existsByEmailId(anyString())).thenReturn(false);
        when(userRepository.existsByMobileNo(anyString())).thenReturn(false);

        when(passwordEncoder.encode(anyString())).thenReturn("encryptedPassword");
        when(userRepository.save(any(UserModel.class))).thenReturn(user);

        ApiResponse response = userService.createUser(user);

        assertEquals(AppConstants.SUCCESS_CODE, response.getStatusCode());

        verify(passwordEncoder).encode("password");
        verify(userRepository).save(any(UserModel.class));
    }
    
    @Test
    void testCreateUser_DuplicateUserName() {

        when(userRepository.existsByUserName("rakesh")).thenReturn(true);

        ApiResponse response = userService.createUser(user);

        assertEquals(AppConstants.FAILURE_CODE, response.getStatusCode());
        assertEquals(AppConstants.USER_NAME_DUPLICATE, response.getMessage());

        verify(userRepository, never()).save(any());
    }
    
    @Test
    void testCreateUser_DuplicateEmail() {

        when(userRepository.existsByUserName(anyString())).thenReturn(false);
        when(userRepository.existsByEmailId(anyString())).thenReturn(true);

        ApiResponse response = userService.createUser(user);

        assertEquals(AppConstants.FAILURE_CODE, response.getStatusCode());
        assertEquals(AppConstants.EMAIL_ID_DUPLICATE, response.getMessage());
    }
    
    @Test
    void testCreateUser_DuplicateMobile() {

        when(userRepository.existsByUserName(anyString())).thenReturn(false);
        when(userRepository.existsByEmailId(anyString())).thenReturn(false);
        when(userRepository.existsByMobileNo(anyString())).thenReturn(true);

        ApiResponse response = userService.createUser(user);

        assertEquals(AppConstants.FAILURE_CODE, response.getStatusCode());
        assertEquals(AppConstants.MOBILE_DUPLICATE, response.getMessage());
    }
    
    @Test
    void testGetProfileDetail_Success() {

        when(userRepository.findByUserName("rakesh")).thenReturn(user);

        ApiResponse response = userService.getProfileDetail("rakesh");

        assertEquals(AppConstants.SUCCESS_CODE, response.getStatusCode());
        assertNotNull(response.getResult());

        verify(userRepository).findByUserName("rakesh");
    }
    
    @Test
    void testGetUsersByRole() {

        when(userRepository.findByRole("ADMIN"))
                .thenReturn(List.of(user));

        List<UserModel> users = userService.getUsersByRole("ADMIN");
        assertEquals(1, users.size());
        verify(userRepository).findByRole("ADMIN");
    }
    
    @Test
    void testUpdateUser_Success() {

        when(userRepository.existsByUserName("rakesh")).thenReturn(true);
        when(passwordEncoder.encode("password")).thenReturn("encrypted");

        ApiResponse response = userService.updateUser(user);

        assertEquals(AppConstants.SUCCESS_CODE, response.getStatusCode());

        verify(userRepository).updatePassword(
                eq("encrypted"),
                any(LocalDateTime.class),
                eq("rakesh"));
    }
    
    @Test
    void testUpdateUser_UserNotFound() {

        when(userRepository.existsByUserName("rakesh")).thenReturn(false);

        ApiResponse response = userService.updateUser(user);

        assertEquals(AppConstants.FAILURE_CODE, response.getStatusCode());

        verify(userRepository, never())
                .updatePassword(anyString(), any(), anyString());
    }
    
    @Test
    void testGetProfileDetail_NotFound() {

        when(userRepository.findByUserName("rakesh")).thenReturn(null);

        ApiResponse response = userService.getProfileDetail("rakesh");

        assertEquals(AppConstants.FAILURE_CODE, response.getStatusCode());
        assertEquals(AppConstants.USER_NOT_FOUND, response.getMessage());
    }
    
}
