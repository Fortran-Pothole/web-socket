package Pothole.websocket.exception;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import java.util.ArrayList;
import java.util.List;
@Component
public class WebSocketHandler extends TextWebSocketHandler {

    // 연결된 세션을 저장하는 리스트
    private final List<WebSocketSession> sessions = new ArrayList<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessions.add(session);
        System.out.println("New connection established: " + session.getId());
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        System.out.println("Received message: " + message.getPayload());

        try {
            // 받은 메시지를 정수로 변환
            int velocity = Integer.parseInt(message.getPayload().trim());
            String responseMessage = String.valueOf(velocity);

            // 다른 클라이언트에게 속도 데이터를 전송
            for (WebSocketSession wsSession : sessions) {
                if (wsSession.isOpen() && !wsSession.getId().equals(session.getId())) {
                    wsSession.sendMessage(new TextMessage(responseMessage));
                    System.out.println("Sent response message to session " + wsSession.getId() + ": " + responseMessage);
                }
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid message format: " + message.getPayload());
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, org.springframework.web.socket.CloseStatus status) throws Exception {
        sessions.remove(session);
        System.out.println("Connection closed: " + session.getId());
    }
}
