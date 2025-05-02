package web.example.websocket_proj.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.example.websocket_proj.dto.ChatMessageDTO;
import web.example.websocket_proj.model.Message;
import web.example.websocket_proj.repository.MessageRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ChatService {

    @Autowired
    private MessageRepository messageRepository;
    
    private final ObjectMapper objectMapper;
    
    public ChatService() {
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule());
    }

    public void saveMessage(String jsonContent) throws IOException {
        ChatMessageDTO chatMessageDTO = objectMapper.readValue(jsonContent, ChatMessageDTO.class);
        Message message = new Message(chatMessageDTO.getContent(), chatMessageDTO.getUsername());
        messageRepository.save(message);
    }
    
    public String convertToJson(Message message) throws IOException {
        ChatMessageDTO dto = new ChatMessageDTO(
            message.getContent(),
            message.getUsername(),
            message.getTimestamp()
        );
        return objectMapper.writeValueAsString(dto);
    }
    
    public List<Message> getRecentMessages(int count) {
        // Această metodă va fi folosită pentru a încărca mesajele recente când un utilizator se conectează
        return messageRepository.findTopByOrderByTimestampDesc(count);
    }
}