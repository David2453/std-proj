import { Component, OnInit, OnDestroy, Inject, PLATFORM_ID } from '@angular/core';
import { isPlatformBrowser } from '@angular/common';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

interface ChatMessage {
  content: string;
  username: string;
  timestamp: string;
}

@Component({
  selector: 'app-chat',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './chat.component.html',
  styleUrls: ['./chat.component.css']
})
export class ChatComponent implements OnInit, OnDestroy {
  messages: ChatMessage[] = [];
  newMessage: string = '';
  username: string = 'Anonymous';
  websocket: WebSocket | null = null;
  connectionStatus: string = 'Disconnected';

  constructor(@Inject(PLATFORM_ID) private platformId: Object) { }

  ngOnInit(): void {
    if (isPlatformBrowser(this.platformId)) {
      this.connectWebSocket();
    }
  }

  ngOnDestroy(): void {
    this.disconnectWebSocket();
  }

  connectWebSocket(): void {
    // Use WebSocket URL that matches your Java backend
    this.websocket = new WebSocket('ws://localhost:30088/chat');

    this.websocket.onopen = () => {
      this.connectionStatus = 'Connected';
      console.log('WebSocket connection established');
    };

    this.websocket.onmessage = (event) => {
      try {
        const message = JSON.parse(event.data);
        this.messages.push(message);
        // Auto-scroll to bottom
        if (isPlatformBrowser(this.platformId)) {
          setTimeout(() => {
            const chatContainer = document.querySelector('.chat-messages');
            if (chatContainer) {
              chatContainer.scrollTop = chatContainer.scrollHeight;
            }
          }, 0);
        }
      } catch (error) {
        console.error('Error parsing message:', error);
      }
    };

    this.websocket.onclose = () => {
      this.connectionStatus = 'Disconnected';
      console.log('WebSocket connection closed');
      // Try to reconnect after 3 seconds
      setTimeout(() => {
        if (isPlatformBrowser(this.platformId)) {
          this.connectWebSocket();
        }
      }, 3000);
    };

    this.websocket.onerror = (error) => {
      this.connectionStatus = 'Error';
      console.error('WebSocket error:', error);
    };
  }

  disconnectWebSocket(): void {
    if (this.websocket) {
      this.websocket.close();
      this.websocket = null;
    }
  }

  sendMessage(): void {
    if (!this.newMessage.trim() || !this.websocket) {
      return;
    }

    const message: ChatMessage = {
      content: this.newMessage.trim(),
      username: this.username.trim() || 'Anonymous',
      timestamp: new Date().toISOString()
    };

    this.websocket.send(JSON.stringify(message));
    this.newMessage = '';
  }
}