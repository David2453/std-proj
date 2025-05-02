package web.example.websocket_proj.dto;

import java.time.LocalDateTime;

public class ChatMessageDTO {
    private String content;
    private String username;
    private LocalDateTime timestamp;

    // Constructor gol necesar pentru deserializare
    public ChatMessageDTO() {
    }

    public ChatMessageDTO(String content, String username) {
        this.content = content;
        this.username = username;
        this.timestamp = LocalDateTime.now();
    }

    public ChatMessageDTO(String content, String username, LocalDateTime timestamp) {
        this.content = content;
        this.username = username;
        this.timestamp = timestamp;
    }

    // Getters și setters
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}