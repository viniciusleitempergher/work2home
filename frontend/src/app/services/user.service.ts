import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Usuario } from 'src/models/Usuario';
import { environment } from 'src/environments/environment';
import { LoginResponse } from '../screens/auth/login-screen/login-screen.component';
import { catchError, delay, Observable, retry } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  constructor(private http: HttpClient) { }

  refresh(): Observable<any> {
    let unparsedToken = localStorage.getItem("refreshToken");

    if (!unparsedToken) throw new Error;

    let refreshToken = JSON.parse(unparsedToken);

    return this.http.post(`${environment.apiHostAddress}/auth/refresh`, {
        refreshToken
    }).pipe(
      retry(15),
    )
  }

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
    return new Promise(async resolve => {
        this.http.get(`${environment.apiHostAddress}/usuario/me`).pipe(
          retry(15)
        ).subscribe(response => {
          resolve(response as Usuario);
        })
    })
  }
  listarUsuarios(): Promise<Usuario[]> {
    return new Promise(async resolve => {
        try {
          this.http.get(`${environment.apiHostAddress}/usuario`).pipe(
            retry(15),
          ).subscribe(response => {
            resolve(response as Usuario[]);
          })
        } catch (err) {
          console.log("KKKKKK");
          
          resolve(await this.listarUsuarios())
        }
    })
  }

  esqueceuSenha(email: string): Promise<void> {
    return new Promise(resolve => {
      this.http.post(`${environment.apiHostAddress}/email/resgatar-senha`, {
        email
      }).pipe(
        retry(15),
      ).subscribe(response => {
        resolve();
      })
    })
  }

  alterarSenha(senha: string): Promise<void> {
    return new Promise(resolve => {
      this.http.patch(`${environment.apiHostAddress}/usuario/alterar-senha`, {
        novaSenha: senha
      }).pipe(
        retry(15),
      ).subscribe(response => {
        resolve();
      })
    })
  }

  cadastrarImagemPerfil(imagem: File): Promise<{ imagemUrl: string }> {
    return new Promise(resolve => {
      const formData = new FormData();
      formData.append("image", imagem, imagem.name)
      this.http.post(`${environment.apiHostAddress}/usuario/imagem`, formData)
      .pipe(
        retry(15),
      ).subscribe(response => resolve(response as { imagemUrl: string }))
    })
  }
  getUserRole(id: number): Promise<{ role: string }> {
    return new Promise(resolve => {
      this.http.get(`${environment.apiHostAddress}/usuario/${id}/get-role`).pipe(
        retry(15),
      ).subscribe(response => {
        resolve(response as { role: string });
      })
    });
  }
  banimentoUsuario(id:number): Promise<void>{
    return new Promise(resolve => {
      this.http.patch(`${environment.apiHostAddress}/usuario/banimento/${id}`,{}).pipe(
        retry(15),
      ).subscribe(response => {
        resolve();
      })
    });
  }
  cadastrarAdm(user:Usuario):Promise<Usuario>{
      return new Promise(resolve => {
        console.log(user)
        this.http.post(`${environment.apiHostAddress}/admin`,
          user
        ).subscribe(response => resolve(response as Usuario))
      })
    }
}

