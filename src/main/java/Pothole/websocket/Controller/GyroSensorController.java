package Pothole.websocket.Controller;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

@Controller
@EnableScheduling
public class GyroSensorController {
    private final SimpMessagingTemplate messagingTemplate;

    public GyroSensorController (SimpMessagingTemplate simpMessagingTemplate) {
        this.messagingTemplate = simpMessagingTemplate; // Spring 의 메시지 테믈릿, 클라이언트에게 메시지를 전송하는 데 사용
    }
    @Scheduled(fixedRate = 1000) // 1초마다 주기적으로 호출됨.
    public void sendGyroData() {
        double yValue = getGyroYValue();
        messagingTemplate.convertAndSend("/topic/km-h", "{\"yValue\": " + yValue + "}"); // "km-h" 주제로 클라이언트에 전송?
        System.out.println("Sending Y Value: " + yValue);

    }

    protected double getGyroYValue() {
        // 자이로 센서에서 Y 값을 읽어오는 로직을 구현합니다.
        // 여기서는 예시로 랜덤 값을 반환합니다.
        return Math.floor(Math.random() * 61); // 실제 센서 값을 반환하도록 수정
    }
}
