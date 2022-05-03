import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { CidadePrestador } from 'src/models/CidadePrestador';

@Injectable({
  providedIn: 'root'
})
export class CidadeService {

  constructor(private http:HttpClient) { }


  cadastrarCidade(cidade:CidadePrestador):Promise<CidadePrestador>{

    return new Promise(resolve => {
      this.http.post(`${environment.apiHostAddress}/cidade`,
        cidade
      ).subscribe(response => resolve(response as CidadePrestador))
    })
  }  
  getAll():Promise<CidadePrestador[]>{
    return new Promise(resolve => {
      this.http.get(`${environment.apiHostAddress}/cidade`)
        .subscribe(response => resolve(response as CidadePrestador[]))
    })
  }

  deletar(id: number):Promise<void> {
    return new Promise(resolve => {
      this.http.delete(`${environment.apiHostAddress}/cidade/${id}`)
        .subscribe(response => resolve())
    })
  }

}
