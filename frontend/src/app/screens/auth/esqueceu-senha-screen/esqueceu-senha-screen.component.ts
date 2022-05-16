import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { UserService } from 'src/app/services/user.service';
import { CustomAlerts } from 'src/models/CustomAlerts';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-esqueceu-senha-screen',
  templateUrl: './esqueceu-senha-screen.component.html',
  styleUrls: ['./esqueceu-senha-screen.component.css']
})
export class EsqueceuSenhaScreenComponent implements OnInit {

  esqueceuSenhaForm = new FormGroup({
    email: new FormControl()
  })

  constructor(private usuarioService:UserService) { }

  ngOnInit(): void {
  }

  async handleEsqueceuSenha() {
    let email = this.esqueceuSenhaForm.value.email;

    await this.usuarioService.esqueceuSenha(email);

    CustomAlerts.primaryAlert.fire({
      position: 'center',
      icon: 'success',
      title: 'Confira seu email!',
      showConfirmButton: true
    })
  }
}
