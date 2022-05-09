import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Usuario } from 'src/models/Usuario';
import { environment } from 'src/environments/environment';
import { LoginResponse } from '../screens/auth/login-screen/login-screen.component';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  constructor(private http: HttpClient) { }

  login(email: string, senha: string) {
    return new Promise(resolve => {
      this.http.post(`${environment.apiHostAddress}/auth/login`, {
        email,
        senha
      }).subscribe(response => {
        resolve(response as LoginResponse);
      })
    })
  }

  getUserFromAccessToken(): Promise<Usuario> {
    return new Promise(resolve => {
      this.http.get(`${environment.apiHostAddress}/usuario/me`).subscribe(response => {
        resolve(response as Usuario);
      })
    })
  }
  listarUsuarios(): Promise<Usuario[]> {
    return new Promise(resolve => {
      this.http.get(`${environment.apiHostAddress}/usuario`).subscribe(response => {
        resolve(response as Usuario[]);
      })
    })
  }

  buscarUsuarioId(id:number): Promise<Usuario> {
    return new Promise(resolve => {
      this.http.get(`${environment.apiHostAddress}/usuario/${id}`).subscribe(response => {
        resolve(response as Usuario);
      })
    })
  }

  esqueceuSenha(email: string): Promise<void> {
    return new Promise(resolve => {
      this.http.post(`${environment.apiHostAddress}/email/resgatar-senha`, {
        email
      }).subscribe(response => {
        resolve();
      })
    })
  }

  alterarSenha(senha: string): Promise<void> {
    return new Promise(resolve => {
      this.http.patch(`${environment.apiHostAddress}/usuario/alterar-senha`, {
        novaSenha: senha
      }).subscribe(response => {
        resolve();
      })
    })
  }

  cadastrarImagemPerfil(imagem: File): Promise<{ imagemUrl: string }> {
    return new Promise(resolve => {
      const formData = new FormData();
      formData.append("image", imagem, imagem.name)
      this.http.post(`${environment.apiHostAddress}/usuario/imagem`, formData)
        .subscribe(response => resolve(response as { imagemUrl: string }))
    })
  }
  getUserRole(id: number): Promise<{ role: string }> {
    return new Promise(resolve => {
      this.http.get(`${environment.apiHostAddress}/usuario/${id}/get-role`).subscribe(response => {
        resolve(response as { role: string });

      })
    });
  }
  banimentoUsuario(id:number): Promise<void>{
    return new Promise(resolve => {
      this.http.patch(`${environment.apiHostAddress}/usuario/banir`,{
        id: id
      }).subscribe(response => {
        resolve();
      })
    });
  }
}

