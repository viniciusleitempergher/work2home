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
      console.log(res)
      this.imagem = environment.apiHostAddress + "/" + res.imagemUrl
    })
  }

  carregarInformacoes(){

  }

}
