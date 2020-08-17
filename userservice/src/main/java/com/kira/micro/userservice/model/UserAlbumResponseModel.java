package com.kira.micro.userservice.model;

import lombok.Data;

import java.util.List;

@Data
public class UserAlbumResponseModel {
    private String firstName;
    private String lastName;
    private String emailId;
    private String userId;
    private List<AlbumCollection> albums;
}
