import Swal  from 'sweetalert2';
import { AvaliacaoService } from './../services/avaliacao.service';
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
      Swal.fire({
        position: 'center',
        icon: 'success',
        title: 'Orçamento aceito!',
        showConfirmButton: false,
        timer: 1500
      })
    }else{
      this.status = "NEGADO"
      Swal.fire({
        position: 'center',
        icon: 'success',
        title: 'Orçamento negado',
        showConfirmButton: false,
        timer: 1500
      })
    }
  }

  negarSolicitacao(){
    this.osService.negarSolicitacao(this.ordemServico.id).then(() => {
      this.status = 'NEGADO'
      Swal.fire({
        position: 'center',
        icon: 'success',
        title: 'Solicitação negada',
        showConfirmButton: false,
        timer: 1500
      })
    }).catch((err) => {
      console.log(err)
    })
  }

  finalizarServico(){

    var listaData = this.ordemServico.dataInicio.split('/');
    var dataFormatada = listaData[1] + '-' + listaData[0] + '-' +
    listaData[2];

    if(new Date(dataFormatada) >= new Date){
      Swal.fire('Erro!', "Muito cedo para finalizar", 'error')
    }
    else{
    this.osService.finalizarOrcamento(this.ordemServico.id).then(() => {
      this.status = 'FINALIZADO'
      Swal.fire({
        position: 'center',
        icon: 'success',
        title: 'Serviço finalizado!',
        showConfirmButton: false,
        timer: 1500
      })
      this.router.navigate([`ordem-servico/${this.ordemServico.id}/avaliacao` ])
    }).catch((err) => {
      console.log(err)
    })}
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

  async relatorioOs(){
    const fileURL = URL.createObjectURL(await this.osService.relatorioOs(this.ordemServico.id));
    window.open(fileURL);
  }
}
