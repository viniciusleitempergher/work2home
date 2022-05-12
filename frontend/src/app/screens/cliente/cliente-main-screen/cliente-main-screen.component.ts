import { Usuario } from 'src/models/Usuario';
import { UserService } from 'src/app/services/user.service';
import { OrdemServicoResponse } from './../../../../models/OrdemServicoResponse';
import { Component, OnInit } from '@angular/core';
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
  usuario: Usuario = new Usuario;
  cbxStatus: string = '';
  categoriaId : number = 0;

  constructor(
    private categoriaService: CategoriaService,
    private ordemService: OrdemServicoService,
    private userService : UserService,
    private router: Router
  ) {}

  async ngOnInit(): Promise<void>{
    this.usuario = await this.userService.getUserFromAccessToken();

    this.buscarCategorias();
    this.cbxStatus = '-1';
    this.getServicosByStatus();
  }

  getServicosByStatus() {
    try {
      this.ordemService
        .getAllByFilter(Number.parseInt(this.cbxStatus))
        .then((res) => {
          this.ordensServico = res
          this.ordensServico.map(os => {
            if(os.descricao.length > 20){
              os.descricao = os.descricao.substring(0, 20) + "..."
            }

          }) as []
        });
    } catch (err) {}
  }

  buscarCategorias() {
    this.categoriaService.getAll().then((res) => {
      this.categorias = res;
    });
  }
}
