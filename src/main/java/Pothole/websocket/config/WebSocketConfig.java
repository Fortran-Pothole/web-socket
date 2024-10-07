package Pothole.websocket.config;

import Pothole.websocket.exception.WebSocketHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.web.socket.config.annotation.*;
import org.springframework.web.socket.server.HandshakeHandler;

@Configuration
@EnableWebSocket //메시지 플로우를 모으기 위해 컴포넌트 구성
public class WebSocketConfig implements WebSocketConfigurer {
    private final WebSocketHandler webSocketHandler;

    public WebSocketConfig(WebSocketHandler webSocketHandler) {
        this.webSocketHandler = webSocketHandler;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(webSocketHandler, "/socket")
                .setAllowedOrigins("*");
    }
}
