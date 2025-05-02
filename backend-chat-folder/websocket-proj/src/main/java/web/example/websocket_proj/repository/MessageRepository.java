package web.example.websocket_proj.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import web.example.websocket_proj.model.Message;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {
    
    @Query(value = "SELECT * FROM message ORDER BY timestamp DESC LIMIT :count", nativeQuery = true)
    List<Message> findTopByOrderByTimestampDesc(@Param("count") int count);
}