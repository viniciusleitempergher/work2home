import Swal from 'sweetalert2'
import { DatePipe } from '@angular/common';
import { OrdemServicoRequest } from 'src/models/OrdemServicoRequest';
import { OrdemServicoService } from 'src/app/services/ordem-servico.service';
import { ActivatedRoute, Router } from '@angular/router';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Component, OnInit } from '@angular/core';
import { CustomAlerts } from 'src/models/CustomAlerts';

@Component({
  selector: 'app-orcamento',
  templateUrl: './orcamento.component.html',
  styleUrls: ['./orcamento.component.css'],
  providers: [DatePipe]
})
export class OrcamentoComponent implements OnInit {

  dataInicioInvalido = false;
  tempoEstimadoInvalido = false;
  valorInvalido = false;
  osId: number = 0;

  ordemServico: OrdemServicoRequest = new OrdemServicoRequest();

  orcamentoForm = new FormGroup({
    dataInicio: new FormControl(null, [Validators.required]),
    tempoEstimado: new FormControl(null, [Validators.required]),
    valor: new FormControl(null, [Validators.required]),
  });

  constructor(private route: ActivatedRoute,
    private router: Router,
    private osService: OrdemServicoService,
    private datePipe: DatePipe) { }

  ngOnInit(): void {

    this.osId = this.route.snapshot.params['id']
  }

  enviarOrcamento(){

    try {
      this.validaDataInicio();
      this.validaTempoEstimado();
      this.validaValor();

    }catch(err:any){
      CustomAlerts.primaryAlert.fire('Erro!', err.message, 'error')
    }

    if(this.orcamentoForm.valid){

      this.osService.aceitarSolicitacao(this.osId, this.ordemServico)
      .then(() => {
        CustomAlerts.primaryAlert.fire({
          position: 'center',
          icon: 'success',
          title: 'Orçamento realizado!',
          showConfirmButton: false,
          timer: 1500
        })
        this.router.navigate(["ordem-servico/" + this.osId])
      })
      .catch((err) => {
        console.log(err)
      })
    }

  }

  cancelar(){
    this.router.navigate(["ordem-servico/" + this.osId])
  }

  validaDataInicio(){

    let data = this.datePipe.transform(this.orcamentoForm.value.dataInicio, 'dd/MM/yyyy') as string;

    if (data == null || new Date(this.orcamentoForm.value.dataInicio) < new Date()) {
      this.dataInicioInvalido = true;
      throw new Error("Data de inicio inválida!");
    } else {
      this.ordemServico.dataInicio = data
      this.dataInicioInvalido = false;
    }
  }

  validaTempoEstimado(){
    if (!this.orcamentoForm.get('tempoEstimado')?.valid) {
      this.tempoEstimadoInvalido = true;
      throw new Error("Tempo Estimado inválido!");
    } else {
      this.ordemServico.tempoEstimado = this.orcamentoForm.value.tempoEstimado;
      this.tempoEstimadoInvalido = false;
    }
  }

  validaValor(){
    if (!this.orcamentoForm.get('valor')?.valid) {
      this.tempoEstimadoInvalido = true;
      throw new Error("Valor inválido!");
    } else {
      this.ordemServico.valor = this.orcamentoForm.value.valor;
      this.valorInvalido = false;
    }
  }
}
