package web.example.websocket_proj.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.example.websocket_proj.model.Message;
import web.example.websocket_proj.repository.MessageRepository;

@Service
public class ChatService {

    @Autowired
    private MessageRepository messageRepository;

    public void saveMessage(String content) {
        Message message = new Message(content);
        messageRepository.save(message);
    }
}
