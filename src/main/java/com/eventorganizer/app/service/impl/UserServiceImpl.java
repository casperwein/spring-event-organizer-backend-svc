package com.eventorganizer.app.service.impl;

import com.eventorganizer.app.entity.UserEntity;
import com.eventorganizer.app.payload.AuthRequest;
import com.eventorganizer.app.payload.AuthResponse;
import com.eventorganizer.app.payload.UserDto;
import com.eventorganizer.app.repository.UserRepository;
import com.eventorganizer.app.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public UserDto createUser(UserDto userDto) {
        UserEntity user = mapToEntity(userDto);

        UserEntity newUser = userRepository.save(user);
        UserDto userRes = mapToDto(newUser);

        return userRes;
    }

    @Override
    public List<UserDto> getAllUser() {
        List<UserEntity> user = userRepository.findAll();
        return user.stream().map(usr -> mapToDto(usr)).collect(Collectors.toList());
    }

    @Override
    public AuthResponse register(UserDto request) {
        var user = User.builder()
            .username(request.getUsername())
            .password(passwordEncoder.encode(request.getPassword()))
            .roles(String.valueOf(request.getRole()))
                .build();

        UserEntity userEntity = mapToEntity(request);
        userEntity.setPassword(passwordEncoder.encode(request.getPassword()));

        userRepository.save(userEntity);

        var jwtToken = jwtService.generateToken(userEntity);
        return AuthResponse.builder()
                .token(jwtToken)
                .build();
    }

    @Override
    public AuthResponse loginAuthenticate(AuthRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = userRepository.findByemail(request.getEmail()).orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        return AuthResponse.builder()
                .token(jwtToken)
                .build();
    }


    public UserDto mapToDto(UserEntity user){
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
        userDto.setEmail(user.getEmail());

        return userDto;
    }

    public UserEntity mapToEntity(UserDto userDto){
        UserEntity user = new UserEntity();

        user.setId(userDto.getId());
        user.setNama(userDto.getNama());
        user.setUsername(userDto.getUsername());
        user.setRole(userDto.getRole());
        user.setPassword(userDto.getPassword());
        user.setStatus(userDto.getStatus());
        user.setTelepon(userDto.getTelepon());
        user.setEmail(userDto.getEmail());

        return user;
    }
}
