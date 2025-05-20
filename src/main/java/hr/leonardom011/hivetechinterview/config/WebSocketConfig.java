package hr.leonardom011.hivetechinterview.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import static hr.leonardom011.hivetechinterview.constant.WebSocketConstant.TASK_PATH;
import static hr.leonardom011.hivetechinterview.constant.WebSocketConstant.TOPIC_PATH;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker(TOPIC_PATH);
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint(TASK_PATH).setAllowedOriginPatterns("*");
        registry.addEndpoint(TASK_PATH).setAllowedOriginPatterns("*").withSockJS();
    }

}
