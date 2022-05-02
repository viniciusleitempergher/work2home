import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Cidade } from 'src/models/Cidade';
import { Estado } from 'src/models/Estado';

@Injectable({
  providedIn: 'root'
})
export class EnderecoApiService {

  constructor(private http: HttpClient) { }

  listarEstados(): Observable<Estado[]> {
    return this.http.get<Estado[]>('https://servicodados.ibge.gov.br/api/v1/localidades/estados?orderBy=nome');
  }
  listarEstadosId(indice: number): Observable<Estado> {
    return this.http.get<Estado>('https://servicodados.ibge.gov.br/api/v1/localidades/estados/' + indice);
  }
  listarCidades(sigla: string): Observable<Cidade[]> {
    return this.http.get<Cidade[]>('https://servicodados.ibge.gov.br/api/v1/localidades/estados/' + sigla + '/municipios?orderBy=nome');
  }
}
