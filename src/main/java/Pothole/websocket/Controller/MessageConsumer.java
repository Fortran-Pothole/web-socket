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

    private final ObjectMapper objectMapper = new ObjectMapper();

    @JmsListener(destination = "/app/sendKmH")
    public void receiveMessage(String message) {
        try {
            VelocityData data = objectMapper.readValue(message, VelocityData.class);
            System.out.println("JMS Received message: " + data);
        } catch (Exception e) {
            System.out.println("JMS JSON 파싱 오류: " + e.getMessage());
            System.out.println("JMS Received raw message: " + message);
        }
    }
}