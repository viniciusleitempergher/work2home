import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { Endereco } from 'src/models/Endereco';

@Injectable({
  providedIn: 'root'
})
export class EnderecoService {

  constructor(private http:HttpClient) { }


  cadastrarEndereco(endereco:Endereco):Promise<Endereco>{
    return new Promise(resolve => {
      this.http.post(`${environment.apiHostAddress}/endereco`,
        endereco
      ).subscribe(response => resolve(response as Endereco))
    })
  }

  getEndereco(id:number): Promise<Endereco>{
    return new Promise(resolve => {
      this.http.get(`${environment.apiHostAddress}/endereco/${id}`).subscribe(response => {
        resolve(response as Endereco);
      })
    })
 }
}
