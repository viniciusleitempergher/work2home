import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { Prestador } from 'src/models/Prestador';

@Injectable({
  providedIn: 'root'
})
export class PrestadorService {

  constructor(private http:HttpClient) { }

  cadastrarPrestador(prestador:Prestador):Promise<Prestador>{
    return new Promise(resolve => {
      this.http.post(`${environment.apiHostAddress}/prestador`,
      prestador
      ).subscribe(response => resolve(response as Prestador))
    })
  }
}
