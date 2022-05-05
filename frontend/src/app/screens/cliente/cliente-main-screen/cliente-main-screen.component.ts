import { OrdemServicoResponse } from './../../../../models/OrdemServicoResponse';

import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { CategoriaService } from 'src/app/services/categoria.service';
import { Categoria } from 'src/models/Categoria';
import { environment } from 'src/environments/environment';
import { OrdemServicoService } from 'src/app/services/ordem-servico.service';

@Component({
  selector: 'app-cliente-main-screen',
  templateUrl: './cliente-main-screen.component.html',
  styleUrls: ['./cliente-main-screen.component.css'],
})
export class ClienteMainScreenComponent implements OnInit {
  categorias: Categoria[] = [];
  environment = environment;
  ordensServico: OrdemServicoResponse[] = [];

  value: string = '';

  categoriaId : number = 10;

  cadastroStatusForm = new FormGroup({
    cbxStatusServico: new FormControl(null, Validators.required),
  });

  constructor(
    private categoriaService: CategoriaService,
    private ordemService: OrdemServicoService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.buscarCategorias();
    this.value = '-1';
    this.getServicosByStatus();
  }

  getServicosByStatus() {
    try {
      this.ordemService
        .getAllByFilter(Number.parseInt(this.value))
        .then((res) => {
          this.ordensServico = res;
        });
    } catch (err) {}
  }

  buscarCategorias() {
    this.categoriaService.getAll().then((res) => {
      this.categorias = res;
    });
  }

  setarCbx() {
    this.cadastroStatusForm.get('cbxStatusServico')?.setValue('0');
  }
}
