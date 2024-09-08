package ru.anyline.websocket.service;

import ru.anyline.websocket.entity.Message;

import java.util.List;

public interface MessageService {

    public void saveMessage(String roomId, String content);

    public List<Message> getMessageHistory(String roomId);
}
