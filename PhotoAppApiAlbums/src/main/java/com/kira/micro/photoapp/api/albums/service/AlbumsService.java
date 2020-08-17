package com.kira.micro.photoapp.api.albums.service;


import com.kira.micro.photoapp.api.albums.data.AlbumEntity;
import java.util.List;

public interface AlbumsService {
    List<AlbumEntity> getAlbums(String userId);
}
