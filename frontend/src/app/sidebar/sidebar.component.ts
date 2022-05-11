import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { environment } from 'src/environments/environment';
import { Usuario } from 'src/models/Usuario';
import { UserService } from '../services/user.service';

@Component({
  selector: 'app-sidebar',
  templateUrl: './sidebar.component.html',
  styleUrls: ['./sidebar.component.css']
})
export class SidebarComponent implements OnInit {
  usuario: Usuario = new Usuario;
  nomeUsuario : string = "";
  fotoPerfilUsuario: string = "";
  isImageVisible : boolean = false

  constructor(private userService : UserService, private router: Router) { }

  async ngOnInit(): Promise<void> {
    this.usuario = await this.userService.getUserFromAccessToken();
    this.carregarInfoUsuario()
  }

  carregarInfoUsuario(){
    this.nomeUsuario ="Bem vindo, " + this.usuario.nome;
    this.carregarImagemPerfil();
  }
  
  carregarImagemPerfil(){
    if (this.usuario.imagemUrl == null) {
      this.isImageVisible = false;
    } else {
      this.isImageVisible=true;

      if ((this.usuario.imagemUrl as string).includes("https")) {
        this.fotoPerfilUsuario = this.usuario.imagemUrl;
      } else {
        this.fotoPerfilUsuario = environment.apiHostAddress + '/' + this.usuario.imagemUrl;
      }
    }
  }

  logOut(){
    localStorage.removeItem("accessToken");
    localStorage.removeItem("refreshToken");
    this.router.navigate(["/"]);
    window.location.href = "/login";
  }
}
