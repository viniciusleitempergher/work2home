import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { EnderecoApiService } from 'src/app/services/endereco-api.service';
import { PrestadorService } from 'src/app/services/prestador.service';
import { UserService } from 'src/app/services/user.service';
import { Prestador } from 'src/models/Prestador';
import { Usuario } from 'src/models/Usuario';
import { Categoria } from 'src/models/Categoria';
import { CategoriaService } from 'src/app/services/categoria.service';

@Component({
  selector: 'app-prestador-categoria-screen',
  templateUrl: './prestador-categoria-screen.component.html',
  styleUrls: ['./prestador-categoria-screen.component.css']
})
export class PrestadorCategoriaScreenComponent implements OnInit {
  user = {} as Usuario;
  prestador:Prestador = new Prestador();
  categoriaInvalida:boolean=false;
  categorias:Categoria[] =[];

  categoriaAtuaForm = new FormGroup({
    cbxCategoria: new FormControl(null, Validators.required)    
  }); 

  constructor(private categoriaService : CategoriaService ,private usuarioService: UserService ,private prestadorService:PrestadorService,private serviceEnderecoApi:EnderecoApiService,private router: Router) { }

  async ngOnInit(): Promise<void> {
    this.categorias = await this.categoriaService.getAll();
    this.user = await this.usuarioService.getUserFromAccessToken();
    this.prestador = await this.prestadorService.getPrestador(this.user.id);
  }

  continuar(){}
  cadastrar(){}
  handleDeletarCategoria(id:number){}

}
