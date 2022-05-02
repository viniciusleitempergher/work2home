import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { CategoriaService } from 'src/app/services/categoria.service';
import { environment } from 'src/environments/environment';
import { Categoria } from 'src/models/Categoria';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-cadastrar-categoria',
  templateUrl: './cadastrar-categoria.component.html',
  styleUrls: ['./cadastrar-categoria.component.css']
})
export class CadastrarCategoriaComponent implements OnInit {

  environment = environment;

  categorias:Categoria[] = [];

  categoriaForm = new FormGroup({
    nome: new FormControl(),
    imagem: new FormControl(),
    imagemSrc: new FormControl()
  });

  constructor(private categoriaService: CategoriaService) { }

  ngOnInit(): void {
    this.loadCategories();
  }

  async loadCategories() {
    this.categorias = await this.categoriaService.getAll();
  }

  onImgChange(event: any) {
    if (event.target.files.length > 0) {
      const file = event.target.files[0];
      this.categoriaForm.patchValue({
        imagemSrc: file
      });
    }
  }

  async handleDeletarCategoria(id: number) {
    let escolha = await Swal.fire({
      title: '<strong>Alerta!</strong>',
      icon: 'info',
      html:
        'Você deseja realmente excluir essa categoria!?',
      showCloseButton: true,
      showCancelButton: true,
      focusConfirm: false,
      confirmButtonText:
        'Sim!',
      confirmButtonAriaLabel: 'Sim!',
      cancelButtonText:
        'Não',
      cancelButtonAriaLabel: 'Não!'
    });

    if (escolha.isConfirmed) {
      await this.categoriaService.deletar(id);
    }
  }

  async handleAddCategoria() {
    await this.categoriaService.cadastrar(
      this.categoriaForm.value.nome, this.categoriaForm.get("imagemSrc")?.value
    )
  }

}
