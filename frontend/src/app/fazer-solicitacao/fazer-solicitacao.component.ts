import { PrestadorService } from 'src/app/services/prestador.service';
import { Router } from '@angular/router';
import { Component, Input, OnInit } from '@angular/core';

@Component({
  selector: 'app-fazer-solicitacao',
  templateUrl: './fazer-solicitacao.component.html',
  styleUrls: ['./fazer-solicitacao.component.css']
})
export class FazerSolicitacaoComponent implements OnInit {

  id : number = 0;



  constructor(private router: Router, private prestadorService : PrestadorService) { }

  ngOnInit(): void {

    this.id = Number.parseInt(this.router.url.charAt(this.router.url.length - 1))



  }

  buscarPrestadores(){



  }

}
