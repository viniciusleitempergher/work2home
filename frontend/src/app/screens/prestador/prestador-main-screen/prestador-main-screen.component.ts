import { Component, OnInit } from '@angular/core';
import { PrestadorService } from 'src/app/services/prestador.service';
import { UserService } from 'src/app/services/user.service';
import { environment } from 'src/environments/environment';
import { Prestador } from 'src/models/Prestador';
import { Usuario } from 'src/models/Usuario';

@Component({
  selector: 'app-prestador-main-screen',
  templateUrl: './prestador-main-screen.component.html',
  styleUrls: ['./prestador-main-screen.component.css']
})
export class PrestadorMainScreenComponent implements OnInit {
  isImageVisible:boolean=true;
  fotoPerfilUsuario:string = '';
  user:Usuario = new Usuario();
  nomePrestador:string='';

  constructor(private usuarioService: UserService) { }

  async ngOnInit(): Promise<void> {
    this.user = await this.usuarioService.getUserFromAccessToken();
    this.carregarInfoPrestador();

  }

  carregarInfoPrestador(){
    this.nomePrestador ="Bem vindo, " +this.user.nome;
    this.carregarImagemPerfil();

  }

  carregarImagemPerfil(){
    if (this.user.imagemUrl == null) {
      this.isImageVisible = false;
    } else {
      this.isImageVisible=true;
    }
    this.fotoPerfilUsuario = environment.apiHostAddress + '/' + this.user.imagemUrl;
  }

}
