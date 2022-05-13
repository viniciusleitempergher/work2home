import { Router } from '@angular/router';
import { OrdemServicoService } from 'src/app/services/ordem-servico.service';
import { OrdemServicoResponse } from './../../../../models/OrdemServicoResponse';
import { Component, OnInit } from '@angular/core';
import { UserService } from 'src/app/services/user.service';
import { environment } from 'src/environments/environment';
import { Usuario } from 'src/models/Usuario';

@Component({
  selector: 'app-prestador-main-screen',
  templateUrl: './prestador-main-screen.component.html',
  styleUrls: ['./prestador-main-screen.component.css']
})
export class PrestadorMainScreenComponent implements OnInit {
  isImageVisible: boolean = true;
  fotoPerfilUsuario: string = '';
  usuario: Usuario = new Usuario;
  nomePrestador: string = '';
  qtdStatusOs : [0, 0, 0, 0, 0] = [0, 0, 0, 0, 0]



  environment = environment;
  ordensServico: OrdemServicoResponse[] = [];

  cbxStatus: string = '';


  constructor(
    private ordemService: OrdemServicoService,
    private userService : UserService,
    private router: Router) { }

  async ngOnInit(): Promise<void> {
    this.buscarQuantidadesDeOs();
    this.cbxStatus = '-1';
    this.usuario = await this.userService.getUserFromAccessToken();
    this.carregarInfoPrestador();
    this.getServicosByStatus();

  }

  buscarQuantidadesDeOs(){
    this.ordemService.buscarQuantidadesDeOs().then((res) => {
      for(let i = 0; i < res.length; i++){
        this.qtdStatusOs[res[i][0]] = res[i][1]
      }
    })
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
    } catch (err) {
      console.log(err)
    }
  }

  carregarInfoPrestador() {
    this.nomePrestador = "Bem vindo, " + this.usuario.nome;
    this.carregarImagemPerfil();
  }

  carregarImagemPerfil() {
    if (this.usuario.imagemUrl == null) {
      this.isImageVisible = false;
    } else {
      this.isImageVisible = true;
    }
    this.fotoPerfilUsuario = environment.apiHostAddress + '/' + this.usuario.imagemUrl;
  }

  logOut(){
    localStorage.clear();
  }

  reduzirDescricao(descricao : string){

    alert("fwfwe")
  }
}
