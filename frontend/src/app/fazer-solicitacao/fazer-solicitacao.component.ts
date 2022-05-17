import { OrdemServicoService } from 'src/app/services/ordem-servico.service';

import {
  FormControl,
  FormGroup,
} from '@angular/forms';
import { Prestador } from 'src/models/Prestador';
import { PrestadorService } from 'src/app/services/prestador.service';
import { Router, ActivatedRoute } from '@angular/router';
import { Component, OnInit } from '@angular/core';
import Swal from 'sweetalert2';
import { OrdemServicoRequest } from 'src/models/OrdemServicoRequest';
import { environment } from 'src/environments/environment';
import { CustomAlerts } from 'src/models/CustomAlerts';

@Component({
  selector: 'app-fazer-solicitacao',
  templateUrl: './fazer-solicitacao.component.html',
  styleUrls: ['./fazer-solicitacao.component.css'],
})
export class FazerSolicitacaoComponent implements OnInit {
  environment = environment;
  categoriaId: number = 0;
  prestadores: Prestador[] = [];
  cidade: string = '';
  osr: OrdemServicoRequest = new OrdemServicoRequest();
  existePrestadores: boolean = this.prestadores.length != 0;
  prestadorId: number = 0;
  descricao: string = '';

  nomeImagem: string = ""

  solicitacaoForm = new FormGroup({
    imagemSrc: new FormControl(),
  });

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private prestadorService: PrestadorService,
    private osService: OrdemServicoService
  ) {}

  ngOnInit(): void {
    this.getCategoriaId();
    this.buscarPrestadores();
  }

  getCategoriaId() {
    this.categoriaId = this.route.snapshot.params['categoriaId'];
  }

  buscarPrestadores() {
    this.prestadorService.getPrestadorByFiltro(this.categoriaId).then((res) => {
      this.prestadores = res;

      this.existePrestadores = this.prestadores.length != 0;
    });
  }

  solicitar() {
    try {
      if (this.prestadorId == 0) {
        throw new Error('Selecione um prestador');
      }
      if (this.descricao == '') {
        throw new Error('Insira uma descrição');
      }

      this.osr.descricao = this.descricao;
      this.osr.prestadorId = this.prestadorId;
      this.osr.categoriaServicoId = this.categoriaId;

      this.osService.cadastrar(
        this.osr,
        this.solicitacaoForm.get('imagemSrc')?.value
      );

      CustomAlerts.primaryAlert.fire({
        position: 'center',
        icon: 'success',
        title: 'Solicitação realizada',
        showConfirmButton: false,
        timer: 1500,
      });
      this.router.navigate(['cliente']);
    } catch (err: any) {
      CustomAlerts.primaryAlert.fire('Erro!', err.message, 'error');
    }
  }

  onImgChange(event: any) {
    if (event.target.files.length > 0) {
      const file = event.target.files[0];
      this.nomeImagem = file.name

      this.solicitacaoForm.patchValue({
        imagemSrc: file,
      });
    }
  }

  getSrc(url: string) {
    if ((url).includes("https")) {
      return url;
    }
    return environment.apiHostAddress + '/' + url;
  }

  selecionarPrestadorId(id: number) {
    this.prestadorId = id;
  }

  cancelar() {
    this.router.navigate(['login']);
  }

  handleEditarImagem() {
    let file = document.getElementById('imagemFile');
    file?.click();
  }
  // teste
}
