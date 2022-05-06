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
  imagem : string= ""
  isFinalizado : boolean = false;
  isEmOrcamento : boolean = false;
  thereIsImage : boolean = false;

  constructor( private route: ActivatedRoute,
    private router: Router,
    private osService: OrdemServicoService) { }

  ngOnInit(): void {
    this.getOrdemServico()
  }

  async getOrdemServico(){
    let id = this.route.snapshot.params['id']
    await this.osService.getById(id).then((res) => {
      this.ordemServico = res
      this.imagem = environment.apiHostAddress + "/" + res.imagemUrl

      console.log(res.imagemUrl)



      this.thereIsImage = !!res.imagemUrl

      this.isFinalizado = res.status == "FINALIZADO";
      this.isEmOrcamento = res.status == "EM_ORCAMENTO"
    })
  }



  respostaOrcamento(aceitar : boolean){

    this.osService.responderOrcamento(aceitar, this.ordemServico.id)

    this.isEmOrcamento = false;

    if(aceitar){
      this.ordemServico.status = "EM_ANDAMENTO"
    }else{
      this.ordemServico.status = "NEGADO"
    }


  }

}
