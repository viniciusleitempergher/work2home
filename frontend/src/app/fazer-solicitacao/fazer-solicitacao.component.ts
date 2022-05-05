import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Usuario } from 'src/models/Usuario';
import { UserService } from 'src/app/services/user.service';
import { Prestador } from 'src/models/Prestador';
import { PrestadorService } from 'src/app/services/prestador.service';
import { Router, ActivatedRoute } from '@angular/router';
import { Component, Input, OnInit } from '@angular/core';

@Component({
  selector: 'app-fazer-solicitacao',
  templateUrl: './fazer-solicitacao.component.html',
  styleUrls: ['./fazer-solicitacao.component.css']
})
export class FazerSolicitacaoComponent implements OnInit {

  categoriaId : number = 0;
  prestadores : Prestador[] = [];
  cidade : string = ""




  descricaoInvalida = false;

  cadastroSolicitacaoServico = new FormGroup({

    descricao: new FormControl(null, [Validators.required, Validators.minLength(1), Validators.pattern('^[a-zA-Zà-úÀ-Ú_ ]*$')]),

  });



  constructor(private router: ActivatedRoute, private prestadorService : PrestadorService,
    private userService: UserService) { }

  ngOnInit(): void {

   // this.id = Number.parseInt(this.router.url.charAt(this.router.url.length - 1))
   this.getCategoriaId()
   this.buscarPrestadores()


  }


  getCategoriaId(){
    this.categoriaId = this.router.snapshot.params['categoriaId']
  }


  buscarPrestadores(){

    this.prestadorService.getPrestadorByFiltro(this.categoriaId).then((res) =>{

      this.prestadores = res;
      console.log(res)
    })


  }


  validaDescricao(){

  }

}
