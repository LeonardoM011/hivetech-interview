package hr.leonardom011.hivetechinterview.users.model.mapper;

import hr.leonardom011.hivetechinterview.users.model.entity.UserEntity;
import hr.leonardom011.hivetechinterview.users.model.request.RegisterUserRequest;
import hr.leonardom011.hivetechinterview.users.model.response.RegisterUserResponse;
import hr.leonardom011.hivetechinterview.users.model.response.UserResponse;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

@Component
public class UserMapper {
    public UserResponse mapToUserResponse(UserEntity userEntity) {
        UserResponse userResponse = new UserResponse();
        userResponse.setId(userEntity.getId());
        userResponse.setUsername(userEntity.getUsername());
        userResponse.setAuthorities(getAuthorities(userEntity.getAuthorities()));
        userResponse.setAccountNonExpired(userEntity.isAccountNonExpired());
        userResponse.setAccountNonLocked(userEntity.isAccountNonLocked());
        userResponse.setCredentialsNonExpired(userEntity.isCredentialsNonExpired());
        userResponse.setEnabled(userEntity.isEnabled());
        return userResponse;
    }

    public UserEntity mapToUserEntity(RegisterUserRequest registerUserRequest) {
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(registerUserRequest.getUsername());
        userEntity.setPassword(registerUserRequest.getPassword());
        userEntity.setDeleted(false);
        return userEntity;
    }

    public RegisterUserResponse mapToRegisterUserResponse(UserEntity userEntity, String jwtToken, Long expiresIn) {
        RegisterUserResponse response = new RegisterUserResponse();
        response.setId(userEntity.getId());
        response.setUsername(userEntity.getUsername());
        response.setToken(jwtToken);
        response.setExpiresIn(expiresIn);
        response.setAuthorities(getAuthorities(userEntity.getAuthorities()));
        response.setAccountNonExpired(userEntity.isAccountNonExpired());
        response.setAccountNonLocked(userEntity.isAccountNonLocked());
        response.setCredentialsNonExpired(userEntity.isCredentialsNonExpired());
        response.setEnabled(userEntity.isEnabled());
        return response;
    }

    private List<String> getAuthorities(Collection<? extends GrantedAuthority> authorities) {
        return authorities
                .stream()
                .map(GrantedAuthority::getAuthority)
                .toList();
    }
}
