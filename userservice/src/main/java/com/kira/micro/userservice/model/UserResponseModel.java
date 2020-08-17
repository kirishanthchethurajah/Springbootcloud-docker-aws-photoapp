package com.kira.micro.userservice.model;

import lombok.Data;


@Data
public class UserResponseModel {
    private String firstName;
    private String lastName;
    private String emailId;
    private String userId;
}
