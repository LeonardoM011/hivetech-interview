package hr.leonardom011.hivetechinterview.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Configuration;
import org.zalando.problem.jackson.ProblemModule;
import org.zalando.problem.violations.ConstraintViolationProblemModule;

@Configuration
public class ProblemConfig implements InitializingBean {

    private final ObjectMapper objectMapper;

    public ProblemConfig(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void afterPropertiesSet() {
        objectMapper.registerModules(new ProblemModule(), new ConstraintViolationProblemModule());
    }
}
