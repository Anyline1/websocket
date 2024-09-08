package ru.anyline.websocket;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import ru.anyline.websocket.handler.WebSocketHandler;
import ru.anyline.websocket.service.MessageServiceImpl;

import static org.mockito.Mockito.*;

public class WebSocketHandlerTest {

    @Mock
    private WebSocketSession webSocketSession;

    @Mock
    private MessageServiceImpl messageService;

    @InjectMocks
    private WebSocketHandler webSocketHandler;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testHandleTextMessage() throws Exception {
        // Имитация отправки текстового сообщения
        when(webSocketSession.isOpen()).thenReturn(true);

        TextMessage message = new TextMessage("Hello, World!");
        webSocketHandler.handleTextMessage(webSocketSession, message);

        // Проверяем, что сообщение было сохранено
        verify(messageService, times(1)).saveMessage(eq("default"), eq("Hello, World!"));

        // Проверяем, что сообщение было отправлено
        verify(webSocketSession, times(1)).sendMessage(any(TextMessage.class));
    }
}

