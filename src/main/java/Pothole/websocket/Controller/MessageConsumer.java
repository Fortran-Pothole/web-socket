package Pothole.websocket.Controller;

import Pothole.websocket.entity.VelocityData;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
public class MessageConsumer {
    private final SimpMessagingTemplate messagingTemplate;

    public MessageConsumer(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @JmsListener(destination = "/app/sendKmH")
    public void receiveMessage(String message) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        VelocityData data = objectMapper.readValue(message, VelocityData.class);
        System.out.println("Jms Received velocity: " + data.getVelocity());
        //System.out.println("Received message from client: " + message); // 받은 값을 출력
        messagingTemplate.convertAndSend("/topic/km-h", data.getVelocity()); // 받은 메시지를 클라이언트로 전송
        System.out.println("Jms Received message: " + message);
        // 메시지를 처리하는 로직 추가
    }
}