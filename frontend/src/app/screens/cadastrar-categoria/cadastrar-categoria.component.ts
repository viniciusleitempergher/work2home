import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { CategoriaService } from 'src/app/services/categoria.service';

@Component({
  selector: 'app-cadastrar-categoria',
  templateUrl: './cadastrar-categoria.component.html',
  styleUrls: ['./cadastrar-categoria.component.css']
})
export class CadastrarCategoriaComponent implements OnInit {

  categoriaForm = new FormGroup({
    nome: new FormControl(),
    imagem: new FormControl(),
    imagemSrc: new FormControl()
  });

  constructor(private categoriaService: CategoriaService) { }

  ngOnInit(): void {
  }

  onImgChange(event: any) {
  
    if (event.target.files.length > 0) {
      const file = event.target.files[0];
      this.categoriaForm.patchValue({
        imagemSrc: file
      });
    }
  }

  async handleAddCategoria() {
    await this.categoriaService.cadastrar(
      this.categoriaForm.value.nome, this.categoriaForm.get("imagemSrc")?.value
    )
  }

}
