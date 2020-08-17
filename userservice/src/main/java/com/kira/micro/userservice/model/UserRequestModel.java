package com.kira.micro.userservice.model;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class UserRequestModel {
    @NotNull(message = "firstName should not be null")
    @Size(min = 5, message = "first name should be at least 5 char")
    private String firstName;
    @NotNull(message = "lastName should not be null")
    @Size(min = 3, message = "last name should be at least 3 char")
    private String lastName;
    @NotNull(message = "email should not be null")
    @Email
    private String emailId;
    @NotNull(message = "password should not be null")
    @Size(min = 8, max = 16, message = "password must be between 8 and 16 characters ")
    private String password;

}
