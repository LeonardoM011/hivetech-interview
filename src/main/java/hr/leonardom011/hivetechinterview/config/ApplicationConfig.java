package hr.leonardom011.hivetechinterview.config;

import hr.leonardom011.hivetechinterview.users.service.UserService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import static hr.leonardom011.hivetechinterview.constant.SecurityConstant.JWT_AUTH_NAME;

@Configuration
@ComponentScan(basePackages = {"hr.leonardom011.hivetechinterview"})
@OpenAPIDefinition(info = @Info(title = "KanbanAPI", version = "1.0", description = "Kanban API documentation",
    contact = @Contact(name = "Leonardo Marinovic", email = "leonardo.leo.201@gmail.com")))
@SecurityScheme(name = JWT_AUTH_NAME, type = SecuritySchemeType.HTTP, bearerFormat = "JWT", scheme = "bearer")
@EnableJpaAuditing
public class ApplicationConfig implements WebMvcConfigurer {

    private final UserService userService;

    private static final String ROOT_URL = "/";
    private final String SWAGGER_UI_URL = "/swagger-ui.html";
    private final String APPLICATION_NAME = "kanban-api";

    public ApplicationConfig(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addRedirectViewController(ROOT_URL, SWAGGER_UI_URL);
    }

    @Bean
    UserDetailsService userDetailsService() {
        return userService::getUserByUsername;
    }

    @Bean
    BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }
}
