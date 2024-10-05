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
            // JSON 형식으로 메시지를 파싱하여 velocity 값 추출
            VelocityData data = objectMapper.readValue(message, VelocityData.class);
            int velocity = data.getVelocity();
            System.out.println("JMS Received velocity: " + velocity);

            // 클라이언트에 숫자만 전송
            messagingTemplate.convertAndSend("/topic/velocity", velocity);
        } catch (JsonProcessingException e) {
            System.out.println("JMS JSON 파싱 오류: " + e.getMessage());
            System.out.println("JMS Received raw message: " + message);
        } catch (Exception e) {
            System.out.println("JMS 처리 오류: " + e.getMessage());
            System.out.println("JMS Received raw message: " + message);
        }
    }
}