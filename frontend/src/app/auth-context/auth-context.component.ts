import { Component, OnInit } from '@angular/core';
import { Usuario } from 'src/models/Usuario';
import { UserService } from '../services/user.service';

@Component({
  selector: 'app-auth-context',
  templateUrl: './auth-context.component.html',
  styleUrls: ['./auth-context.component.css']
})
export class AuthContextComponent implements OnInit {
  user:Usuario = {} as Usuario;

  constructor(private usuarioService:UserService) { }

  async ngOnInit(): Promise<void> {
    if (!this.user.email) {
      this.user = await this.usuarioService.getUserFromAccessToken();
    }
  }
}
