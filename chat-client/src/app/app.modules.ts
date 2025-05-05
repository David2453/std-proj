import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';

import { AppComponent } from './app.component';
import { ChatComponent } from './chat/chat.component';

@NgModule({
  declarations: [], // Remove components from declarations since they're standalone
  imports: [
    BrowserModule,
    FormsModule,
    AppComponent, // Import the standalone components instead
    ChatComponent
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }