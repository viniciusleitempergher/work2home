import { AvaliacaoService } from './../services/avaliacao.service';
import { OrdemServicoService } from './../services/ordem-servico.service';
import { OrdemServicoResponse } from './../../models/OrdemServicoResponse';
import { Router, ActivatedRoute } from '@angular/router';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-avaliacao',
  templateUrl: './avaliacao.component.html',
  styleUrls: ['./avaliacao.component.css']
})
export class AvaliacaoComponent implements OnInit {

  ordemServico : OrdemServicoResponse = new OrdemServicoResponse;

  constructor(private router: Router, private route: ActivatedRoute,
     private osService: OrdemServicoService, private avaliacaoService: AvaliacaoService) { }

  ngOnInit(): void {
    this.getOrdemServico()
  }

  getOrdemServico(){
    let id = this.route.snapshot.params['id']

    this.osService.getById(id).then((res) => {
      this.ordemServico = res;
    }).catch((err) => {
      console.log(err)
    })
  }
}
