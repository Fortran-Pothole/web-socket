package Pothole.websocket.exception;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
public class WebSocketHandler extends TextWebSocketHandler {

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        // 클라이언트로부터 메시지를 받았을 때 처리하는 로직 작성
        System.out.println("Received message: " + message.getPayload());
        session.sendMessage(new TextMessage("메시지를 잘 받았습니다."));
    }
}
