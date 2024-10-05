package Pothole.websocket.Controller;

import Pothole.websocket.entity.VelocityData;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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

//    @MessageMapping("/sendKmH")
//    @SendTo("/topic/km-h")
//    public String processMessageFromClient(String message) {
//        System.out.println("processMessageFromClient 메서드 호출됨");
//        System.out.println("Received message from client: " + message);
//        return message; // 메시지를 다시 주제로 전송
//    }

//    @MessageMapping("/sendKmH") // 클라이언트가 /app/sendKmH로 메시지를 보내면 해당 메서드가 호출됨.
//    public void sendGyroData(String message) throws JsonProcessingException {
//        ObjectMapper objectMapper = new ObjectMapper();
//        VelocityData data = objectMapper.readValue(message, VelocityData.class);
//        System.out.println("Received velocity: " + data.getVelocity());
//        //System.out.println("Received message from client: " + message); // 받은 값을 출력
//        messagingTemplate.convertAndSend("/topic/km-h", data.getVelocity()); // 받은 메시지를 클라이언트로 전송
//    }

    @SubscribeMapping("/topic/km-h")
    public void receiveGyroData(String message) {
        System.out.println("Received from ActiveMQ: " + message);
        // 원하는 추가 처리 수행
    }
}
