import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Usuario } from 'src/models/Usuario';
import { environment } from 'src/environments/environment';

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
        resolve(response as Usuario);
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
}
