import { Avaliacao } from './../../models/Avaliacao';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { retry } from 'rxjs';

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
        ).pipe(
          retry(15),
        )
        .subscribe(() => {
          resolve();
        });
    });
  }
}
