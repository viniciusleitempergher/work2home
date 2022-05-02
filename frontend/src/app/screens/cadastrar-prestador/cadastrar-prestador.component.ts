import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { DatePipe } from '@angular/common';
import Swal from 'sweetalert2';
import { Prestador } from 'src/models/Prestador';
import { PrestadorService } from 'src/app/services/prestador.service';

@Component({
  selector: 'app-cadastrar-prestador',
  templateUrl: './cadastrar-prestador.component.html',
  styleUrls: ['./cadastrar-prestador.component.css'],
  providers: [DatePipe] //DatePipe como provider
})
export class CadastrarPrestadorComponent implements OnInit {

  prestador:Prestador= new Prestador();

  cadastroPrestadorForm= new FormGroup({
    email: new FormControl(null, [Validators.required, Validators.pattern("^[a-z0-9._-]+@[a-z0-9.-]+\\.[a-z]{2,4}$")]),
    nome: new FormControl(null, [Validators.required, Validators.minLength(1), Validators.pattern('^[a-zA-Zà-úÀ-Ú_ ]*$')]),
    senha: new FormControl(null, [Validators.required, Validators.minLength(8), Validators.pattern('^[a-zA-Zà-úÀ-Ú_ ]*$')]),
    repetirSenha: new FormControl(null, [Validators.required, Validators.minLength(8), Validators.pattern('^[a-zA-Zà-úÀ-Ú_ ]*$')]),
    dataNascimento: new FormControl(null, [Validators.required]),
    cnpj: new FormControl(null,[Validators.required]),
    telefone: new FormControl(null,[Validators.required]),
    nomeEmpresa: new FormControl(null,[Validators.required])

  });

  

  constructor(private prestadorService: PrestadorService,private router: Router,private datePipe: DatePipe) { }

  ngOnInit(): void {
    this.cadastroPrestadorForm.reset();
  }

  cancelar(){
    this.router.navigate(['login']);
  }
  async cadastrar(){
    this.validaEmail();
    this.validaNome();
    this.validaSenha();
    this.validaDataNascimento();
    this.validaCnpj();
    this.validaTelefone();
    this.validaNomeEmpresa();
    if(this.cadastroPrestadorForm.valid){
      await this.prestadorService.cadastrarPrestador(this.prestador);
      Swal.fire({
        position: 'center',
        icon: 'success',
        title: 'Prestador Cadastrado!!!',
        showConfirmButton: false,
        timer: 1500
      })
      //this.router.navigate(['cadastrar-endereco']);
    }
    else{
      Swal.fire({
        position: 'top-end',
        icon: 'error',
        title: 'Erro ao Cadastrar!!!',
        showConfirmButton: false,
        timer: 1500
      })
    }
  }
  validaEmail() {
    if (!this.cadastroPrestadorForm.get('email')?.valid) {
      Swal.fire('Erro!!!', 'Email Invalido', 'error')
    }else{
      this.prestador.usuarioDto.email = this.cadastroPrestadorForm.value.email;
    }
  }
  validaNome() {
    if (!this.cadastroPrestadorForm.get('nome')?.valid) {
      Swal.fire('Erro!!!', 'Nome Invalido', 'error')
    }else{
      this.prestador.usuarioDto.nome = this.cadastroPrestadorForm.value.nome;
    }
  }
  validaSenha() {
    if (!this.cadastroPrestadorForm.get('senha')?.valid) {
      Swal.fire('Erro!!!', 'Senha Invalido', 'error')
    }else if(this.cadastroPrestadorForm.get('senha')?.value!=this.cadastroPrestadorForm.get('repetirSenha')?.value){
      Swal.fire('Erro!!!', 'Senhas não conferem', 'error')
    }else{
      this.prestador.usuarioDto.senha = this.cadastroPrestadorForm.value.senha;
    }

  }
  validaCnpj() {
    if (!this.cadastroPrestadorForm.get('cnpj')?.valid) {
      Swal.fire('Erro!!!', 'Cnpj Invalido', 'error')
    }else{
      this.prestador.cnpj = this.cadastroPrestadorForm.value.cnpj;
    }
  }
  validaTelefone() {
    if (!this.cadastroPrestadorForm.get('telefone')?.valid) {
      Swal.fire('Erro!!!', 'Telefone Invalido', 'error')
    }else{
      this.prestador.usuarioDto.telefone = this.cadastroPrestadorForm.value.telefone;
    }
  }
  validaDataNascimento() {
    if(this.datePipe.transform(this.cadastroPrestadorForm.get("dataNascimento")?.value, 'dd/MM/yyyy')==null){
      Swal.fire('Erro!!!', 'Informe a data', 'error')
    }else{
      this.prestador.usuarioDto.dtNascimento = this.datePipe.transform(this.cadastroPrestadorForm.value.dataNascimento, 'dd/MM/yyyy') as string;
    }
  }
  validaNomeEmpresa() {
    if (!this.cadastroPrestadorForm.get('nomeEmpresa')?.valid) {
      Swal.fire('Erro!!!', 'Nome da Empresa Invalido', 'error')
    }else{
      this.prestador.nomeFantasia = this.cadastroPrestadorForm.value.nomeEmpresa;
    }
  }

}
