package ru.anyline.websocket;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.TextMessage;
import ru.anyline.websocket.entity.Message;
import ru.anyline.websocket.repository.MessageRepo;
import ru.anyline.websocket.service.MessageService;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class MessageServiceTest {

    @Mock
    private MessageRepo messageRepository;

    @InjectMocks
    private MessageService messageService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSaveMessage() {
        String roomId = "room1";
        String content = "Hello, Room 1";

        messageService.saveMessage(roomId, content);

        verify(messageRepository, times(1)).save(any(Message.class));
    }

    @Test
    public void testGetMessageHistory() {
        String roomId = "room1";

        messageService.getMessageHistory(roomId);

        verify(messageRepository, times(1)).findByRoomId(roomId);
    }

//    @Test
//    public void testHandleBinaryMessage() throws Exception {
//        byte[] payload = "Test Binary Data".getBytes();
//        BinaryMessage binaryMessage = new BinaryMessage(payload);
//
//        when(webSocketSession.isOpen()).thenReturn(true);
//
//        webSocketHandler.handleBinaryMessage(webSocketSession, binaryMessage);
//
//        Path path = Paths.get("uploads/file_" + webSocketSession.getId());
//        assertTrue(Files.exists(path));
//
//        verify(webSocketSession, times(1)).sendMessage(any(TextMessage.class));
//    }
}

