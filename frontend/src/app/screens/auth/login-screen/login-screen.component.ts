import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { Router } from '@angular/router';
import { UserService } from 'src/app/services/user.service';
import { environment } from 'src/environments/environment';
import { CustomAlerts } from 'src/models/CustomAlerts';
import { Usuario } from 'src/models/Usuario';
import Swal from 'sweetalert2';

export type LoginResponse = {
  accessToken:string,
  refreshToken:string
}

@Component({
  selector: 'app-login-screen',
  templateUrl: './login-screen.component.html',
  styleUrls: ['./login-screen.component.css']
})
export class LoginScreenComponent implements OnInit {


  user = {} as Usuario;
  environment = environment;

  loginForm = new FormGroup({
    email: new FormControl(),
    senha: new FormControl(),
  });

  emailInvalido = false;
  senhaInvalida = false;

  constructor(private usuarioService:UserService, private router: Router) { }

  ngOnInit(): void {
    let strAccessToken = localStorage.getItem('accessToken');

    if (strAccessToken) {
      this.getUserNRedirect();
      return;
    }
  }

  async handleGoogleSignin() {

  }

  validaEmail(): boolean {
    let email = this.loginForm.value.email;
    if (!email || email.length < 5) {
      this.emailInvalido = true;
      return false;
    } else {
      this.emailInvalido = false;
      return true;
    }
  }

  validaSenha(): boolean {
    let senha = this.loginForm.value.senha;
    if (!senha || senha.length < 5) {
      this.senhaInvalida = true;
      return false;
    } else {
      this.senhaInvalida = false;
      return true;
    }
  }
  
  async handleLogin() {
    if (!this.validaEmail()) {
      CustomAlerts.primaryAlert.fire('Erro!', 'Preencha o email!', 'error')
      return;
    }
    if (!this.validaSenha()) {
      CustomAlerts.primaryAlert.fire('Erro!', 'Preencha a senha!', 'error')
      return;
    }

    let email = this.loginForm.value.email;
    let senha = this.loginForm.value.senha;

    let response:LoginResponse = await this.usuarioService.login(email, senha) as LoginResponse;
    
    localStorage.setItem('accessToken', JSON.stringify(response.accessToken));
    localStorage.setItem('refreshToken', JSON.stringify(response.refreshToken));
    
    this.getUserNRedirect();
  }

  async getUserNRedirect() {
    this.user = await this.usuarioService.getUserFromAccessToken();

    localStorage.setItem("userId", this.user.id + "");

    if (this.user.role == 'ADMIN')
      this.router.navigate(['admin']);

    if (this.user.role == 'CLIENTE')
      this.router.navigate(['cliente']);

    if (this.user.role == 'PRESTADOR')
      this.router.navigate(['prestador']);

    if (this.user.role == "CADASTRO_INCOMPLETO")
      this.router.navigate(['escolher-tipo']);
  }

  handleCadastrar(){
    this.router.navigate(['escolher-tipo']);
  }

}
