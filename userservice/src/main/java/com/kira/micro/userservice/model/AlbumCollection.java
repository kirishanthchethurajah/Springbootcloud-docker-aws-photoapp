package com.kira.micro.userservice.model;

import lombok.Data;

@Data
public class AlbumCollection {
    private String userId;
    private String albumId;
    private String name;
    private String description;

}
