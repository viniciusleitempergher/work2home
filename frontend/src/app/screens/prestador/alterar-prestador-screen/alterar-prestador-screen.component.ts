import { DatePipe } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { PrestadorService } from 'src/app/services/prestador.service';
import { UserService } from 'src/app/services/user.service';
import { CustomAlerts } from 'src/models/CustomAlerts';
import { Usuario } from 'src/models/Usuario';
import Swal from 'sweetalert2';
import { LoginResponse } from '../../auth/login-screen/login-screen.component';
import { PrestadorCadastro } from '../cadastrar-prestador/cadastrar-prestador.component';

@Component({
  selector: 'app-alterar-prestador-screen',
  templateUrl: './alterar-prestador-screen.component.html',
  styleUrls: ['./alterar-prestador-screen.component.css'],
  providers: [DatePipe]
})
export class AlterarPrestadorScreenComponent implements OnInit {

  user = {} as Usuario;
  prestador: PrestadorCadastro = {usuarioDto: new Usuario()} as PrestadorCadastro;
  cadastroIncompleto = false;

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

  async ngOnInit() {
    this.cadastroPrestadorForm.reset();

    this.user = await this.usuarioService.getUserFromAccessToken();

    if (this.user.role == 'CADASTRO_INCOMPLETO') {
      this.cadastroIncompleto = true;
    }

    this.cadastroPrestadorForm.get('email')?.setValue(this.user.email);
    this.cadastroPrestadorForm.get('nome')?.setValue(this.user.nome);

    this.cadastroPrestadorForm.controls['email'].disable()

    this.cadastroPrestadorForm.get('telefone')?.setValue(this.user.telefone);
    this.cadastroPrestadorForm.get('dataNascimento')?.setValue(this.user.dtNascimento);

    let prestador = await this.prestadorService.getPrestador(this.user.id);
    console.log(prestador.cnpj);
    
    this.cadastroPrestadorForm.get('cnpj')?.setValue(prestador.cnpj);
    this.cadastroPrestadorForm.get('nomeEmpresa')?.setValue(prestador.nomeFantasia);
  }

  cancelar() {
    this.router.navigate(['login']);
  }

  async cadastrar() {
    try {
      this.prestador.usuarioDto.email = this.user.email;
      if (!this.cadastroIncompleto) {
        this.validaSenha();
      }
      this.validaNome();
      this.validaDataNascimento();
      this.validaCnpj();
      this.validaTelefone();
      this.validaNomeEmpresa();

      if (this.cadastroIncompleto)
        await this.prestadorService.completarCadastro(this.prestador);
      else 
        await this.prestadorService.alterarPrestador(this.prestador);

        CustomAlerts.primaryAlert.fire({
        position: 'center',
        icon: 'success',
        title: 'Dados atualizados!',
        showConfirmButton: false,
        timer: 1500
      });
     this.router.navigate(['cidade']);
    } catch (e:any) {
      CustomAlerts.primaryAlert.fire('Erro!', e.message, 'error')
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
