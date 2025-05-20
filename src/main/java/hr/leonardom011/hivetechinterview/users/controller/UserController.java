package hr.leonardom011.hivetechinterview.users.controller;

import hr.leonardom011.hivetechinterview.users.model.response.UserResponse;
import hr.leonardom011.hivetechinterview.users.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@Validated
@CrossOrigin
@RequestMapping("api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/me", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get current user", description = "Endpoint for getting the current user")
    @SecurityRequirement(name = "bearerAuth")
    @Secured("ROLE_USER")
    public ResponseEntity<UserResponse> getCurrentUser() {
        log.info("GET /api/users/me started");
        UserResponse response = userService.getCurrentUser();
        log.info("GET /api/users/me finished");
        return ResponseEntity.ok(response);
    }
}
