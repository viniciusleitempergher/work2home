import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { DatePipe } from '@angular/common';
import { ClienteService } from 'src/app/services/cliente.service';
import Swal from 'sweetalert2';
import { UserService } from 'src/app/services/user.service';
import { Usuario } from 'src/models/Usuario';
import { LoginResponse } from '../../auth/login-screen/login-screen.component';
import { CustomAlerts } from 'src/models/CustomAlerts';

export type ClienteCadastro = {
  usuarioDto: Usuario;
  cpf: string;
}

@Component({
  selector: 'app-cadastrar-cliente',
  templateUrl: './cadastrar-cliente.component.html',
  styleUrls: ['./cadastrar-cliente.component.css'],
  providers: [DatePipe] //DatePipe como provider
})
export class CadastrarClienteComponent implements OnInit {

  cliente:ClienteCadastro = {usuarioDto: new Usuario()} as ClienteCadastro;

  emailInvalido = false;
  nomeInvalido = false;
  senhaInvalida = false;
  cpfInvalido = false;
  telefoneInvalido = false;
  dataNascimentoInvalida = false;

  cadastroClienteForm= new FormGroup({
    email: new FormControl(null, [Validators.required, Validators.pattern("^[a-z0-9._-]+@[a-z0-9.-]+\\.[a-z]{2,4}$")]),
    nome: new FormControl(null, [Validators.required, Validators.minLength(1), Validators.pattern('^[a-zA-Zà-úÀ-Ú_ ]*$')]),
    senha: new FormControl(null, [Validators.required, Validators.minLength(8)]),
    repetirSenha: new FormControl(null, [Validators.required, Validators.minLength(8)]),
    dataNascimento: new FormControl(null, [Validators.required]),
    cpf: new FormControl(null,[Validators.required]),
    telefone: new FormControl(null,[Validators.required])
  });
  

  constructor(private usuarioService:UserService, private clienteService: ClienteService,private router: Router,private datePipe: DatePipe) {
    
   }

  ngOnInit(): void {
    this.cadastroClienteForm.reset();
  }

  cancelar(){
    this.router.navigate(['login']);
  }
  
  async cadastrar() {
    try {
      this.validaEmail();
      this.validaNome();
      this.validaSenha();
      this.validaDataNascimento();
      this.validaCpf();
      this.validaTelefone();
    } catch (e:any) {
      CustomAlerts.primaryAlert.fire('Erro!', e.message, 'error')
    }
    if (this.cadastroClienteForm.valid) {
      await this.clienteService.cadastrarCliente(this.cliente);
      this.logar();
      CustomAlerts.primaryAlert.fire({
        position: 'center',
        icon: 'success',
        title: 'Cliente Cadastrado!',
        showConfirmButton: false,
        timer: 1500
      })
      this.router.navigate(['cadastrar-endereco']);
    }
  }
  async logar(){
    let response:LoginResponse = await this.usuarioService.login(this.cliente.usuarioDto.email, this.cliente.usuarioDto.senha) as LoginResponse;
    localStorage.setItem('accessToken', JSON.stringify(response.accessToken));
    localStorage.setItem('refreshToken', JSON.stringify(response.refreshToken));
  }
  validaEmail() {
    if (!this.cadastroClienteForm.get('email')?.valid) {
      this.emailInvalido = true;
      throw new Error("Email inválido!");
    }else{
      this.cliente.usuarioDto.email = this.cadastroClienteForm.value.email;
      this.emailInvalido = false;
    }
  }
  validaNome() {
    if (!this.cadastroClienteForm.get('nome')?.valid) {
      this.nomeInvalido = true;
      throw new Error("Nome inválido!");
    }else{
      this.cliente.usuarioDto.nome = this.cadastroClienteForm.value.nome;
      this.nomeInvalido = false;
    }
  }
  validaSenha() {
    if (!this.cadastroClienteForm.get('senha')?.valid) {
      this.senhaInvalida = true;
      throw new Error("Senha inválida!");
    }else if(this.cadastroClienteForm.get('senha')?.value!=this.cadastroClienteForm.get('repetirSenha')?.value){
      this.senhaInvalida = true;
      throw new Error("Senhas não conferem");
    }else{
      this.cliente.usuarioDto.senha = this.cadastroClienteForm.value.senha;
      this.senhaInvalida=false;
    }

  }
  validaCpf() {
    if (!this.cadastroClienteForm.get('cpf')?.valid) {
      this.cpfInvalido=true;
      throw new Error("Cpf inválido!");
    }else{
      this.cliente.cpf = this.cadastroClienteForm.value.cpf;
      this.cpfInvalido=false;
    }
  }
  validaTelefone() {
    if (!this.cadastroClienteForm.get('telefone')?.valid) {
      this.telefoneInvalido=true;
      throw new Error("Telefone inválido!");
    }else{
      this.cliente.usuarioDto.telefone = this.cadastroClienteForm.value.telefone;
      this.telefoneInvalido=false;
    }
  }
  validaDataNascimento() {
    if(this.datePipe.transform(this.cadastroClienteForm.get("dataNascimento")?.value, 'dd/MM/yyyy')==null){
      this.dataNascimentoInvalida=true;
      throw new Error("Informe a data");
    }else{
      this.cliente.usuarioDto.dtNascimento = this.datePipe.transform(this.cadastroClienteForm.value.dataNascimento, 'dd/MM/yyyy') as string;
      this.dataNascimentoInvalida=false;
    }
  }


}
