import { Categoria } from './../../../models/Categoria';
import { CategoriaService } from './../../services/categoria.service';
import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';

@Component({
  selector: 'app-cliente-main-screen',
  templateUrl: './cliente-main-screen.component.html',
  styleUrls: ['./cliente-main-screen.component.css']
})
export class ClienteMainScreenComponent implements OnInit {

  categorias : Categoria[] = []

  value : any

  cadastroStatusForm = new FormGroup({
    cbxStatusServico: new FormControl(null, Validators.required),
  });

  constructor(private categoriaService:CategoriaService, private router: Router) { }

  ngOnInit(): void {
    this.buscarCategorias()
    this.cadastroStatusForm.get('cbxStatusServico')?.setValue("0");

    this.value = "All"

  }


  buscarCategorias(){

    this.categoriaService.getAll().then((res) => {
      this.categorias = res
    })
  }

  setarCbx(){
    this.cadastroStatusForm.get('cbxStatusServico')?.setValue("0");
  }

}
