import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';

@Component({
  selector: 'app-esqueceu-senha-screen',
  templateUrl: './esqueceu-senha-screen.component.html',
  styleUrls: ['./esqueceu-senha-screen.component.css']
})
export class EsqueceuSenhaScreenComponent implements OnInit {

  esqueceuSenhaForm = new FormGroup({
    email: new FormControl()
  })

  constructor() { }

  ngOnInit(): void {
  }

  handleEsqueceuSenha() {

  }
}
