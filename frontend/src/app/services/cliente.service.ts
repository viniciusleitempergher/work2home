import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { Cliente } from 'src/models/Cliente';

@Injectable({
  providedIn: 'root'
})
export class ClienteService {

  constructor(private http:HttpClient) { }



  cadastrarCliente(cliente:Cliente):Promise<Cliente>{

    console.log(cliente.cpf)
    return new Promise(resolve => {
      this.http.post(`${environment.apiHostAddress}/cliente`,
        cliente
      ).subscribe(response => resolve(response as Cliente))
    })
  }
}

