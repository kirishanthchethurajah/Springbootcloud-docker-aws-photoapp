package com.kira.micro.userservice.dto;

import com.kira.micro.userservice.model.AlbumCollection;
import lombok.Data;

import java.io.Serializable;
import java.util.List;


@Data
public class UserDto implements Serializable {
    private String firstName;
    private String lastName;
    private String emailId;
    private String password;
    private String encryptedPassword;
    private String userId;
    private List<AlbumCollection> albums;
}
