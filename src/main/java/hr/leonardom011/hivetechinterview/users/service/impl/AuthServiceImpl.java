package hr.leonardom011.hivetechinterview.users.service.impl;

import hr.leonardom011.hivetechinterview.jwt.service.JwtService;
import hr.leonardom011.hivetechinterview.users.model.entity.UserEntity;
import hr.leonardom011.hivetechinterview.users.model.mapper.UserMapper;
import hr.leonardom011.hivetechinterview.users.model.request.LoginUserRequest;
import hr.leonardom011.hivetechinterview.users.model.request.RegisterUserRequest;
import hr.leonardom011.hivetechinterview.users.model.response.LoginUserResponse;
import hr.leonardom011.hivetechinterview.users.model.response.RegisterUserResponse;
import hr.leonardom011.hivetechinterview.users.service.AuthService;
import hr.leonardom011.hivetechinterview.users.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtService jwtService;
    private final UserMapper userMapper;

    public AuthServiceImpl(PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, UserService userService, JwtService jwtService, JwtService jwtService1, UserMapper userMapper) {
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.jwtService = jwtService1;
        this.userMapper = userMapper;
    }

    @Override
    @Transactional
    public RegisterUserResponse registerUser(RegisterUserRequest registerUserRequest) {
        registerUserRequest.setPassword(passwordEncoder.encode(registerUserRequest.getPassword()));
        UserEntity userEntity = userService.createUser(registerUserRequest);
        return userMapper.mapToRegisterUserResponse(userEntity, jwtService.generateToken(userEntity), jwtService.getExpirationTime());
    }

    @Override
    public LoginUserResponse loginUser(LoginUserRequest loginUserRequest) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginUserRequest.getUsername(),
                loginUserRequest.getPassword()));

        UserEntity userEntity = userService.getUserByUsername(loginUserRequest.getUsername());

        LoginUserResponse response = new LoginUserResponse();
        response.setToken(jwtService.generateToken(userEntity));
        response.setExpiresIn(jwtService.getExpirationTime());
        return response;
    }
}
