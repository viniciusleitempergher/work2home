import { OrdemServicoResponse } from './../../models/OrdemServicoResponse';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { Endereco } from 'src/models/Endereco';

@Injectable({
  providedIn: 'root'
})
export class OrdemServicoService {

  constructor(private http:HttpClient) { }


  cadastrarEndereco(endereco:Endereco):Promise<Endereco>{
    return new Promise(resolve => {
      this.http.post(`${environment.apiHostAddress}/endereco`,
        endereco
      ).subscribe(response => resolve(response as Endereco))
    })
  }

  getAllByFilter(status:number):Promise<OrdemServicoResponse[]>{
    return new Promise(resolve => {
      this.http.get(`${environment.apiHostAddress}/ordem-servico/filtro/${status}`)
        .subscribe(response => resolve(response as OrdemServicoResponse[]))
    })
  }
}
