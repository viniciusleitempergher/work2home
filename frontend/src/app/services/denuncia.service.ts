import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { Denuncia } from 'src/models/dtos/Denuncia';

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
}
