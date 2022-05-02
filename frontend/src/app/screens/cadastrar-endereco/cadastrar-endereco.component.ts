import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { EnderecoApiService } from 'src/app/services/endereco-api.service';
import { EnderecoService } from 'src/app/services/endereco.service';
import { Cidade } from 'src/models/Cidade';
import { Endereco } from 'src/models/Endereco';
import { Estado } from 'src/models/Estado';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-cadastrar-endereco',
  templateUrl: './cadastrar-endereco.component.html',
  styleUrls: ['./cadastrar-endereco.component.css']
})
export class CadastrarEnderecoComponent implements OnInit {

  estado: Estado = new Estado();
  estados: Estado[] = [];
  cidades: Cidade[] = [];
  endereco:Endereco= new Endereco();

  bairroInvalida=false;
  estadoInvalida=false;
  cidadeInvalida=false;
  ruaInvalida=false;
  numeroInvalida=false;
  complementoInvalida=false;

  
  cadastroEnderecoForm = new FormGroup({
    cbxEstado: new FormControl(null, Validators.required),
    cbxCidade: new FormControl(null, Validators.required),
    bairro:new FormControl(null,Validators.required),
    rua:new FormControl(null,Validators.required),
    numero:new FormControl(null,[Validators.required,Validators.pattern(/^-?(0|[1-9]\d*)?$/)]),
    complemento:new FormControl(null,Validators.required)   
  });


  constructor(private serviceEnderecoApi:EnderecoApiService, private enderecoService:EnderecoService, private router: Router) { }

  ngOnInit(): void {
    this.limparCombo();
    this.obterEstados();
    this.obterCidades("");
  }
  cancelar(){
    this.router.navigate(['login']);
  }
  async cadastrar() {
    try {
    this.validaCbxEstado();
    this.validaCbxCidade();
    this.validaBairro();
    this.validaRua();
    this.validaNumero();
    this.validaComplemento();
    console.log(this.endereco);
    console.log(this.cadastroEnderecoForm.value.cbxEstado)
    console.log(this.cadastroEnderecoForm.value.cbxCidade)
  } catch (e:any) {
    Swal.fire('Erro!', e.message, 'error')
  }
  if (this.cadastroEnderecoForm.valid) {
    await this.enderecoService.cadastrarEndereco(this.endereco);
    Swal.fire({
      position: 'center',
      icon: 'success',
      title: 'Endereço Cadastrado!',
      showConfirmButton: false,
      timer: 1500
    })
    this.router.navigate(['cliente']);
  }
}
  obterEstados = () => {
    this.serviceEnderecoApi.listarEstados()
      .subscribe(retorno => this.estados = retorno);
  }
  selecaoEstado = () => {
    this.obterCidades(this.cadastroEnderecoForm.value.cbxEstado);
    this.cadastroEnderecoForm.get('cbxCidade')?.setValue("");
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
    if (this.cadastroEnderecoForm.value.cbxEstado == '') {
      this.estadoInvalida = true;
      throw new Error("Escolha um Estado!");
    } else {
      this.endereco.estado=this.cadastroEnderecoForm.value.cbxEstado;
      this.estadoInvalida = false;
    }
  }
  validaCbxCidade = () => {
    if (this.cadastroEnderecoForm.value.cbxCidade == '') {
      this.cidadeInvalida = true;
      throw new Error("Escolha um Cidade");
    } else {
      this.endereco.cidade=this.cadastroEnderecoForm.value.cbxCidade;
      this.cidadeInvalida= false;
    }
  }
  validaBairro(){
    if(!this.cadastroEnderecoForm.get('bairro')?.valid){
      this.bairroInvalida = true;
      throw new Error("Bairro inválido!!!");
    }else{
      this.endereco.bairro=this.cadastroEnderecoForm.value.bairro;
      this.bairroInvalida=false;
      
    }
  }
  validaRua(){
    if(!this.cadastroEnderecoForm.get('rua')?.valid){
      this.ruaInvalida=true;
      throw new Error("Rua inválida!!!");
    }else{
      this.endereco.endereco=this.cadastroEnderecoForm.value.rua;
      this.ruaInvalida=false;
    }
  }
  validaNumero(){
    if(!this.cadastroEnderecoForm.get('numero')?.valid){
      this.numeroInvalida=true;
      throw new Error("Numero inválido");
    }else {
      this.endereco.numero= this.cadastroEnderecoForm.value.numero;
      this.numeroInvalida=false;
   }
  }
  validaComplemento(){
    if(!this.cadastroEnderecoForm.get('complemento')?.valid){
      this.complementoInvalida=true;
      throw new Error("Complemento inválido");
    }else{
      this.endereco.complemento=this.cadastroEnderecoForm.value.complemento;
      this.complementoInvalida=false;
    }
  }


  limparCombo() {
    this.cadastroEnderecoForm.reset();
    this.cadastroEnderecoForm.get("cbxEstado")?.setValue("");
    this.cadastroEnderecoForm.get("cbxCidade")?.setValue("");
  
  }
  
}
