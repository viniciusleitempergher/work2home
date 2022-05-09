import { Usuario } from 'src/models/Usuario';
import { UserService } from 'src/app/services/user.service';
import { OrdemServicoResponse } from './../../../../models/OrdemServicoResponse';

import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { CategoriaService } from 'src/app/services/categoria.service';
import { Categoria } from 'src/models/Categoria';
import { environment } from 'src/environments/environment';
import { OrdemServicoService } from 'src/app/services/ordem-servico.service';

@Component({
  selector: 'app-cliente-main-screen',
  templateUrl: './cliente-main-screen.component.html',
  styleUrls: ['./cliente-main-screen.component.css'],
})
export class ClienteMainScreenComponent implements OnInit {
  categorias: Categoria[] = [];
  environment = environment;
  ordensServico: OrdemServicoResponse[] = [];
  usuario: Usuario = new Usuario;
  fotoPerfilUsuario: string = "";
  nomeUsuario : string = ""
  cbxStatus: string = '';

  categoriaId : number = 0;


  isImageVisible : boolean = false


  constructor(
    private categoriaService: CategoriaService,
    private ordemService: OrdemServicoService,
    private userService : UserService,
    private router: Router
  ) {}

  async ngOnInit(): Promise<void>{

    this.usuario = await this.userService.getUserFromAccessToken();
    this.buscarCategorias();
    this.cbxStatus = '-1';
    this.getServicosByStatus();
    this.carregarInfoUsuario()
  }

  getServicosByStatus() {
    try {
      this.ordemService
        .getAllByFilter(Number.parseInt(this.cbxStatus))
        .then((res) => {
          this.ordensServico = res;
        });
    } catch (err) {}
  }

  buscarCategorias() {
    this.categoriaService.getAll().then((res) => {
      this.categorias = res;
    });
  }

  carregarInfoUsuario(){
    this.nomeUsuario ="Bem vindo, "+this.usuario.nome;
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
    localStorage.clear();
  }
}
