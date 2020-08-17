package com.kira.micro.userservice.model;

import lombok.Data;

@Data
public class LoginRequestModel {
    private String emailId;
    private String password;


}
