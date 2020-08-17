package com.kira.micro.userservice.controller;

import com.kira.micro.userservice.dto.UserDto;
import com.kira.micro.userservice.entity.UserEntity;
import com.kira.micro.userservice.model.UserAlbumResponseModel;
import com.kira.micro.userservice.model.UserRequestModel;
import com.kira.micro.userservice.model.UserResponseModel;
import com.kira.micro.userservice.service.UserService;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("users")
public class UserServiceController {

    UserService userService;
    private Environment environment;

    public UserServiceController(UserService userService, Environment environment) {
        this.userService = userService;
        this.environment = environment;
    }

    @GetMapping("/status/check")
    public String status(){
        return "Working on port"+ environment.getProperty("local.server.port")+environment.getProperty("token.secret");
    }

    @PostMapping(
            consumes = {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<UserResponseModel> addUser(@Valid @RequestBody UserRequestModel requestModel){
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        UserDto userDto =mapper.map(requestModel, UserDto.class);
        UserDto createdUser = userService.createUser(userDto);
        UserResponseModel returnValue = mapper.map(createdUser,UserResponseModel.class);
        return  ResponseEntity.status(HttpStatus.CREATED).body(returnValue);

    }

    @GetMapping(value = "/{userId}",produces = {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<UserAlbumResponseModel> getUser(@PathVariable String userId){
        UserDto userDto = userService.getUserDetailsByUserId(userId);
        UserAlbumResponseModel responseModel = new ModelMapper().map(userDto,UserAlbumResponseModel.class);
        return ResponseEntity.status(HttpStatus.OK).body(responseModel);
    }
}
