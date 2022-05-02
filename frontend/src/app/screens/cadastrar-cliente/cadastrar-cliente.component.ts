import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { DatePipe } from '@angular/common';
import { Cliente } from 'src/models/Cliente';
import { ClienteService } from 'src/app/services/cliente.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-cadastrar-cliente',
  templateUrl: './cadastrar-cliente.component.html',
  styleUrls: ['./cadastrar-cliente.component.css'],
  providers: [DatePipe] //DatePipe como provider
})
export class CadastrarClienteComponent implements OnInit {

  cliente : Cliente = new Cliente();

  cadastroClienteForm= new FormGroup({
    email: new FormControl(null, [Validators.required, Validators.pattern("^[a-z0-9._-]+@[a-z0-9.-]+\\.[a-z]{2,4}$")]),
    nome: new FormControl(null, [Validators.required, Validators.minLength(1), Validators.pattern('^[a-zA-Zà-úÀ-Ú_ ]*$')]),
    senha: new FormControl(null, [Validators.required, Validators.minLength(8), Validators.pattern('^[a-zA-Zà-úÀ-Ú_ ]*$')]),
    repetirSenha: new FormControl(null, [Validators.required, Validators.minLength(8), Validators.pattern('^[a-zA-Zà-úÀ-Ú_ ]*$')]),
    dataNascimento: new FormControl(null, [Validators.required]),
    cpf: new FormControl(null,[Validators.required]),
    telefone: new FormControl(null,[Validators.required])
  });
  

  constructor(private clienteService: ClienteService,private router: Router,private datePipe: DatePipe) {
    
   }

  ngOnInit(): void {
    this.cadastroClienteForm.reset();
  }

  cancelar(){
    this.router.navigate(['login']);
  }

  async cadastrar(){
    this.validaEmail();
    this.validaNome();
    this.validaSenha();
    this.validaDataNascimento();
    this.validaCpf();
    this.validaTelefone();
    if(this.cadastroClienteForm.valid){
      await this.clienteService.cadastrarCliente(this.cliente);
      Swal.fire({
        position: 'center',
        icon: 'success',
        title: 'Cliente Cadastrado!!!',
        showConfirmButton: false,
        timer: 1500
      })
      this.router.navigate(['cadastrar-endereco']);
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
    if (!this.cadastroClienteForm.get('email')?.valid) {
      Swal.fire('Erro!!!', 'Email Invalido', 'error')
    }else{
      this.cliente.usuarioDto.email = this.cadastroClienteForm.value.email;
    }
  }
  validaNome() {
    if (!this.cadastroClienteForm.get('nome')?.valid) {
      Swal.fire('Erro!!!', 'Nome Invalido', 'error')
    }else{
      this.cliente.usuarioDto.nome = this.cadastroClienteForm.value.nome;
    }
  }
  validaSenha() {
    if (!this.cadastroClienteForm.get('senha')?.valid) {
      Swal.fire('Erro!!!', 'Senha Invalido', 'error')
    }else if(this.cadastroClienteForm.get('senha')?.value!=this.cadastroClienteForm.get('repetirSenha')?.value){
      Swal.fire('Erro!!!', 'Senhas não conferem', 'error')
    }else{
      this.cliente.usuarioDto.senha = this.cadastroClienteForm.value.senha;
    }

  }
  validaCpf() {
    if (!this.cadastroClienteForm.get('cpf')?.valid) {
      Swal.fire('Erro!!!', 'Cpf Invalido', 'error')
    }else{
      this.cliente.cpf = this.cadastroClienteForm.value.cpf;
    }
  }
  validaTelefone() {
    if (!this.cadastroClienteForm.get('telefone')?.valid) {
      Swal.fire('Erro!!!', 'Telefone Invalido', 'error')
    }else{
      this.cliente.usuarioDto.telefone = this.cadastroClienteForm.value.telefone;
    }
  }
  validaDataNascimento() {
    if(this.datePipe.transform(this.cadastroClienteForm.get("dataNascimento")?.value, 'dd/MM/yyyy')==null){
      Swal.fire('Erro!!!', 'Informe a data', 'error')
    }else{
      this.cliente.usuarioDto.dtNascimento = this.datePipe.transform(this.cadastroClienteForm.value.dataNascimento, 'dd/MM/yyyy') as string;
    }
  }

}
