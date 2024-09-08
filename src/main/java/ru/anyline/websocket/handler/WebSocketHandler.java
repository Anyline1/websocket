package ru.anyline.websocket.handler;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import ru.anyline.websocket.entity.Message;
import ru.anyline.websocket.service.MessageServiceImpl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
public class WebSocketHandler extends TextWebSocketHandler {

    private MessageServiceImpl messageService;

    private static Set<WebSocketSession> sessions = Collections.synchronizedSet(new HashSet<>());

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessions.add(session);
        System.out.println("New connection: " + session.getId());
        List<Message> history = messageService.getMessageHistory("default");
        for (Message msg : history) {
            session.sendMessage(new TextMessage("History: " + msg.getContent()));
        }
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        System.out.println("Received: " + message.getPayload());

        String payload = message.getPayload();
        String roomId = "default";
        messageService.saveMessage(roomId, payload);

        for (WebSocketSession webSocketSession : sessions) {
            if (webSocketSession.isOpen()) {
                webSocketSession.sendMessage(new TextMessage(message.getPayload()));
            }
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        sessions.remove(session);
        System.out.println("Disconnected: " + session.getId());
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        System.err.println("Error in session " + session.getId() + ": " + exception.getMessage());
        if (session.isOpen()) {
            session.close(CloseStatus.SERVER_ERROR);
        }
    }

    @Override
    protected void handleBinaryMessage(WebSocketSession session, BinaryMessage message) {
        byte[] payload = message.getPayload().array();
        System.out.println("Received binary message of size: " + payload.length);

        Path path = Paths.get("uploads/file_" + session.getId());
        try {
            Files.write(path, payload);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        for (WebSocketSession webSocketSession : sessions) {
            if (webSocketSession.isOpen()) {
                try {
                    webSocketSession.sendMessage(new TextMessage("Binary message received from session " + session.getId()));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }


}

