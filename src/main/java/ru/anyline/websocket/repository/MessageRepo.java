package ru.anyline.websocket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.anyline.websocket.entity.Message;

import java.util.List;

public interface MessageRepo extends JpaRepository<Message, Long> {

    List<Message> findByRoomId(String roomId);
}
