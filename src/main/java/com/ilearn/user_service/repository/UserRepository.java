package com.ilearn.user_service.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ilearn.user_service.model.UserModel;

@Repository
public interface UserRepository extends JpaRepository<UserModel,Long>{

}
