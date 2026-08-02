package com.ilearn.user_service.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ilearn.user_service.model.UserModel;

import jakarta.transaction.Transactional;

@Repository
public interface UserRepository extends JpaRepository<UserModel, Long> {

	boolean existsByUserName(String userName);

	boolean existsByEmailId(String emailId);

	boolean existsByMobileNo(String mobileNo);

	List<UserModel> findByRole(String role);

	UserModel findByUserName(String userName);

	UserModel findByMobileNo(String mobileNo);

	@Transactional
	@Modifying
	@Query("""
			UPDATE UserModel u SET u.password = :password, u.modifiedDateTime = :modifiedDateTime WHERE u.userName = :userName """)
	int updatePassword(@Param("password") String password, @Param("modifiedDateTime") LocalDateTime modifiedDateTime,
			@Param("userName") String userName);

}
