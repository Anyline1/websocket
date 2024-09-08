package ru.anyline.websocket;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.anyline.websocket.entity.Message;
import ru.anyline.websocket.repository.MessageRepo;
import ru.anyline.websocket.service.MessageService;

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
}

