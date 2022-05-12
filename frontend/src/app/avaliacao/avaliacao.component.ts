import { Avaliacao } from 'src/models/Avaliacao';
import { UserService } from 'src/app/services/user.service';
import { Usuario } from 'src/models/Usuario';
import { AvaliacaoService } from './../services/avaliacao.service';
import { OrdemServicoService } from './../services/ordem-servico.service';
import { OrdemServicoResponse } from './../../models/OrdemServicoResponse';
import { Router, ActivatedRoute } from '@angular/router';
import { Component, OnInit } from '@angular/core';
import { Location } from '@angular/common';

@Component({
  selector: 'app-avaliacao',
  templateUrl: './avaliacao.component.html',
  styleUrls: ['./avaliacao.component.css']
})
export class AvaliacaoComponent implements OnInit {

  ordemServico : OrdemServicoResponse = new OrdemServicoResponse;
  usuario: Usuario = new Usuario;
  comentario : string = "";
  clienteLogado : boolean = false;

  avaliacao: Avaliacao = new Avaliacao;

  constructor(private router: Router, private route: ActivatedRoute,
     private osService: OrdemServicoService, private avaliacaoService: AvaliacaoService,
     private userService : UserService, private location: Location) { }

  async ngOnInit(): Promise<void> {
    this.getOrdemServico()
    this.usuario = await this.userService.getUserFromAccessToken();
    this.clienteLogado = this.usuario.role == "CLIENTE";
  }

  getOrdemServico(){
    let id = this.route.snapshot.params['id']
    console.log(id)

    this.osService.getById(id).then((res) => {
      this.ordemServico = res;
    }).catch((err) => {
      console.log(err)
    })
  }

  selecionarEstrela(qtdEstrelas : number){
    this.avaliacao.nota = qtdEstrelas;
  }

  avaliarUsuario(){
    try{
    if(this.usuario.role == "CLIENTE"){
      this.avaliacaoService.clienteAvaliaPrestador(this.avaliacao, this.ordemServico.id)
    }else if(this.usuario.role == "PRESTADOR"){
      this.avaliacaoService.prestadorAvaliaCliente(this.avaliacao, this.ordemServico.id)
    }
    this.location.back()

  }catch(err){
      console.log(err)
    } 
  }
}
