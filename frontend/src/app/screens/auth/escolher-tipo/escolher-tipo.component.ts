import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { Router } from '@angular/router';
import { UserService } from 'src/app/services/user.service';
import { Usuario } from 'src/models/Usuario';

@Component({
  selector: 'app-escolher-tipo',
  templateUrl: './escolher-tipo.component.html',
  styleUrls: ['./escolher-tipo.component.css']
})
export class EscolherTipoComponent implements OnInit {

  user = {} as Usuario;

  constructor(private router: Router, private usuarioService: UserService) { }

  async ngOnInit() {
    if (localStorage.getItem('accessToken'))
      this.user = await this.usuarioService.getUserFromAccessToken();
  }

  handlePrestador(){
    if (this.user.email && this.user.role == "CADASTRO_INCOMPLETO")
      this.router.navigate(['prestador/alterar']);
    else 
      this.router.navigate(['cadastrar-prestador']);
  }
  handleCliente(){
    if (this.user.email && this.user.role == "CADASTRO_INCOMPLETO")
      this.router.navigate(['cliente/alterar']);
    else 
      this.router.navigate(['cadastrar-cliente']);
  }

}
