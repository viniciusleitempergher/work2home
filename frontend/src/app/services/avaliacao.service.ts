import { Avaliacao } from './../../models/Avaliacao';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root',
})
export class AvaliacaoService {
  constructor(private http: HttpClient) {}

  clienteAvaliaPrestador(avaliacao: Avaliacao, osId:number): Promise<void> {
    return new Promise((resolve) => {
      this.http
        .post(
          `${environment.apiHostAddress}/avaliaca/prestador-avalia-cliente/${osId}`,
          avaliacao
        )
        .subscribe(() => {
          resolve();
        });
    });
  }

  prestadorAvaliaCliente(avaliacao: Avaliacao, osId:number): Promise<void> {
    return new Promise((resolve) => {
      this.http
        .post(
          `${environment.apiHostAddress}/avaliacao/cliente-avalia-prestador/${osId}`,
          avaliacao
        )
        .subscribe(() => {
          resolve();
        });
    });
  }

  avaliacaoJaExiste(osId:number): Promise<boolean> {
    return new Promise((resolve) => {
      this.http
        .get(
          `${environment.apiHostAddress}/avaliacao/avaliacao-existe/${osId}`
        )
        .subscribe((res) => {
          resolve(res as boolean);
        });
    });
  }
}
