package hr.leonardom011.hivetechinterview.users.service;

import hr.leonardom011.hivetechinterview.users.model.entity.UserEntity;
import hr.leonardom011.hivetechinterview.users.model.request.RegisterUserRequest;
import hr.leonardom011.hivetechinterview.users.model.response.UserResponse;
import org.springframework.transaction.annotation.Transactional;

public interface UserService {

    UserEntity getUserByUsername(String username);

    @Transactional
    UserEntity createUser(RegisterUserRequest registerUserRequest);

    UserResponse getCurrentUser();
}
