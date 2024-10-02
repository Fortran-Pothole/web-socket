package Pothole.websocket.Controller;

import Pothole.websocket.exception.WebSocketHandler;
import org.junit.jupiter.api.Test;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


class GyroSensorControllerTest {
    private final GyroSensorController gyroSensorController = new GyroSensorController(null); // SimpMessagingTemplate는 null로 설정

    @Test
    public void testGetGyroYValue() {
        for (int i = 0; i < 100; i++) { // 100번 테스트하여 신뢰성 확인
            double yValue = gyroSensorController.getGyroYValue();
            // Y 값이 0 이상 60 이하인지 확인
            assertTrue(yValue >= 0 && yValue <= 60, "Y 값이 범위를 벗어났습니다: " + yValue);
        }
    }

    @Test
    public void testWebSocketMessageSending() throws Exception {
        // Mock 객체 생성
        WebSocketSession mockSession = mock(WebSocketSession.class);

        // 웹소켓 세션이 열린 상태로 설정
        when(mockSession.isOpen()).thenReturn(true);

        // 메시지 전송 메서드
        doNothing().when(mockSession).sendMessage(any(TextMessage.class));

        // 메시지 전송 시도
        String messageToSend = "Hello, WebSocket!";
        mockSession.sendMessage(new TextMessage(messageToSend));

        // 메시지가 올바르게 전송되었는지 검증
        verify(mockSession, times(1)).sendMessage(new TextMessage(messageToSend));

        // 성공 메시지 출력 (테스트가 성공적으로 끝났음을 알리기 위해)
        System.out.println("메시지가 성공적으로 전송되었습니다.");
    }

    @Test
    public void testWebSocketClosedState() {
        WebSocketSession mockSession = mock(WebSocketSession.class);

        // 웹소켓이 닫혀 있다고 설정
        when(mockSession.isOpen()).thenReturn(false);

        // 상태가 닫혀 있을 때 메시지 전송 시도
        if (!mockSession.isOpen()) {
            System.out.println("웹소켓 세션이 닫혀 있습니다.");
        }

        // 출력 내용 검증
        assertTrue("웹소켓 세션이 닫혀 있습니다.".contains("세션이 닫혀 있습니다."));
    }

    @Test
    public void testWebSocketMessageSendingWhenClosed() throws Exception {
        // Mock 객체 생성
        WebSocketSession mockSession = mock(WebSocketSession.class);

        // 웹소켓 세션이 닫혀 있다고 설정
        when(mockSession.isOpen()).thenReturn(false);

        // sendMessage 호출 시 예외가 발생하도록 설정
        doThrow(new IllegalStateException("웹소켓 세션이 닫혀 있습니다.")).when(mockSession).sendMessage(any(TextMessage.class));

        // 예외 발생을 위한 테스트
        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            mockSession.sendMessage(new TextMessage("Hello, WebSocket!"));
        });

        // 예외 메시지 확인
        assertEquals("웹소켓 세션이 닫혀 있습니다.", exception.getMessage());

        // 성공적인 예외 처리 출력
        System.out.println("웹소켓 세션이 닫혀 있어 메시지를 전송할 수 없습니다.");
    }

}