package Pothole.websocket.Controller;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class MessageConsumer {

    @JmsListener(destination = "/app/sendKmH")
    public void receiveMessage(String message) {
        System.out.println("Received message: " + message);
        // 메시지를 처리하는 로직 추가
    }
}