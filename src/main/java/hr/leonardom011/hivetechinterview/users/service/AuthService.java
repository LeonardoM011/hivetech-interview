package hr.leonardom011.hivetechinterview.users.service;

import hr.leonardom011.hivetechinterview.users.model.request.LoginUserRequest;
import hr.leonardom011.hivetechinterview.users.model.request.RegisterUserRequest;
import hr.leonardom011.hivetechinterview.users.model.response.LoginUserResponse;
import hr.leonardom011.hivetechinterview.users.model.response.RegisterUserResponse;
import org.springframework.transaction.annotation.Transactional;

public interface AuthService {

    @Transactional
    RegisterUserResponse registerUser(RegisterUserRequest registerUserRequest);

    LoginUserResponse loginUser(LoginUserRequest loginUserRequest);

}
