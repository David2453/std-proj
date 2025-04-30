package web.example.websocket_proj.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import web.example.websocket_proj.model.Message;

public interface MessageRepository extends JpaRepository<Message, Long> {
}
