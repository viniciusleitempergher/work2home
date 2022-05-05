import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { PrestadorService } from 'src/app/services/prestador.service';
import { UserService } from 'src/app/services/user.service';
import { Prestador } from 'src/models/Prestador';
import { Usuario } from 'src/models/Usuario';
import { Cliente } from 'src/models/Cliente';
import { Avaliacao } from 'src/models/Avaliacao';
import { FormControl, FormGroup } from '@angular/forms';
import { environment } from 'src/environments/environment';
import { ClienteService } from 'src/app/services/cliente.service';

@Component({
  selector: 'app-perfil-usuario',
  templateUrl: './perfil-usuario.component.html',
  styleUrls: ['./perfil-usuario.component.css']
})
export class PerfilUsuarioComponent implements OnInit {

  user: Usuario = new Usuario();
  rolePerfil: string = '';
  cliente: Cliente = new Cliente();
  prestador: Prestador = new Prestador();
  av: Avaliacao = new Avaliacao();
  av2: Avaliacao = new Avaliacao();

  isVisible: boolean = true;
  isCliente: boolean = true;
  isImageVisible: boolean = true;
  starVazia: string = '../../../assets/star.svg';
  starMetade: string = '../../../assets/star-half.svg';
  starCheia: string = '../../../assets/star-fill.svg';
  usuarioPerfilId: number = +this.route.snapshot.params['usuarioId'];

  star1: string = this.starCheia;
  star2: string = this.starMetade;
  star3: string = this.starVazia;
  star4: string = this.starMetade;
  star5: string = this.starMetade;

  fotoPerfilUsuario: string = '';

  perfilForm = new FormGroup({
    imagemSrc: new FormControl()
  });

  nome: string = 'Jefferson Bisatto';
  email: string = 'jefferson.bisatto@gmail.com';
  telefone: string = '(047)99915-8513'

  constructor(private route: ActivatedRoute, private usuarioService: UserService, private clienteService: ClienteService, private prestadorService: PrestadorService, private router: Router) { }


  ngOnInit(): void {
    this.verificaUsuario();
  }

  async verificaUsuario() {
    this.user = await this.usuarioService.getUserFromAccessToken();
    this.rolePerfil = (await this.usuarioService.getUserRole(this.usuarioPerfilId)).role;
    this.buscarPerfil();
  }
  buscarPerfil() {

    if (this.rolePerfil == "CLIENTE") {
      this.perfilLogado();
      this.clienteLogado();
    } else
      if (this.rolePerfil == "PRESTADOR" || this.rolePerfil == "INATIVO") {
        this.perfilLogado();
        this.prestadorLogado();
      }
  }

  perfilLogado() {
    if (this.user.id == this.usuarioPerfilId) {
      this.isVisible = true;
    } else {
      this.isVisible = false;
    }
  }
  async clienteLogado() {
    this.cliente = await this.clienteService.getCliente(this.usuarioPerfilId);
    this.isCliente = true;
    this.carregaDadosCliente();
  }

  async prestadorLogado() {
    this.isCliente = false;
    this.prestador = await this.prestadorService.getPrestador(this.usuarioPerfilId);
    this.carregaDadosPrestador();
  }

  handleEditarImagem() {
    let file = document.getElementById('imagemFile');
    file?.click();
  }

  handleChangeFile(event: any) {
    if (event.target.files.length > 0) {
      const file = event.target.files[0];
      this.perfilForm.patchValue({
        imagemSrc: file
      });
    }
    this.addImageUsuario();
  }

  async addImageUsuario() {
    let caminhoImagem: { imagemUrl: string } = await this.usuarioService.cadastrarImagemPerfil(
      this.perfilForm.get("imagemSrc")?.value
    );
    this.fotoPerfilUsuario = environment.apiHostAddress + '/' + caminhoImagem.imagemUrl;
  }



  carregaDadosPrestador() {
    this.verificaImagemPerfil();
    this.nome = this.prestador.nome;
    this.email = this.prestador.email;
    this.telefone = this.prestador.telefone;
    this.prestador.mediaAvaliacao = 5;
    this.mediaAvaliacao(this.prestador.mediaAvaliacao);
    this.av.comentario = "comentario 1 teste testetes tetestetestetestetestetes stetestetesteteste!!!";
    this.av.nota = 5;
    this.av2.comentario = "comentario 2 !!!";
    this.av2.nota = 4;
    this.prestador.avaliacoes.push(this.av);
    this.prestador.avaliacoes.push(this.av2);

  }
  verificaImagemPerfil() {
    if (this.user.role == 'CLIENTE') {
      if (this.cliente.imagemUrl == null) {
        this.isImageVisible = false;
      } else {
        this.fotoPerfilUsuario = environment.apiHostAddress + '/' + this.cliente.imagemUrl;
      }
    } else {
      if (this.prestador.imagemUrl == null) {
        this.isImageVisible = false;
      } else {
        this.fotoPerfilUsuario = environment.apiHostAddress + '/' + this.prestador.imagemUrl;
      }
    }
  }
  carregaDadosCliente() {
    this.verificaImagemPerfil();
    this.nome = this.cliente.nome;
    this.email = this.cliente.email;
    this.telefone = this.cliente.telefone;
    this.mediaAvaliacao(this.cliente.mediaAvaliacao);
  }

  mediaAvaliacao(media: number) {
    this.media00;
    if (media >= 0.5) this.media05();
    if (media >= 1) this.media10();
    if (media >= 1.5) this.media15();
    if (media >= 2) this.media20();
    if (media >= 2.5) this.media25();
    if (media >= 3) this.media30();
    if (media >= 3.5) this.media35();
    if (media >= 4) this.media40();
    if (media >= 4.5) this.media45();
    if (media == 5) this.media50();
  }

  media00() {
    this.star1 = this.starVazia;
    this.star2 = this.starVazia;
    this.star3 = this.starVazia;
    this.star4 = this.starVazia;
    this.star5 = this.starVazia;
  }
  media05() {
    this.star1 = this.starMetade;
    this.star2 = this.starVazia;
    this.star3 = this.starVazia;
    this.star4 = this.starVazia;
    this.star5 = this.starVazia;
  }
  media10() {
    this.star1 = this.starCheia;
    this.star2 = this.starVazia;
    this.star3 = this.starVazia;
    this.star4 = this.starVazia;
    this.star5 = this.starVazia;
  }
  media15() {
    this.star1 = this.starCheia;
    this.star2 = this.starMetade;
    this.star3 = this.starVazia;
    this.star4 = this.starVazia;
    this.star5 = this.starVazia;
  }
  media20() {
    this.star1 = this.starCheia;
    this.star2 = this.starCheia;
    this.star3 = this.starVazia;
    this.star4 = this.starVazia;
    this.star5 = this.starVazia;
  }
  media25() {
    this.star1 = this.starCheia;
    this.star2 = this.starCheia;
    this.star3 = this.starMetade;
    this.star4 = this.starVazia;
    this.star5 = this.starVazia;
  }
  media30() {
    this.star1 = this.starCheia;
    this.star2 = this.starCheia;
    this.star3 = this.starCheia;
    this.star4 = this.starVazia;
    this.star5 = this.starVazia;
  }
  media35() {
    this.star1 = this.starCheia;
    this.star2 = this.starCheia;
    this.star3 = this.starCheia;
    this.star4 = this.starMetade;
    this.star5 = this.starVazia;
  }
  media40() {
    this.star1 = this.starCheia;
    this.star2 = this.starCheia;
    this.star3 = this.starCheia;
    this.star4 = this.starVazia;
    this.star5 = this.starVazia;
  }
  media45() {
    this.star1 = this.starCheia;
    this.star2 = this.starCheia;
    this.star3 = this.starCheia;
    this.star4 = this.starCheia;
    this.star5 = this.starMetade;
  }
  media50() {
    this.star1 = this.starCheia;
    this.star2 = this.starCheia;
    this.star3 = this.starCheia;
    this.star4 = this.starCheia;
    this.star5 = this.starCheia;
  }

}
