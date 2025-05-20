package hr.leonardom011.hivetechinterview.users.controller;

import hr.leonardom011.hivetechinterview.users.model.request.LoginUserRequest;
import hr.leonardom011.hivetechinterview.users.model.request.RegisterUserRequest;
import hr.leonardom011.hivetechinterview.users.model.response.LoginUserResponse;
import hr.leonardom011.hivetechinterview.users.model.response.RegisterUserResponse;
import hr.leonardom011.hivetechinterview.users.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@Validated
@CrossOrigin
@RequestMapping("api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping(value = "/signup", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Register a new user", description = "Endpoint for registering a new user")
    public ResponseEntity<RegisterUserResponse> registerUser(@RequestBody @Valid RegisterUserRequest registerUserRequest) {
        log.info("POST /api/auth/signup started");
        RegisterUserResponse response = authService.registerUser(registerUserRequest);
        log.info("POST /api/auth/signup finished");
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Login a user", description = "Endpoint for logging in a user")
    public ResponseEntity<LoginUserResponse> loginUser(@RequestBody @Valid LoginUserRequest loginUserRequest) {
        log.info("POST /api/auth/login started");
        LoginUserResponse response = authService.loginUser(loginUserRequest);
        log.info("POST /api/auth/login finished");
        return ResponseEntity.ok(response);
    }

}
