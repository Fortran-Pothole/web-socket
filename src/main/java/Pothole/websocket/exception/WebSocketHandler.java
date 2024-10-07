package Pothole.websocket.exception;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Component
public class WebSocketHandler extends TextWebSocketHandler {

    private final Set<WebSocketSession> sessions = Collections.synchronizedSet(new HashSet<>());
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessions.add(session);
        System.out.println("New connection established: " + session.getId());
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        System.out.println("Received message: " + message.getPayload());

        try {
            // JSON 형식의 메시지를 파싱하여 velocity 값 추출
            JsonNode jsonNode = objectMapper.readTree(message.getPayload());
            int velocity = jsonNode.get("velocity").asInt();

            // 모든 다른 클라이언트에게 전송
            for (WebSocketSession s : sessions) {
                if (s.isOpen() && s != session) {
                    s.sendMessage(new TextMessage(String.valueOf(velocity)));
                }
            }
            System.out.println("Broadcasted velocity: " + velocity);
        } catch (Exception e) {
            System.out.println("Invalid message format: " + message.getPayload());
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        sessions.remove(session);
        System.out.println("Connection closed: " + session.getId());
    }
}
