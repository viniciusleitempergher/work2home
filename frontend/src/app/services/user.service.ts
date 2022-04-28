import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Usuario } from 'src/models/Usuario';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  constructor(private http:HttpClient) { }

  getUserFromAccessToken():Promise<Usuario> {
    return new Promise(resolve => {
      this.http.get("http://localhost:8080/usuario/me").subscribe(response => {
        resolve(response as Usuario);
      })
    })
  }
}
