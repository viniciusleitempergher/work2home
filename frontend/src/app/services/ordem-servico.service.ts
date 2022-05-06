import { OrdemServicoRequest } from './../../models/OrdemServicoRequest';
import { OrdemServicoResponse } from './../../models/OrdemServicoResponse';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root',
})
export class OrdemServicoService {
  constructor(private http: HttpClient) {}

  #cadastrarSolicitacao(
    solicitacao: OrdemServicoRequest
  ): Promise<OrdemServicoResponse> {
    return new Promise((resolve) => {
      this.http
        .post(
          `${environment.apiHostAddress}/ordem-servico/solicitar`,
          solicitacao
        )
        .subscribe((response) => {
          resolve(response as OrdemServicoResponse);
        });
    });
  }

  #cadastrarImagem(solicitacao: any, imagem: File): void {
    const formData = new FormData();
    formData.append('image', imagem, imagem.name);
    this.http
      .post(
        `${environment.apiHostAddress}/ordem-servico/${solicitacao.id}/imagem`,
        formData
      )
      .subscribe();
  }

  async cadastrar(
    solicitacao: OrdemServicoRequest,
    imagem: File
  ): Promise<void> {
    let solicitacaoResponse = await this.#cadastrarSolicitacao(solicitacao);
    this.#cadastrarImagem(solicitacaoResponse, imagem);
  }

  getAllByFilter(status: number): Promise<OrdemServicoResponse[]> {
    return new Promise((resolve) => {
      this.http
        .get(`${environment.apiHostAddress}/ordem-servico/filtro/${status}`)
        .subscribe((response) => resolve(response as OrdemServicoResponse[]));
    });
  }

  getById(id: number): Promise<OrdemServicoResponse> {
    return new Promise((resolve) => {
      this.http
        .get(`${environment.apiHostAddress}/ordem-servico/${id}`)
        .subscribe((response) => resolve(response as OrdemServicoResponse));
    });
  }


  responderOrcamento(aceitar : boolean, id : number) : Promise<void>{

    return new Promise((resolve) => {
      this.http
      .patch(`${environment.apiHostAddress}/ordem-servico/${id}/aceitar-orcamento`, {
        aceitar : aceitar
      })
      .subscribe(() => resolve());

    })
  }

}
