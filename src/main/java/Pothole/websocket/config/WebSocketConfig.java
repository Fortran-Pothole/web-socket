package Pothole.websocket.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker //메시지 플로우를 모으기 위해 컴포넌트 구성
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/socket")
        .setAllowedOriginPatterns("*")
        .withSockJS();
    }

    @Override // 클라이언트가 특정 주제 topic에만 관련된메시지만받을수있음
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/topic"); // Simple Broker 활성화
        registry.setApplicationDestinationPrefixes("/app"); // 클라이언트가 보내는 메시지의 prefix
    }
}
