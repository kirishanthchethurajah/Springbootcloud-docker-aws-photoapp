package com.kira.micro.userservice.repository;

import com.kira.micro.userservice.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity,Long> {
UserEntity findByEmailId(String emailId);

UserEntity findByUserId(String userId);
}
