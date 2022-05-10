import { AvaliacaoService } from './../services/avaliacao.service';
import { Avaliacao } from 'src/models/Avaliacao';
import { Usuario } from './../../models/Usuario';
import { UserService } from 'src/app/services/user.service';
import { OrdemServicoResponse } from './../../models/OrdemServicoResponse';
import { OrdemServicoService } from './../services/ordem-servico.service';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { environment } from 'src/environments/environment';

@Component({
  selector: 'app-ordem-servico',
  templateUrl: './ordem-servico.component.html',
  styleUrls: ['./ordem-servico.component.css']
})
export class OrdemServicoComponent implements OnInit {


  ordemServico : OrdemServicoResponse = new OrdemServicoResponse
  usuario : Usuario = new Usuario
  imagem : string= ""
  status : string = ""
  jaAvaliado: boolean = false

  thereIsImage : boolean = false;

  constructor( private route: ActivatedRoute,
    private router: Router,
    private osService: OrdemServicoService,
    private userService: UserService,
    private avaliacaoService: AvaliacaoService) { }

  async ngOnInit(): Promise<void> {
    this.usuario = await this.userService.getUserFromAccessToken();
    this.getOrdemServico()
  }

  async getOrdemServico(){
    let id = this.route.snapshot.params['id']
    await this.osService.getById(id).then((res) => {
      this.ordemServico = res
      this.imagem = environment.apiHostAddress + "/" + res.imagemUrl

      this.thereIsImage = !!res.imagemUrl
      this.status = res.status
      this.verificarAvaliacao(res.id)
    })
  }

  respostaOrcamento(aceitar : boolean){

    this.osService.responderOrcamento(aceitar, this.ordemServico.id)

    if(aceitar){
      this.status = "EM_ANDAMENTO"
    }else{
      this.status = "NEGADO"
    }
  }

  negarSolicitacao(){
    this.osService.negarSolicitacao(this.ordemServico.id).then(() => {
      this.status = 'NEGADO'
    }).catch((err) => {
      console.log(err)
    })
  }

  finalizarServico(){
    this.osService.finalizarOrcamento(this.ordemServico.id).then(() => {
      this.status = 'FINALIZADO'
    }).catch((err) => {
      console.log(err)
    })
  }

  verificarAvaliacao(osId : number){
    this.avaliacaoService.avaliacaoJaExiste(osId).then((res) =>
    this.jaAvaliado = res).catch((err) => {
      console.log(err)
    })
  }

  existe(x: any){
    return x != null
  }

  logOut(){
    localStorage.clear()
  }

}
