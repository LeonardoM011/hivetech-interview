package hr.leonardom011.hivetechinterview.users.model.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginUserResponse {

    private String token;
    private Long expiresIn;

}
