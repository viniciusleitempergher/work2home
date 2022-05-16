import { DatePipe } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { ClienteService } from 'src/app/services/cliente.service';
import { UserService } from 'src/app/services/user.service';
import { CustomAlerts } from 'src/models/CustomAlerts';
import { Usuario } from 'src/models/Usuario';
import Swal from 'sweetalert2';
import { LoginResponse } from '../../auth/login-screen/login-screen.component';
import { ClienteCadastro } from '../cadastrar-cliente/cadastrar-cliente.component';

@Component({
  selector: 'app-alterar-cliente-screen',
  templateUrl: './alterar-cliente-screen.component.html',
  styleUrls: ['./alterar-cliente-screen.component.css'],
  providers: [DatePipe]
})
export class AlterarClienteScreenComponent implements OnInit {

  user = {} as Usuario;
  cadastroIncompleto = false;
  cliente: ClienteCadastro = { usuarioDto: new Usuario() } as ClienteCadastro;

  emailInvalido = false;
  nomeInvalido = false;
  senhaInvalida = false;
  cpfInvalido = false;
  telefoneInvalido = false;
  dataNascimentoInvalida = false;

  cadastroClienteForm = new FormGroup({
    email: new FormControl(null, [Validators.required, Validators.pattern("^[a-z0-9._-]+@[a-z0-9.-]+\\.[a-z]{2,4}$")]),
    nome: new FormControl(null, [Validators.required, Validators.minLength(1), Validators.pattern('^[a-zA-Zà-úÀ-Ú_ ]*$')]),
    senha: new FormControl(null, [Validators.required, Validators.minLength(8)]),
    repetirSenha: new FormControl(null, [Validators.required, Validators.minLength(8)]),
    dataNascimento: new FormControl(null, [Validators.required]),
    cpf: new FormControl(null, [Validators.required]),
    telefone: new FormControl(null, [Validators.required])
  });

  constructor(private usuarioService: UserService, private clienteService: ClienteService, private router: Router, private datePipe: DatePipe) {

  }

  async ngOnInit() {
    this.cadastroClienteForm.reset();
    this.user = await this.usuarioService.getUserFromAccessToken();

    if (this.user.role == 'CADASTRO_INCOMPLETO') {
      this.cadastroIncompleto = true;
    }

    this.cadastroClienteForm.get('email')?.setValue(this.user.email);
    this.cadastroClienteForm.get('nome')?.setValue(this.user.nome);

    this.cadastroClienteForm.controls['email'].disable()

    this.cadastroClienteForm.get('telefone')?.setValue(this.user.telefone);
    this.cadastroClienteForm.get('dataNascimento')?.setValue(this.user.dtNascimento);
  }

  cancelar() {
    this.router.navigate(['login']);
  }

  async handleAlterar() {
    try {
      
      this.cliente.usuarioDto.email = this.user.email;
      if (!this.cadastroIncompleto) {
        this.validaSenha();
      }
      this.validaNome();
      this.validaDataNascimento();
      this.validaCpf();
      this.validaTelefone();

      if (!this.cadastroIncompleto)
        await this.clienteService.alterarCliente(this.cliente);
      else 
        await this.clienteService.completarCadastro(this.cliente);

        CustomAlerts.primaryAlert.fire({
        position: 'center',
        icon: 'success',
        title: 'Dados atualizados!',
        showConfirmButton: false,
        timer: 1500
      })
      this.router.navigate(['cadastrar-endereco']);
    } catch (e: any) {
      CustomAlerts.primaryAlert.fire('Erro!', e.message, 'error')
    }
  }

  validaNome() {
    if (!this.cadastroClienteForm.get('nome')?.valid) {
      this.nomeInvalido = true;
      throw new Error("Nome inválido!");
    } else {
      this.cliente.usuarioDto.nome = this.cadastroClienteForm.value.nome;
      this.nomeInvalido = false;
    }
  }
  validaSenha() {
    if (!this.cadastroClienteForm.get('senha')?.valid) {
      this.senhaInvalida = true;
      throw new Error("Senha inválida!");
    } else if (this.cadastroClienteForm.get('senha')?.value != this.cadastroClienteForm.get('repetirSenha')?.value) {
      this.senhaInvalida = true;
      throw new Error("Senhas não conferem");
    } else {
      this.cliente.usuarioDto.senha = this.cadastroClienteForm.value.senha;
      this.senhaInvalida = false;
    }

  }
  validaCpf() {
    if (!this.cadastroClienteForm.get('cpf')?.valid) {
      this.cpfInvalido = true;
      throw new Error("Cpf inválido!");
    } else {
      this.cliente.cpf = this.cadastroClienteForm.value.cpf;
      this.cpfInvalido = false;
    }
  }
  validaTelefone() {
    if (!this.cadastroClienteForm.get('telefone')?.valid) {
      this.telefoneInvalido = true;
      throw new Error("Telefone inválido!");
    } else {
      this.cliente.usuarioDto.telefone = this.cadastroClienteForm.value.telefone;
      this.telefoneInvalido = false;
    }
  }
  validaDataNascimento() {
    if (this.datePipe.transform(this.cadastroClienteForm.get("dataNascimento")?.value, 'dd/MM/yyyy') == null) {
      this.dataNascimentoInvalida = true;
      throw new Error("Informe a data");
    } else {
      this.cliente.usuarioDto.dtNascimento = this.datePipe.transform(this.cadastroClienteForm.value.dataNascimento, 'dd/MM/yyyy') as string;
      this.dataNascimentoInvalida = false;
    }
  }

}
