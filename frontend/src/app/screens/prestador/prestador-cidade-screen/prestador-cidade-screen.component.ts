import { Component, Input, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { CidadeService } from 'src/app/services/cidade.service';
import { EnderecoApiService } from 'src/app/services/endereco-api.service';
import { PrestadorService } from 'src/app/services/prestador.service';
import { UserService } from 'src/app/services/user.service';
import { Cidade } from 'src/models/Cidade';
import { CidadePrestador } from 'src/models/CidadePrestador';
import { CustomAlerts } from 'src/models/CustomAlerts';
import { Estado } from 'src/models/Estado';
import { Prestador } from 'src/models/Prestador';
import { Usuario } from 'src/models/Usuario';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-prestador-cidade-screen',
  templateUrl: './prestador-cidade-screen.component.html',
  styleUrls: ['./prestador-cidade-screen.component.css']
})
export class PrestadorCidadeScreenComponent implements OnInit {
  user = {} as Usuario;

  estado: Estado = new Estado();
  estados: Estado[] = [];
  cidades: Cidade[] = [];
  cidadePrestador:CidadePrestador = new CidadePrestador();
  prestador:Prestador = new Prestador();
  
  estadoInvalida=false;
  cidadeInvalida=false;

  cidadeForm = new FormGroup({
    cbxEstado: new FormControl(null, Validators.required),
    cbxCidade: new FormControl(null, Validators.required)  
  }); 

  constructor(private usuarioService: UserService ,private prestadorService:PrestadorService, private serviceCidade: CidadeService, private serviceEnderecoApi:EnderecoApiService,private router: Router) { }

  async ngOnInit(): Promise<void> {
    this.user = await this.usuarioService.getUserFromAccessToken();
    console.log(this.user);
    this.prestador = await this.prestadorService.getPrestador(this.user.id);
    this.limparCombo();
    this.obterEstados();
    this.obterCidades("");
  }

  continuar(){
    this.router.navigate(['prestador-categoria']);
  }
 
  async cadastrar() {
    console.log(this.user);
    
    try {
    this.validaCbxEstado();
    this.validaCbxCidade();
  } catch (e:any) {
    CustomAlerts.primaryAlert.fire('Erro!', e.message, 'error')
  }
  if (this.cidadeForm.valid) {
    await this.serviceCidade.cadastrarCidade(this.cidadePrestador);
    CustomAlerts.primaryAlert.fire({
      position: 'center',
      icon: 'success',
      title: 'Cidade Adicionada!',
      showConfirmButton: false,
      timer: 1500
    })
    this.prestador = await this.prestadorService.getPrestador(this.user.id);
  }
}
  obterEstados = () => {
    this.serviceEnderecoApi.listarEstados()
      .subscribe(retorno => this.estados = retorno);
  }
  selecaoEstado = () => {
    this.obterCidades(this.cidadeForm.value.cbxEstado);
    this.cidadeForm.get('cbxCidade')?.setValue("");
    this.validaCbxEstado()
  }
  obterCidades = (sigla: string) => {
    return new Promise((resolve, reject) => {
      this.serviceEnderecoApi.listarCidades(sigla)
        .subscribe(retorno => {
          this.cidades = retorno;
          resolve(retorno);
        });
    })

  }
  validaCbxEstado = () => {
    if (this.cidadeForm.value.cbxEstado == '') {
      this.estadoInvalida = true;
      throw new Error("Escolha um Estado!");
    } else {
      this.cidadePrestador.estado=this.cidadeForm.value.cbxEstado;
      this.estadoInvalida = false;
    }
  }
  validaCbxCidade = () => {
    if (this.cidadeForm.value.cbxCidade == '') {
      this.cidadeInvalida = true;
      throw new Error("Escolha um Cidade");
    } else {
      this.cidadePrestador.nome=this.cidadeForm.value.cbxCidade;
      this.cidadeInvalida= false;
    }
  }
  limparCombo() {
    this.cidadeForm.reset();
    this.cidadeForm.get("cbxEstado")?.setValue("");
    this.cidadeForm.get("cbxCidade")?.setValue("");
  
  }

  async handleDeletarCidade(id:number){
    console.log(id)
    let escolha = await CustomAlerts.primaryAlert.fire({
      title: '<strong>Alerta!</strong>',
      icon: 'info',
      html:
        'Você deseja realmente excluir essa cidade!?',
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
      await this.prestadorService.deletarCidadePrestador(id);
      this.prestador = await this.prestadorService.getPrestador(this.user.id);
    }
    
  }

}
