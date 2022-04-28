import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';

@Component({
  selector: 'app-login-screen',
  templateUrl: './login-screen.component.html',
  styleUrls: ['./login-screen.component.css']
})
export class LoginScreenComponent implements OnInit {

  usuarioForm = new FormGroup({
    email: new FormControl(),
    senha: new FormControl(),
  });

  constructor() { }

  ngOnInit(): void {
  }

}
