import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { UserService } from 'src/app/services/user.service';
import { CustomAlerts } from 'src/models/CustomAlerts';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-alterar-senha-screen',
  templateUrl: './alterar-senha-screen.component.html',
  styleUrls: ['./alterar-senha-screen.component.css']
})
export class AlterarSenhaScreenComponent implements OnInit {
  accessToken:string = this.route.snapshot.paramMap.get('accessToken') as string;

  alterarSenhaForm = new FormGroup({
    senha: new FormControl(),
    repetirSenha: new FormControl()
  });

  constructor(private route: ActivatedRoute, private router: Router, private usuarioService:UserService) { }

  ngOnInit(): void {  
  }

  async handleAlterarSenha() {
    let senha = this.alterarSenhaForm.value.senha;
    let repetirSenha = this.alterarSenhaForm.value.repetirSenha;

    if (!senha || senha.length < 8) {
      CustomAlerts.primaryAlert.fire('Erro!', "A senha deve possuir mais que 8 caracteres!", 'error')
      return;
    }
    if (senha != repetirSenha) {
      CustomAlerts.primaryAlert.fire('Erro!', "Senhas diferentes!", 'error')
      return;
    }

    localStorage.setItem('accessToken', JSON.stringify(this.accessToken));
    this.usuarioService.alterarSenha(senha);
    
    await CustomAlerts.primaryAlert.fire({
      position: 'center',
      icon: 'success',
      title: 'Senha alterada!',
      showConfirmButton: false,
      timer: 1500
    });

    this.router.navigate(["login"])
  }
}
