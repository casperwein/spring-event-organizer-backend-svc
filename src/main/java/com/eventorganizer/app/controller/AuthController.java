package com.eventorganizer.app.controller;

import com.eventorganizer.app.payload.AuthRequest;
import com.eventorganizer.app.payload.AuthResponse;
import com.eventorganizer.app.payload.CustomeResponse;
import com.eventorganizer.app.payload.UserDto;
import com.eventorganizer.app.service.UserService;
import com.eventorganizer.app.util.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/spring/eo/v1/auth")
public class AuthController {
    private UserService userService;
    Utils utils = new Utils();

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<CustomeResponse> register(@RequestBody UserDto request){
        AuthResponse authResponse = userService.register(request);
        CustomeResponse customeResponse = utils.customeResponses();
        customeResponse.setData(authResponse);
        return new ResponseEntity<>(customeResponse, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<CustomeResponse> authenticate(@RequestBody AuthRequest request){
        CustomeResponse customeResponse = utils.customeResponses();
        AuthResponse authResponse = userService.loginAuthenticate(request);
        customeResponse.setData(authResponse);
        return new ResponseEntity<>(customeResponse, HttpStatus.OK);

    }
}
