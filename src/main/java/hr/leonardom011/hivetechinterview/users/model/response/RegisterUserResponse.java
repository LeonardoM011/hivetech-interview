package hr.leonardom011.hivetechinterview.users.model.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class RegisterUserResponse {

    private UUID id;
    private String username;
    private String token;
    private Long expiresIn;
    private List<String> authorities;
    private Boolean accountNonExpired;
    private Boolean accountNonLocked;
    private Boolean credentialsNonExpired;
    private Boolean enabled;
}
