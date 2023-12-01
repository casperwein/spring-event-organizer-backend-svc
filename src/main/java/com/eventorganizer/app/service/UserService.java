package com.eventorganizer.app.service;

import com.eventorganizer.app.payload.AuthRequest;
import com.eventorganizer.app.payload.AuthResponse;
import com.eventorganizer.app.payload.UserDto;

import java.util.List;

public interface UserService {
    UserDto createUser(UserDto userDto);
    List<UserDto> getAllUser();

    AuthResponse register(UserDto request);
    AuthResponse loginAuthenticate(AuthRequest authRequest);
}
