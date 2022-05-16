import { Component, OnInit } from '@angular/core';
import { Temas } from './temas';

@Component({
  selector: 'app-change-theme',
  templateUrl: './change-theme.component.html',
  styleUrls: ['./change-theme.component.css']
})
export class ChangeThemeComponent implements OnInit {
  constructor() { }

  ngOnInit(): void {
  }

  temaAmarelo() {
    localStorage.setItem("tema", "amarelo");
    Temas.temaAmarelo();
  }
  temaAzul() {
    localStorage.setItem("tema", "azul");
    Temas.temaAzul();
  }
  temaRoxo() {
    localStorage.setItem("tema", "roxo");
    Temas.temaRoxo();
  }
  temaPreto() {
    localStorage.setItem("tema", "preto");
    Temas.temaPreto();
  }
  temaVerde() {
    localStorage.setItem("tema", "verde");
    Temas.temaVerde();
  }

  temaVermelho() {
    localStorage.setItem("tema", "vermelho");
    Temas.temaVermelho();
  }
  
}
