package hr.leonardom011.hivetechinterview.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import static hr.leonardom011.hivetechinterview.constant.SecurityConstant.JWT_AUTH_NAME;

@Configuration
@ComponentScan(basePackages = {"hr.leonardom011.hivetechinterview"})
@OpenAPIDefinition(info = @Info(title = "KanbanAPI", version = "1.0", description = "Kanban API documentation",
    contact = @Contact(name = "Leonardo Marinovic", email = "leonardo.leo.201@gmail.com")))
@SecurityScheme(name = JWT_AUTH_NAME, type = SecuritySchemeType.HTTP, bearerFormat = "JWT", scheme = "bearer")
public class ApplicationConfig implements WebMvcConfigurer {

    private static final String ROOT_URL = "/";
    private final String SWAGGER_UI_URL = "/swagger-ui.html";
    private final String APPLICATION_NAME = "kanban-api";

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addRedirectViewController(ROOT_URL, SWAGGER_UI_URL);
    }
}
