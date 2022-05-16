import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { DatePipe } from '@angular/common';
import Swal from 'sweetalert2';
import { PrestadorService } from 'src/app/services/prestador.service';

import { UserService } from 'src/app/services/user.service';
import { Usuario } from 'src/models/Usuario';
import { LoginResponse } from '../../auth/login-screen/login-screen.component';
import { CustomAlerts } from 'src/models/CustomAlerts';

export type PrestadorCadastro = {
  usuarioDto: Usuario;
  cnpj: string;
  nomeFantasia: string;
}

@Component({
  selector: 'app-cadastrar-prestador',
  templateUrl: './cadastrar-prestador.component.html',
  styleUrls: ['./cadastrar-prestador.component.css'],
  providers: [DatePipe] //DatePipe como provider
})
export class CadastrarPrestadorComponent implements OnInit {

  prestador: PrestadorCadastro = {usuarioDto: new Usuario()} as PrestadorCadastro;

  emailInvalido = false;
  nomeInvalido = false;
  senhaInvalida = false;
  cnpjInvalido = false;
  telefoneInvalido = false;
  dataNascimentoInvalida = false;
  nomeEmpresaInvalido = false;

  cadastroPrestadorForm = new FormGroup({
    email: new FormControl(null, [Validators.required, Validators.pattern("^[a-z0-9._-]+@[a-z0-9.-]+\\.[a-z]{2,4}$")]),
    nome: new FormControl(null, [Validators.required, Validators.minLength(1), Validators.pattern('^[a-zA-Zà-úÀ-Ú_ ]*$')]),
    senha: new FormControl(null, [Validators.required, Validators.minLength(8)]),
    repetirSenha: new FormControl(null, [Validators.required, Validators.minLength(8)]),
    dataNascimento: new FormControl(null, [Validators.required]),
    cnpj: new FormControl(null, [Validators.required]),
    telefone: new FormControl(null, [Validators.required]),
    nomeEmpresa: new FormControl(null, [Validators.required])
  });

  constructor(private usuarioService: UserService,private prestadorService: PrestadorService, private router: Router, private datePipe: DatePipe) { }

  ngOnInit(): void {
    this.cadastroPrestadorForm.reset();
  }

  cancelar() {
    this.router.navigate(['login']);
  }

  async cadastrar() {
    try {
      this.validaEmail();
      this.validaNome();
      this.validaSenha();
      this.validaDataNascimento();
      this.validaCnpj();
      this.validaTelefone();
      this.validaNomeEmpresa();
    } catch (e:any) {
      CustomAlerts.primaryAlert.fire('Erro!', e.message, 'error')
    }

    if (this.cadastroPrestadorForm.valid) {
      await this.prestadorService.cadastrarPrestador(this.prestador);
      await this.logar();
      CustomAlerts.primaryAlert.fire({
        position: 'center',
        icon: 'success',
        title: 'Prestador Cadastrado!',
        showConfirmButton: false,
        timer: 1500
      })
     this.router.navigate(['cidade']);
    }
  }
  async logar(){
    return new Promise<void>(async resolve => {
      let response:LoginResponse = await this.usuarioService.login(this.prestador.usuarioDto.email, this.prestador.usuarioDto.senha) as LoginResponse;
      localStorage.setItem('accessToken', JSON.stringify(response.accessToken));
      localStorage.setItem('refreshToken', JSON.stringify(response.refreshToken));
      resolve()
    })
  }
  validaEmail() {
    if (!this.cadastroPrestadorForm.get('email')?.valid) {
      this.emailInvalido = true;
      throw new Error("Email inválido!");
    } else {
      this.prestador.usuarioDto.email = this.cadastroPrestadorForm.value.email;
      this.emailInvalido = false;
    }
  }
  validaNome() {
    if (!this.cadastroPrestadorForm.get('nome')?.valid) {
      this.nomeInvalido = true;
      throw new Error("Nome inválido!");
    } else {
      this.prestador.usuarioDto.nome = this.cadastroPrestadorForm.value.nome;
      this.nomeInvalido = false;
    }
  }
  validaSenha() {
    if (!this.cadastroPrestadorForm.get('senha')?.valid) {
      this.senhaInvalida = true;
      throw new Error("Senha deve possuir 8 caractéres!");
    } else if (this.cadastroPrestadorForm.get('senha')?.value != this.cadastroPrestadorForm.get('repetirSenha')?.value) {
      this.senhaInvalida = true;
      throw new Error("Senhas diferentes!");
    } else {
      this.prestador.usuarioDto.senha = this.cadastroPrestadorForm.value.senha;
      this.senhaInvalida = false;
    }
  }
  validaCnpj() {
    if (!this.cadastroPrestadorForm.get('cnpj')?.valid) {
      this.cnpjInvalido = true;
      throw new Error("CNPJ inválido!");
    } else {
      this.prestador.cnpj = this.cadastroPrestadorForm.value.cnpj;
      this.cnpjInvalido = false;
    }
  }
  validaTelefone() {
    if (!this.cadastroPrestadorForm.get('telefone')?.valid) {
      this.telefoneInvalido = true;
      throw new Error("Telefone inválido!");
    } else {
      this.prestador.usuarioDto.telefone = this.cadastroPrestadorForm.value.telefone;
      this.telefoneInvalido = false;
    }
  }
  validaDataNascimento() {
    if (this.datePipe.transform(this.cadastroPrestadorForm.get("dataNascimento")?.value, 'dd/MM/yyyy') == null) {
      this.dataNascimentoInvalida = true;
      throw new Error("Data de nascimento inválida!");
    } else {
      this.prestador.usuarioDto.dtNascimento = this.datePipe.transform(this.cadastroPrestadorForm.value.dataNascimento, 'dd/MM/yyyy') as string;
      this.dataNascimentoInvalida = false;
    }
  }
  validaNomeEmpresa() {
    if (!this.cadastroPrestadorForm.get('nomeEmpresa')?.valid) {
      this.nomeEmpresaInvalido = true;
      throw new Error("Nome da empresa inválido!");
    } else {
      this.prestador.nomeFantasia = this.cadastroPrestadorForm.value.nomeEmpresa;
      this.nomeEmpresaInvalido = false;
    }
  }

}
