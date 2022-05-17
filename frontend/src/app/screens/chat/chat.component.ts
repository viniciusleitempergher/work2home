import { Component, Input, OnInit } from '@angular/core';
import { environment } from 'src/environments/environment';
import { FormControl, FormGroup } from '@angular/forms';
import { Socket } from 'ngx-socket-io';
import { Usuario } from 'src/models/Usuario';
import { UserService } from 'src/app/services/user.service';
import { ActivatedRoute, Router } from '@angular/router';
import { ClienteService } from 'src/app/services/cliente.service';
import { PrestadorService } from 'src/app/services/prestador.service';
import { MessageDto } from 'src/models/MessageDto';
import { MessageService } from 'src/app/services/message.service';
import { DatePipe, Location } from '@angular/common';


@Component({
  selector: 'app-chat',
  templateUrl: './chat.component.html',
  styleUrls: ['./chat.component.css'],
  providers: [DatePipe] 
})
export class ChatComponent implements OnInit {
  user = {} as Usuario;
  userReceiver: Usuario = {} as Usuario;
  userReceiverId: number = +this.route.snapshot.params['usuarioId'];
  profileImgUrl: string = "";
  messages: MessageDto[] = [] as MessageDto[];

  hasImage: boolean = true;

  constructor(private location: Location, private socket: Socket, private usuarioService: UserService, private messageService: MessageService, private route: ActivatedRoute, private clienteService: ClienteService, private prestadorService: PrestadorService, private datePipe: DatePipe, private router: Router) { }

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

    this.socket.on("receiveMsg", (msg: MessageDto) => {
      if (!msg.sentDate) {
        msg.sentDate = new Date();
      }
      this.messages.push(msg);
      this.scrollMessages();
    })

    this.messages = [];
    this.messages = await this.messageService.getMyMessages();
  }
  
  async handleSendMessage() {
    let msgDto = await this.messageService.sendMessage({
      userFrom: this.user.id,
      userTo: this.userReceiverId,
      text: this.chatForm.value.message
    } as MessageDto)

    this.socket.emit("message", msgDto)
  }

  scrollMessages() {
    let messagesDiv = document.querySelector(".messages") as Element;

    setTimeout(() => {
      messagesDiv.scrollTo(0, messagesDiv.scrollHeight + 1500);
    }, 300);
  }
  voltarPagina(){
    let ordemId = JSON.parse(localStorage.getItem("ordemServicoId") as string);
    this.router.navigate([`ordem-servico/${ordemId}`])
  }
}
