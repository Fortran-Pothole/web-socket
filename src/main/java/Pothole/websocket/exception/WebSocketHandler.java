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
        String responseMessage = "속도 데이터를 수신했습니다: " + message.getPayload();

        // 받은 데이터를 다시 클라이언트로 보내는 예제
        session.sendMessage(new TextMessage(responseMessage));
        System.out.println("Sent response message: " + responseMessage);
    }
}
