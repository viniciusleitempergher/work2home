import { OrdemServicoRequest } from '../../models/OrdemServicoRequest';
import { OrdemServicoResponse } from './../../models/OrdemServicoResponse';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable, Pipe } from '@angular/core';
import { environment } from 'src/environments/environment';
import { retry } from 'rxjs';

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
        ).pipe(
          retry(15),
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
      ).pipe(
        retry(15),
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
        .get(`${environment.apiHostAddress}/ordem-servico/filtro/${status}`).pipe(
          retry(15),
        )
        .subscribe((response) => resolve(response as OrdemServicoResponse[]));
    });
  }

  getById(id: number): Promise<OrdemServicoResponse> {
    return new Promise((resolve) => {
      this.http
        .get(`${environment.apiHostAddress}/ordem-servico/${id}`).pipe(
          retry(15),
        )
        .subscribe((response) => resolve(response as OrdemServicoResponse));
    });
  }

  aceitarSolicitacao(id: number, solicitacao : OrdemServicoRequest) : Promise<void> {
    return new Promise((resolve) => {
      this.http
        .patch(`${environment.apiHostAddress}/ordem-servico/${id}/aceitar-solicitacao`,
        solicitacao
        ).pipe(
          retry(15),
        )
        .subscribe(() => resolve());
    });
  }

  negarSolicitacao(id: number) : Promise<void> {
    return new Promise((resolve) => {
      this.http
        .patch(`${environment.apiHostAddress}/ordem-servico/${id}/negar-solicitacao`, {}).pipe(
          retry(15),
        )
        .subscribe(() => resolve());
    });
  }

  responderOrcamento(aceitar : boolean, id : number) : Promise<void>{
    return new Promise((resolve) => {
      this.http
      .patch(`${environment.apiHostAddress}/ordem-servico/${id}/aceitar-orcamento`, {
        aceitar : aceitar
      }).pipe(
        retry(15),
      )
      .subscribe(() => resolve());

    })
  }

  finalizarOrcamento(id : number): Promise<void>{
    return new Promise((resolve) => {
      this.http
      .patch(`${environment.apiHostAddress}/ordem-servico/${id}/finalizar-os`, {}).pipe(
        retry(15),
      )
      .subscribe(() => resolve());

    })
  }

  relatorioOs(id : number): Promise<any> {
    let headers = new HttpHeaders();
    headers = headers.set('Accept', 'application/pdf');
    return new Promise(resolve => {
      this.http.get(`${environment.apiHostAddress}/relatorio/servico/${id}`,
        { headers: headers, responseType: 'blob' as 'json' }).pipe(
          retry(15),
        )
        .subscribe(response => {
          resolve(response as any);
        });
    })
  }

  buscarQuantidadesDeOs() : Promise<(any)[4][4]>{
    return new Promise((resolve) => {
      this.http.get(`${environment.apiHostAddress}/ordem-servico/quantidades-servicos`).pipe(
        retry(15),
      ).subscribe(response => {
        resolve(response as (any)[4][4]);
      });
    })
  }

}
