package Pothole.websocket.exception;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class WebSocketHandler extends TextWebSocketHandler {

    private final Set<WebSocketSession> sessions = new HashSet<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessions.add(session);
        System.out.println("New connection established: " + session.getId());
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        System.out.println("Received message: " + message.getPayload());
        try {
            int velocity = Integer.parseInt(message.getPayload().trim());
            String responseMessage = String.valueOf(velocity);

            // 모든 연결된 클라이언트에게 메시지를 전송
            for (WebSocketSession s : sessions) {
                if (s.isOpen() && s != session) {  // 현재 보낸 세션을 제외하고 전송
                    s.sendMessage(new TextMessage(responseMessage));
                }
            }
            System.out.println("Broadcasted velocity: " + responseMessage);
        } catch (NumberFormatException e) {
            System.out.println("Invalid message format: " + message.getPayload());
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        sessions.remove(session);
        System.out.println("Connection closed: " + session.getId());
    }
}
