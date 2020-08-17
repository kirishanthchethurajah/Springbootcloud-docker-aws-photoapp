package com.kira.micro.userservice.service;

import com.kira.micro.userservice.dto.UserDto;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    UserDto createUser(UserDto userDto);
    UserDto getUserDetailsByEmail(String email);

    UserDto getUserDetailsByUserId(String userId);
}
