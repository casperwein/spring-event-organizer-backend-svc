package com.eventorganizer.app.service;

import com.eventorganizer.app.payload.UserDto;

import java.util.List;

public interface UserService {
    UserDto createUser(UserDto userDto);
    List<UserDto> getAllUser();
}
