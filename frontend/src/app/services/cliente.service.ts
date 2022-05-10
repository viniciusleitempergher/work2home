import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { retry } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Cliente } from 'src/models/Cliente';
import { ClienteCadastro } from '../screens/cliente/cadastrar-cliente/cadastrar-cliente.component';

@Injectable({
  providedIn: 'root'
})
export class ClienteService {
  constructor(private http:HttpClient) { }

  cadastrarCliente(cliente:ClienteCadastro):Promise<ClienteCadastro>{
    return new Promise(resolve => {
      this.http.post(`${environment.apiHostAddress}/cliente`,
        cliente
      ).pipe(
        retry(15),
      ).subscribe(response => resolve(response as ClienteCadastro))
    })
  }
  alterarCliente(cliente:ClienteCadastro):Promise<ClienteCadastro>{
    return new Promise(resolve => {
      this.http.put(`${environment.apiHostAddress}/cliente`,
        cliente
      ).subscribe(response => resolve(response as ClienteCadastro))
    })
  }
  completarCadastro(cliente: ClienteCadastro) {
    return new Promise(resolve => {
      this.http.put(`${environment.apiHostAddress}/cliente/completar-cadastro`,
        cliente
      ).subscribe(response => resolve(response as ClienteCadastro))
    })
  }

  getCliente(id:number): Promise<Cliente>{
      return new Promise(resolve => {
        this.http.get(`${environment.apiHostAddress}/cliente/${id}`).subscribe(response => {
          resolve(response as Cliente);
        })
      })
   }
}

