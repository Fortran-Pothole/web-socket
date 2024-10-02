package Pothole.websocket.Controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketController {
    @MessageMapping("/sendKmH") // 클라이언트가 km-h에 메시지를 보내면 해당 메서드가 호출됨.
    // 아마 우리 프로젝트에서는 될 일이 없을 듯한데...
    @SendTo("/km-h") // 메서드 반환값을 클라이언트에게 전송
    public String sendGyroData(String message) {
        return message;
    }
}
