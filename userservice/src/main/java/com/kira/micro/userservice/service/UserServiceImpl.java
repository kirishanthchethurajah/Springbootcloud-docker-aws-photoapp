package com.kira.micro.userservice.service;

import com.kira.micro.userservice.dto.UserDto;
import com.kira.micro.userservice.entity.UserEntity;
import com.kira.micro.userservice.model.AlbumCollection;
import com.kira.micro.userservice.repository.UserRepository;
import com.kira.micro.userservice.repository.AlbumFeignServiceClient;
import feign.FeignException;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
@Service
public class UserServiceImpl implements UserService {

    UserRepository userRepository;
    BCryptPasswordEncoder passwordEncoder;
//    RestTemplate restTemplate;
    AlbumFeignServiceClient albumFeignServiceClient;
    Logger logger= LoggerFactory.getLogger(this.getClass());
   /* public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder, RestTemplate restTemplate) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.restTemplate = restTemplate;

    }*/
    @Autowired
    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder, AlbumFeignServiceClient albumFeignServiceClient) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
//        this.restTemplate = restTemplate;
        this.albumFeignServiceClient = albumFeignServiceClient;
    }



    @Override
    public UserDto createUser(UserDto userDto) {
        userDto.setUserId(UUID.randomUUID().toString());
        userDto.setEncryptedPassword(passwordEncoder.encode(userDto.getPassword()));
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        UserEntity userEntity = mapper.map(userDto, UserEntity.class);
        userRepository.save(userEntity);
        UserDto returnValue = mapper.map(userEntity,UserDto.class);
        return returnValue;
    }

    @Override
    public UserDto getUserDetailsByEmail(String email) {
        UserEntity userEntity = userRepository.findByEmailId(email);

        if(userEntity == null) throw new UsernameNotFoundException(email);

        return new ModelMapper().map(userEntity,UserDto.class);
    }

    @Override
    public UserDto getUserDetailsByUserId(String userId) {
        UserEntity userEntity = userRepository.findByUserId(userId);
        if(userEntity == null) throw new UsernameNotFoundException(userId);
        /*String url = String.format("http://ALBUMS-SERVICE//users/%s/albums",userId);
        ResponseEntity<List<AlbumCollection>> responseEntity = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<AlbumCollection>>() {
        });
        List<AlbumCollection>  albumCollections = responseEntity.getBody();
        */
        UserDto userDto = new ModelMapper().map(userEntity,UserDto.class);
        /*List<AlbumCollection> albumCollections ;
        try{
        albumCollections=albumFeignServiceClient.getAlbums(userId);
        userDto.setAlbums(albumCollections);
        } catch (FeignException e){
            logger.error(e.getLocalizedMessage());

        }*/
        logger.info("Before calling album microservices for a userId:"+userId);
        List<AlbumCollection> albumCollections = albumFeignServiceClient.getAlbums(userId);
        logger.info("After calling album microservices for a userId:"+userId);
        userDto.setAlbums(albumCollections);


        return userDto;
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByEmailId(userName);

        if(userEntity == null) throw new UsernameNotFoundException(userName);

        return new User(userEntity.getEmailId(),userEntity.getEncryptedPassword(),true,true,true, true, new ArrayList<>());
    }



}