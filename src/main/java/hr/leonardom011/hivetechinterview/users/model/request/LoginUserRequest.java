package hr.leonardom011.hivetechinterview.users.model.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginUserRequest {

    @NotBlank
    private String username;
    @NotBlank
    private String password;

}
