package hr.leonardom011.hivetechinterview.users.service.impl;

import hr.leonardom011.hivetechinterview.exception.KanbanException;
import hr.leonardom011.hivetechinterview.users.model.entity.UserEntity;
import hr.leonardom011.hivetechinterview.users.model.mapper.UserMapper;
import hr.leonardom011.hivetechinterview.users.model.request.RegisterUserRequest;
import hr.leonardom011.hivetechinterview.users.model.response.UserResponse;
import hr.leonardom011.hivetechinterview.users.repository.UserRepository;
import hr.leonardom011.hivetechinterview.users.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.MessageFormat;

import static hr.leonardom011.hivetechinterview.constant.ExceptionConstant.USER_USERNAME_NOT_FOUND;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public UserEntity getUserByUsername(String username) {
        log.info("Fetching user with username {}", username);
        return userRepository
                .findByIsDeletedFalseAndUsername(username)
                .orElseThrow(() -> new KanbanException(HttpStatus.NOT_FOUND, MessageFormat.format(USER_USERNAME_NOT_FOUND, username)));
    }

    @Transactional
    @Override
    public UserEntity createUser(RegisterUserRequest registerUserRequest) {
        log.info("Creating a new user");
        UserEntity userEntity = userMapper.mapToUserEntity(registerUserRequest);
        return userRepository.save(userEntity);
    }

    @Override
    public UserResponse getCurrentUser() {
        log.info("Fetching current user");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserEntity currentUser = (UserEntity) authentication.getPrincipal();
        return userMapper.mapToUserResponse(currentUser);
    }
}
