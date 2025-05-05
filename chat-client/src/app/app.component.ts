// src/app/app.component.ts
import { Component } from '@angular/core';
import { ChatComponent } from './chat/chat.component';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [ChatComponent],
  template: `
    <div class="main-container">
      <app-chat></app-chat>
    </div>
  `,
  styles: [`
    .main-container {
      width: 100%;
      max-width: 600px;
      height: 80vh;
      margin: 20px auto;
      padding: 0;
    }
  `]
})
export class AppComponent {
  title = 'chat-client';
}