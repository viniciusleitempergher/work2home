import { Component } from '@angular/core';
import { Temas } from './screens/change-theme/temas';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'frontend';

  ngOnInit() {
    let tema = localStorage.getItem("tema");

    if (tema == "amarelo") {
      Temas.temaAmarelo();
    } else if (tema == "azul") {
      Temas.temaAzul();
    } else if (tema == "roxo") {
      Temas.temaRoxo();
    } else if (tema == "preto") {
      Temas.temaPreto();
    } else if (tema == "verde") {
      Temas.temaVerde();
    } else if (tema == "vermelho") {
      Temas.temaVermelho();
    }
  }
}
