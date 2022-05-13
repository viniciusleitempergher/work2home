import { Component, OnInit } from '@angular/core';
import { environment } from 'src/environments/environment';
import { FormControl, FormGroup } from '@angular/forms';
import { Socket } from 'ngx-socket-io';

@Component({
  selector: 'app-chat',
  templateUrl: './chat.component.html',
  styleUrls: ['./chat.component.css']
})
export class ChatComponent implements OnInit {

  constructor(private socket: Socket) { }

  chatForm = new FormGroup({
    message: new FormControl()
  })

  ngOnInit(): void {
    this.socket.connect()
    

    this.socket.emit("message", {})
  }
  
  handleSendMessage() {
    console.log("MANDOU");


    this.socket.emit("message", {})
    this.socket.ioSocket.emit("message")
  }

}
