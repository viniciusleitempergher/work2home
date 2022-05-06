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

  value: string = '';

  categoriaId : number = 0;


  isImageVisible : boolean = false

  cadastroStatusForm = new FormGroup({
    cbxStatusServico: new FormControl(null, Validators.required),
  });

  constructor(
    private categoriaService: CategoriaService,
    private ordemService: OrdemServicoService,
    private userService : UserService,
    private router: Router
  ) {}

  async ngOnInit(): Promise<void>{

    this.usuario = await this.userService.getUserFromAccessToken();
    this.buscarCategorias();
    this.value = '-1';
    this.getServicosByStatus();
    this.carregarInfoUsuario()
  }

  getServicosByStatus() {
    try {
      this.ordemService
        .getAllByFilter(Number.parseInt(this.value))
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

  setarCbx() {
    this.cadastroStatusForm.get('cbxStatusServico')?.setValue('0');
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
      this.fotoPerfilUsuario = environment.apiHostAddress + '/' + this.usuario.imagemUrl;
    }

  }
}
