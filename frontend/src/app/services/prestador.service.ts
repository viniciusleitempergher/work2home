import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { Prestador } from 'src/models/Prestador';
import { PrestadorCadastro } from '../screens/cadastrar-prestador/cadastrar-prestador.component';

@Injectable({
  providedIn: 'root'
})
export class PrestadorService {

  constructor(private http:HttpClient) { }

  cadastrarPrestador(prestador:PrestadorCadastro):Promise<PrestadorCadastro>{
    return new Promise(resolve => {
      this.http.post(`${environment.apiHostAddress}/prestador`,
      prestador
      ).subscribe(response => resolve(response as PrestadorCadastro))
    })
  }

  getPrestador(id:number): Promise<Prestador>{
    return new Promise(resolve => {
      this.http.get(`${environment.apiHostAddress}/prestador/${id}`).subscribe(response => {      
        resolve(response as Prestador);
      })
    })
  }
}
