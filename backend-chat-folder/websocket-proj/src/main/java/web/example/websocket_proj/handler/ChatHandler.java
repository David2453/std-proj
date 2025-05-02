package web.example.websocket_proj.handler;

import web.example.websocket_proj.model.Message;
import web.example.websocket_proj.service.ChatService;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class ChatHandler extends TextWebSocketHandler {
    private static final Logger logger = Logger.getLogger(ChatHandler.class.getName());
    private final List<WebSocketSession> sessions = new CopyOnWriteArrayList<>();
    
    @Autowired
    private ChatService chatService;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws IOException {
        sessions.add(session);
        logger.info("Sesiune nouă conectată: " + session.getId());
        
        // Trimite ultimele 10 mesaje către utilizatorul nou conectat
        List<Message> recentMessages = chatService.getRecentMessages(10);
        for (Message message : recentMessages) {
            String jsonMessage = chatService.convertToJson(message);
            session.sendMessage(new TextMessage(jsonMessage));
        }
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws IOException {
        String payload = message.getPayload();
        logger.info("Mesaj primit: " + payload);
        
        try {
            // Salvăm mesajul în baza de date
            chatService.saveMessage(payload);
            
            // Trimitem mesajul către toți clienții conectați
            for (WebSocketSession s : sessions) {
                if (s.isOpen()) {
                    s.sendMessage(message);
                }
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Eroare la procesarea mesajului", e);
            session.sendMessage(new TextMessage("{\"error\": \"" + e.getMessage() + "\"}"));
        }
    }
    
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        sessions.remove(session);
        logger.info("Sesiune închisă: " + session.getId() + " cu status: " + status);
    }
    
    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) {
        logger.log(Level.SEVERE, "Eroare de transport pentru sesiunea: " + session.getId(), exception);
    }
}