package Pothole.websocket.Controller;

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
            // 메시지가 ASCII 코드값으로 전달된 경우 처리
            if (message.matches("^\\d+(,\\d+)*$")) {
                String[] asciiValues = message.split(",");
                StringBuilder decodedMessage = new StringBuilder();
                for (String value : asciiValues) {
                    decodedMessage.append((char) Integer.parseInt(value));
                }
                message = decodedMessage.toString();
            }

            // 숫자 값을 파싱
            int velocity = Integer.parseInt(message);
            System.out.println("JMS Received velocity: " + velocity);

            // 클라이언트에 숫자만 전송
            messagingTemplate.convertAndSend("/topic/velocity", velocity);
        } catch (Exception e) {
            System.out.println("JMS 처리 오류: " + e.getMessage());
            System.out.println("JMS Received raw message: " + message);
        }
    }
}