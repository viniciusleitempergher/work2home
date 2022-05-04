import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-perfil-usuario',
  templateUrl: './perfil-usuario.component.html',
  styleUrls: ['./perfil-usuario.component.css']
})
export class PerfilUsuarioComponent implements OnInit {
  isVisible:boolean=true;
  starVazia:string='../../../assets/star.svg';
  starMetade:string='../../../assets/star-half.svg';
  starCheia:string='../../../assets/star-fill.svg';

  star1:string=this.starCheia;
  star2:string=this.starMetade;
  star3:string=this.starVazia;
  star4:string=this.starMetade;
  star5:string=this.starMetade;

  nome:string='Jefferson Bisatto';
  email:string='jefferson.bisatto@gmail.com';
  telefone:string='(047)99915-8513'
  constructor() { }
  
  ngOnInit(): void {
  }

}
