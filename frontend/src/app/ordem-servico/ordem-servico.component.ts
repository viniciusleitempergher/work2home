import Swal from 'sweetalert2';
import { AvaliacaoService } from './../services/avaliacao.service';
import { Usuario } from './../../models/Usuario';
import { UserService } from 'src/app/services/user.service';
import { OrdemServicoResponse } from './../../models/OrdemServicoResponse';
import { OrdemServicoService } from './../services/ordem-servico.service';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { environment } from 'src/environments/environment';
import { CustomAlerts } from 'src/models/CustomAlerts';

@Component({
  selector: 'app-ordem-servico',
  templateUrl: './ordem-servico.component.html',
  styleUrls: ['./ordem-servico.component.css'],
})
export class OrdemServicoComponent implements OnInit {
  ordemServico: OrdemServicoResponse = new OrdemServicoResponse();
  usuario: Usuario = new Usuario();
  imagem: string = '';
  status: string = '';
  jaAvaliado: boolean = false;

  thereIsImage: boolean = false;


  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private osService: OrdemServicoService,
    private userService: UserService,
    private avaliacaoService: AvaliacaoService
  ) {}

  async ngOnInit(): Promise<void> {
    this.usuario = await this.userService.getUserFromAccessToken();
    this.getOrdemServico();
  }

  async getOrdemServico() {
    let id = this.route.snapshot.params['id'];
    localStorage.setItem("ordemServicoId", JSON.stringify(id))
    await this.osService.getById(id).then((res) => {
      this.ordemServico = res;
      this.imagem = environment.apiHostAddress + '/' + res.imagemUrl;

      this.thereIsImage = !!res.imagemUrl;
      this.status = res.status;
      this.verificarAvaliacao(res.id);
    });
  }

  async respostaOrcamento(aceitar: boolean) {
    if (aceitar) {
      this.status = 'EM_ANDAMENTO';
      CustomAlerts.primaryAlert.fire({
        position: 'center',
        icon: 'success',
        title: 'Orçamento aceito!',
        showConfirmButton: false,
        timer: 1500,
      });

      this.osService.responderOrcamento(aceitar, this.ordemServico.id);
    } else {
      let escolha = await CustomAlerts.primaryAlert.fire({
        title: '<strong>Alerta!</strong>',
        icon: 'info',
        html: 'Você deseja realmente negar esse orçamento?',
        showCloseButton: true,
        showCancelButton: true,
        focusConfirm: false,
        confirmButtonText: 'Sim!',
        confirmButtonAriaLabel: 'Sim!',
        cancelButtonText: 'Não',
        cancelButtonAriaLabel: 'Não!',
      });

      if (escolha.isConfirmed) {
        this.status = 'NEGADO';
        CustomAlerts.primaryAlert.fire({
          position: 'center',
          icon: 'success',
          title: 'Orçamento negado',
          showConfirmButton: false,
          timer: 1500,
        });
        this.osService.responderOrcamento(aceitar, this.ordemServico.id);
      }
    }
  }

  async negarSolicitacao() {
    
    let escolha = await CustomAlerts.primaryAlert.fire({
      title: '<strong>Alerta!</strong>',
      icon: 'info',
      html: 'Você deseja realmente negar essa solicitação!?',
      showCloseButton: true,
      showCancelButton: true,
      focusConfirm: false,
      confirmButtonText: 'Sim!',
      confirmButtonAriaLabel: 'Sim!',
      cancelButtonText: 'Não',
      cancelButtonAriaLabel: 'Não!',
    });

    if (escolha.isConfirmed) {
      this.osService
        .negarSolicitacao(this.ordemServico.id)
        .then(() => {
          this.status = 'NEGADO';
          CustomAlerts.primaryAlert.fire({
            position: 'center',
            icon: 'success',
            title: 'Solicitação negada',
            showConfirmButton: false,
            timer: 1500,
          });
        })
        .catch((err) => {
          console.log(err);
        });
    }
  }

  finalizarServico() {
    var listaData = this.ordemServico.dataInicio.split('/');
    var dataFormatada = listaData[1] + '-' + listaData[0] + '-' + listaData[2];

    if (new Date(dataFormatada) >= new Date()) {
      CustomAlerts.primaryAlert.fire('Erro!', 'Muito cedo para finalizar', 'error');
    } else {
      this.osService
        .finalizarOrcamento(this.ordemServico.id)
        .then(() => {
          this.status = 'FINALIZADO';
          CustomAlerts.primaryAlert.fire({
            position: 'center',
            icon: 'success',
            title: 'Serviço finalizado!',
            showConfirmButton: false,
            timer: 1500,
          });
          this.router.navigate([
            `ordem-servico/${this.ordemServico.id}/avaliacao`,
          ]);
        })
        .catch((err) => {
          console.log(err);
        });
    }
  }

  verificarAvaliacao(osId: number) {
    this.avaliacaoService
      .avaliacaoJaExiste(osId)
      .then((res) => (this.jaAvaliado = res))
      .catch((err) => {
        console.log(err);
      });
  }

  existe(x: any) {
    return x != null;
  }

  logOut() {
    localStorage.clear();
  }

  async relatorioOs() {
    const fileURL = URL.createObjectURL(
      await this.osService.relatorioOs(this.ordemServico.id)
    );
    window.open(fileURL);
  }
}
