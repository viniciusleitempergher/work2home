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
import Swal from 'sweetalert2';
import { CustomAlerts } from 'src/models/CustomAlerts';

@Component({
  selector: 'app-prestador-categoria-screen',
  templateUrl: './prestador-categoria-screen.component.html',
  styleUrls: ['./prestador-categoria-screen.component.css']
})
export class PrestadorCategoriaScreenComponent implements OnInit {
  user = {} as Usuario;
  prestador: Prestador = new Prestador();
  categoriaInvalida: boolean = false;
  categorias: Categoria[] = [];
  nomeCategoria:string='';

  categoriaAtuaForm = new FormGroup({
    cbxCategoria: new FormControl(null, Validators.required)
  });

  constructor(private categoriaService: CategoriaService, private usuarioService: UserService, private prestadorService: PrestadorService, private serviceEnderecoApi: EnderecoApiService, private router: Router) { }

  async ngOnInit(): Promise<void> {
    this.categorias = await this.categoriaService.getAll();
    this.user = await this.usuarioService.getUserFromAccessToken();
    this.prestador = await this.prestadorService.getPrestador(this.user.id);
    this.limparCombo();
  }

  continuar() {
    this.router.navigate(['prestador']);
  }


  async cadastrar() {
    if (!this.verificaCategoriaCadastrada()) {
      try {
        this.validaCbxCategoria();

      } catch (e: any) {
        CustomAlerts.primaryAlert.fire('Erro!', e.message, 'error')
      }
      if (this.categoriaAtuaForm.valid) {
        await this.categoriaService.cadastrarCategoria(this.categoriaAtuaForm.value.cbxCategoria);
        CustomAlerts.primaryAlert.fire({
          position: 'center',
          icon: 'success',
          title: 'Categoria Cadastrada!',
          showConfirmButton: false,
          timer: 1500
        })
        this.prestador = await this.prestadorService.getPrestador(this.user.id);
      }
    } else {
      CustomAlerts.primaryAlert.fire('Erro!', 'Categoria '+this.nomeCategoria+' já cadastrada', 'error')
    }
  }
  async handleDeletarCategoria(id: number) {
    console.log(id)
    let escolha = await CustomAlerts.primaryAlert.fire({
      title: '<strong>Alerta!</strong>',
      icon: 'info',
      html:
        'Você deseja realmente excluir essa categoria!?',
      showCloseButton: true,
      showCancelButton: true,
      focusConfirm: false,
      confirmButtonText:
        'Sim!',
      confirmButtonAriaLabel: 'Sim!',
      cancelButtonText:
        'Não',
      cancelButtonAriaLabel: 'Não!'
    });

    if (escolha.isConfirmed) {
      console.log(id)
      await this.categoriaService.deletarCategoria(id);
      this.prestador = await this.prestadorService.getPrestador(this.user.id);
    }

  }


  limparCombo() {
    this.categoriaAtuaForm.reset();
    this.categoriaAtuaForm.get("cbxCategoria")?.setValue("");

  }
  validaCbxCategoria() {
    if (this.categoriaAtuaForm.value.cbxCategoria == '') {
      this.categoriaInvalida = true;
      throw new Error("Escolha uma Categoria!");
    } else {
      this.categoriaInvalida = false;
    }
  }
  verificaCategoriaCadastrada = () => {
    for(let ctg of this.prestador.categorias){
      if(ctg.id == this.categoriaAtuaForm.value.cbxCategoria){
        this.nomeCategoria=ctg.nome; 
        return true;
      }
    }
    return false;
  }
}
