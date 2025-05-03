import { Component } from '@angular/core';

@Component({
  selector: 'app-root',
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