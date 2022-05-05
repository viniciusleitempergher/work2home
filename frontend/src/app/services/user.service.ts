import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Usuario } from 'src/models/Usuario';
import { environment } from 'src/environments/environment';
import { LoginResponse } from '../screens/auth/login-screen/login-screen.component';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  constructor(private http:HttpClient) { }

  login(email:string, senha:string) {
    return new Promise(resolve => {
      this.http.post(`${environment.apiHostAddress}/auth/login`, {
          email,
          senha
      }).subscribe(response => {
        resolve(response as LoginResponse);
      })
    })
  }

  getUserFromAccessToken():Promise<Usuario> {
    return new Promise(resolve => {
      this.http.get(`${environment.apiHostAddress}/usuario/me`).subscribe(response => {
        resolve(response as Usuario);
      })
    })
  }

  esqueceuSenha(email: string):Promise<void> {
    return new Promise(resolve => {
      this.http.post(`${environment.apiHostAddress}/email/resgatar-senha`, {
        email
      }).subscribe(response => {
        resolve();
      })
    })
  }

  alterarSenha(senha: string):Promise<void> {
    return new Promise(resolve => {
      this.http.patch(`${environment.apiHostAddress}/usuario/alterar-senha`, {
        novaSenha: senha
      }).subscribe(response => {
        resolve();
      })
    })
  }
}
