import { Component, OnInit } from '@angular/core';
import { UserService } from 'src/app/services/user.service';
import { Usuario } from 'src/models/Usuario';

@Component({
  selector: 'app-prestador-main-screen',
  templateUrl: './prestador-main-screen.component.html',
  styleUrls: ['./prestador-main-screen.component.css']
})
export class PrestadorMainScreenComponent implements OnInit {

  prestador:Usuario = new Usuario();

  constructor(private usuarioService: UserService) { }

  async ngOnInit(): Promise<void> {
    this.prestador = await this.usuarioService.getUserFromAccessToken();
  }

}
