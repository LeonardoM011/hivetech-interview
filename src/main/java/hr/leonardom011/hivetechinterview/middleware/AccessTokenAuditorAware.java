package hr.leonardom011.hivetechinterview.middleware;

import hr.leonardom011.hivetechinterview.users.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.AuditorAware;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@Slf4j
public class AccessTokenAuditorAware implements AuditorAware<String> {

    private final UserService userService;

    AccessTokenAuditorAware(UserService userService) {
        this.userService = userService;
    }

    @Override
    @NonNull
    public Optional<String> getCurrentAuditor() {
        return Optional.of(userService.getCurrentUser().getUsername());
    }
}
