package Pothole.websocket.Controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketController {
    private final SimpMessagingTemplate messagingTemplate;

    public WebSocketController(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @MessageMapping("/sendKmH") // 클라이언트가 /app/sendKmH로 메시지를 보내면 해당 메서드가 호출됨.
    public void sendGyroData(String message) {
        System.out.println("Received message from client: " + message); // 받은 값을 출력
        messagingTemplate.convertAndSend("/topic/km-h", message); // 받은 메시지를 클라이언트로 전송
    }

    @SubscribeMapping("/topic/km-h")
    public void receiveGyroData(String message) {
        System.out.println("Received from ActiveMQ: " + message);
        // 원하는 추가 처리 수행
    }
}
