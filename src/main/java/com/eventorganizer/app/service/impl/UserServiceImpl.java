package com.eventorganizer.app.service.impl;

import com.eventorganizer.app.entity.User;
import com.eventorganizer.app.payload.UserDto;
import com.eventorganizer.app.repository.UserRepository;
import com.eventorganizer.app.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public UserDto createUser(UserDto userDto) {
//        String password = userDto.getPassword();
//        String encryptPswd = passwordEncoder.encode(password);
//        System.out.println(encryptPswd);
//        userDto.setPassword(encryptPswd);

        User user = mapToEntity(userDto);

        User newUser = userRepository.save(user);
        UserDto userRes = mapToDto(newUser);

        return userRes;
    }

    @Override
    public List<UserDto> getAllUser() {
        List<User> user = userRepository.findAll();
        return user.stream().map(usr -> mapToDto(usr)).collect(Collectors.toList());
    }

    public UserDto mapToDto(User user){
        UserDto userDto = new UserDto();

        userDto.setId(user.getId());
        userDto.setNama(user.getNama());
        userDto.setUsername(user.getUsername());
        userDto.setPassword(user.getPassword());
        userDto.setRole(user.getRole());
        userDto.setStatus(user.getStatus());
        userDto.setTelepon(user.getTelepon());
        userDto.setCreatedAt(user.getCreatedAt());
        userDto.setUpdatedAt(user.getUpdatedAt());

        return userDto;
    }

    public User mapToEntity(UserDto userDto){
        User user = new User();

        user.setId(userDto.getId());
        user.setNama(userDto.getNama());
        user.setUsername(userDto.getUsername());
        user.setRole(userDto.getRole());
        user.setPassword(userDto.getPassword());
        user.setStatus(userDto.getStatus());
        user.setTelepon(userDto.getTelepon());

        return user;
    }
}
