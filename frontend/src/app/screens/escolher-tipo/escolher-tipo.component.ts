import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { Router } from '@angular/router';

@Component({
  selector: 'app-escolher-tipo',
  templateUrl: './escolher-tipo.component.html',
  styleUrls: ['./escolher-tipo.component.css']
})
export class EscolherTipoComponent implements OnInit {


  constructor(private router: Router) { }

  ngOnInit(): void {
  }

  cadastrarPrestador(){
    this.router.navigate(['cadastrar-prestador']);
  }
  cadastrarCliente(){
    this.router.navigate(['cadastrar-cliente']);
  }

}
