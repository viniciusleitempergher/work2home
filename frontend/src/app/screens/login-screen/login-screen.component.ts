import { Component, Input, OnInit } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { Router } from '@angular/router';
import { UserService } from 'src/app/services/user.service';
import { Usuario } from 'src/models/Usuario';

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

  @Input() user = {} as Usuario;

  loginForm = new FormGroup({
    email: new FormControl(),
    senha: new FormControl(),
  });

  constructor(private usuarioService:UserService, private router: Router) { }

  ngOnInit(): void {
    
  }
  
  async handleLogin() {    
    let response:LoginResponse = await this.usuarioService.login(this.loginForm.value.email, this.loginForm.value.senha) as LoginResponse;
    
    localStorage.setItem('accessToken', JSON.stringify(response.accessToken));
    localStorage.setItem('refreshToken', JSON.stringify(response.refreshToken));
    
    this.user = await this.usuarioService.getUserFromAccessToken();

    if (this.user.role == 'ADMIN')
      this.router.navigate(['admin']);
      if (this.user.role == 'CLIENTE')
      alert("Cliente Logado");
    
    
  }

  novoCadastro(){
    this.router.navigate(['escolher-tipo']);
  }

}
