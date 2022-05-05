import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { PrestadorService } from 'src/app/services/prestador.service';
import { UserService } from 'src/app/services/user.service';
import { Prestador } from 'src/models/Prestador';
import { Usuario } from 'src/models/Usuario';
import { Avaliacao } from 'src/models/Avaliacao';
import { timestamp } from 'rxjs';

@Component({
  selector: 'app-perfil-usuario',
  templateUrl: './perfil-usuario.component.html',
  styleUrls: ['./perfil-usuario.component.css']
})
export class PerfilUsuarioComponent implements OnInit {

  user:Usuario = new Usuario();
  prestador:Prestador = new Prestador();
  av:Avaliacao = new Avaliacao();
  av2:Avaliacao = new Avaliacao();
  foto = document.getElementById('fotoPerfil');
  file = document.getElementById('imagemFile');

  isVisible: boolean = true;
  starVazia: string = '../../../assets/star.svg';
  starMetade: string = '../../../assets/star-half.svg';
  starCheia: string = '../../../assets/star-fill.svg';

  star1: string = this.starCheia;
  star2: string = this.starMetade;
  star3: string = this.starVazia;
  star4: string = this.starMetade;
  star5: string = this.starMetade;

  nome: string = 'Jefferson Bisatto';
  email: string = 'jefferson.bisatto@gmail.com';
  telefone: string = '(047)99915-8513'

  constructor(private usuarioService: UserService, private prestadorService: PrestadorService, private router: Router) { }


  async ngOnInit(): Promise<void> {
    this.user = await this.usuarioService.getUserFromAccessToken();
    this.prestador = await this.prestadorService.getPrestador(this.user.id);
    this.carregaDados();
    this.fotoConfig();
  }

  fotoConfig(){   
  let fotoPerfil= document.getElementById('fotoPerfil'); 
  let bntEditar = document.getElementById('btnEditarFoto');
  let file = document.getElementById('imagemFile');
  bntEditar?.addEventListener('click', ()=>{
    file?.click();
  })
  file?.addEventListener('chance', (event)=>{
   console.log(event);
      alert('teste');
  });
  }

  
  carregaDados(){
    this.nome = this.prestador.nome;
    this.email = this.prestador.email;
    this.telefone = this.prestador.telefone;
    this.prestador.mediaAvaliacao=2.3;
    this.mediaAvaliacao(this.prestador.mediaAvaliacao);
    this.av.comentario="comentario 1 teste testetes tetestetestetestetestetes stetestetesteteste!!!";
    this.av.nota=3;
    this.av2.comentario="comentario 2 !!!";
    this.av2.nota=4;
    this.prestador.avaliacoes.push(this.av);
    this.prestador.avaliacoes.push(this.av2);
    
  }

  mediaAvaliacao(media:number){
    this.media00;
    if(media>=0.5){
       this.media05();
    }
    if(media>=1){
      this.media10();
    }
    if(media>=1.5){
      this.media15();
    }
    if(media>=2){
      this.media20();
    }
    if(media>=2.5){
      this.media25();
    }
    if(media>=3){
      this.media30();
    }
    if(media>=3.5){
      this.media35();
    }
    if(media>=4){
      this.media40();
    }
    if(media>=4.5){
      this.media45();
    }
    if(media==5){
      this.media50();
    }
  }
  media00(){
    this.star1=this.starVazia;
    this.star2=this.starVazia;
    this.star3=this.starVazia;
    this.star4=this.starVazia;
    this.star5=this.starVazia;
  }
  media05(){
    this.star1=this.starMetade;
    this.star2=this.starVazia;
    this.star3=this.starVazia;
    this.star4=this.starVazia;
    this.star5=this.starVazia;
  }
  media10(){
    this.star1=this.starCheia;
    this.star2=this.starVazia;
    this.star3=this.starVazia;
    this.star4=this.starVazia;
    this.star5=this.starVazia;
  }
  media15(){
    this.star1=this.starCheia;
    this.star2=this.starMetade;
    this.star3=this.starVazia;
    this.star4=this.starVazia;
    this.star5=this.starVazia;
  }
  media20(){
    this.star1=this.starCheia;
    this.star2=this.starCheia;
    this.star3=this.starVazia;
    this.star4=this.starVazia;
    this.star5=this.starVazia;
  }
  media25(){
    this.star1=this.starCheia;
    this.star2=this.starCheia;
    this.star3=this.starMetade;
    this.star4=this.starVazia;
    this.star5=this.starVazia;
  }
  media30(){
    this.star1=this.starCheia;
    this.star2=this.starCheia;
    this.star3=this.starCheia;
    this.star4=this.starVazia;
    this.star5=this.starVazia;
  }
  media35(){
    this.star1=this.starCheia;
    this.star2=this.starCheia;
    this.star3=this.starCheia;
    this.star4=this.starMetade;
    this.star5=this.starVazia;
  }
  media40(){
    this.star1=this.starCheia;
    this.star2=this.starCheia;
    this.star3=this.starCheia;
    this.star4=this.starVazia;
    this.star5=this.starVazia;
  }
  media45(){
    this.star1=this.starCheia;
    this.star2=this.starCheia;
    this.star3=this.starCheia;
    this.star4=this.starCheia;
    this.star5=this.starMetade;
  }
  media50(){
    this.star1=this.starCheia;
    this.star2=this.starCheia;
    this.star3=this.starCheia;
    this.star4=this.starCheia;
    this.star5=this.starCheia;
  }

}
