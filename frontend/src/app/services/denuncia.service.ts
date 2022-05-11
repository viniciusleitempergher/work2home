import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { Denuncia } from 'src/models/dtos/Denuncia';
import { DenunciaResponse } from 'src/models/dtos/DenunciaResponse';
import { UsuarioDenuncia } from 'src/models/dtos/UsuarioDenuncia';

@Injectable({
  providedIn: 'root'
})
export class DenunciaService {

  constructor(private http:HttpClient) { }

  cadastrarDenuncia(denuncia:Denuncia):Promise<Denuncia>{
    console.log(denuncia)
    return new Promise(resolve => {
      this.http.post(`${environment.apiHostAddress}/denuncia`,
        denuncia
      ).subscribe(response => resolve(response as Denuncia))
    })
  }

  getDenunciaPorQuatidade():Promise<DenunciaResponse[]>{
    return new Promise(resolve =>{
      this.http.get(`${environment.apiHostAddress}/denuncia`)
      .subscribe(response => resolve(response as DenunciaResponse[]))
    } )
  }
  getDenunciasPorId(id:number):Promise<UsuarioDenuncia>{
    return new Promise(resolve =>{
      this.http.get(`${environment.apiHostAddress}/denuncia/${id}`)
      .subscribe(response => resolve(response as UsuarioDenuncia))
    } )
  }
}
