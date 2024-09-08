package ru.anyline.websocket.service;

import ru.anyline.websocket.entity.Message;

import java.util.List;

public class MessageServiceImpl implements MessageService{
    @Override
    public void saveMessage(String roomId, String content) {
        
    }

    @Override
    public List<Message> getMessageHistory(String roomId) {
        return null;
    }
}
