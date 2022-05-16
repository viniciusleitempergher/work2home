import { Component, OnInit } from '@angular/core';
import { environment } from 'src/environments/environment';
import { FormControl, FormGroup } from '@angular/forms';
import { Socket } from 'ngx-socket-io';
import { Usuario } from 'src/models/Usuario';
import { UserService } from 'src/app/services/user.service';
import { ActivatedRoute } from '@angular/router';
import { ClienteService } from 'src/app/services/cliente.service';
import { PrestadorService } from 'src/app/services/prestador.service';


@Component({
  selector: 'app-chat',
  templateUrl: './chat.component.html',
  styleUrls: ['./chat.component.css']
})
export class ChatComponent implements OnInit {

  user = {} as Usuario;
  userReceiver: Usuario = {} as Usuario;
  userReceiverId: number = +this.route.snapshot.params['usuarioId'];
  profileImgUrl: string = "";
  messages: any[] = [];

  hasImage: boolean = true;

  constructor(private socket: Socket, private usuarioService: UserService, private route: ActivatedRoute, private clienteService: ClienteService, private prestadorService: PrestadorService) { }

  chatForm = new FormGroup({
    message: new FormControl()
  })

  async ngOnInit(): Promise<void> {
    this.user = await this.usuarioService.getUserFromAccessToken();
    let role = (await this.usuarioService.getUserRole(this.userReceiverId)).role;

    if (role == "CLIENTE") {
      this.userReceiver = (await this.clienteService.getCliente(this.userReceiverId)) as any;
    } else if (role == "PRESTADOR") {
      this.userReceiver = (await this.prestadorService.getPrestador(this.userReceiverId)) as any;
    }

    if (!this.userReceiver.imagemUrl) {
      this.hasImage = false;
    } else {
      if ((this.userReceiver.imagemUrl).includes("https")) {
        this.profileImgUrl = this.userReceiver.imagemUrl ;
      } else {
        this.profileImgUrl = environment.apiHostAddress + '/' + this.userReceiver.imagemUrl;
      }
    }

    this.socket.on("receiveMsg", (msg: any) => {
      this.messages.push(msg);
    })
  }
  
  handleSendMessage() {
    this.socket.emit("message", {
      userFrom: this.user.id,
      userTo: this.userReceiverId,
      text: this.chatForm.value.message
    })
  }

}
