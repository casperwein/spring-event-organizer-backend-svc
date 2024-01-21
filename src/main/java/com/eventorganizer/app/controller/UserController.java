package com.eventorganizer.app.controller;

import com.eventorganizer.app.payload.CustomeResponse;
import com.eventorganizer.app.payload.UserDto;
import com.eventorganizer.app.service.UserService;
import com.eventorganizer.app.util.Utils;
import org.apache.catalina.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/spring/eo/v1/user")
public class UserController {
    private UserService userService;
    Utils utils = new Utils();

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto){
        return new ResponseEntity<>(userService.createUser(userDto), HttpStatus.CREATED);
    }

    @GetMapping("/getUser")
    public ResponseEntity<CustomeResponse> getAllUser(){
        CustomeResponse response = utils.customeResponses();
        List<UserDto> userData = userService.getAllUser();
        response.setData(userData);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
