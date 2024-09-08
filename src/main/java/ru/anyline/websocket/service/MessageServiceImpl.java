package ru.anyline.websocket.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.anyline.websocket.entity.Message;
import ru.anyline.websocket.repository.MessageRepo;

import java.util.List;

@AllArgsConstructor
@Service
public class MessageServiceImpl implements MessageService{

    private final MessageRepo messageRepo;
    @Override
    public void saveMessage(String roomId, String content) {

        Message message = new Message();
        message.setRoomId(roomId);
        message.setContent(content);
        messageRepo.save(message);

    }

    @Override
    public List<Message> getMessageHistory(String roomId) {

        return messageRepo.findByRoomId(roomId);
    }
}
