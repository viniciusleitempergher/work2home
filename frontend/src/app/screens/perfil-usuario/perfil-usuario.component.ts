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
import Swal from 'sweetalert2';
import { DenunciaService } from 'src/app/services/denuncia.service';
import { Denuncia } from 'src/models/dtos/Denuncia';

@Component({
  selector: 'app-perfil-usuario',
  templateUrl: './perfil-usuario.component.html',
  styleUrls: ['./perfil-usuario.component.css']
})
export class PerfilUsuarioComponent implements OnInit {

  user: Usuario = new Usuario();
  rolePerfil: string = '';
  cliente: Cliente = new Cliente();
  cidadeCliente: string = '';
  estadoCliente: string = '';
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

  star1: string = this.starVazia;
  star2: string = this.starVazia;
  star3: string = this.starVazia;
  star4: string = this.starVazia;
  star5: string = this.starVazia;


  fotoPerfilUsuario: string = '';
  btnEditar: string = '';

  perfilForm = new FormGroup({
    imagemSrc: new FormControl()
  });

  nome: string = '';
  email: string = '';
  telefone: string = ''

  constructor(private denunciaService: DenunciaService, private route: ActivatedRoute, private usuarioService: UserService, private clienteService: ClienteService, private prestadorService: PrestadorService, private router: Router) { }


  ngOnInit(): void {
    this.verificaUsuario();
  }

  async verificaUsuario() {
    this.user = await this.usuarioService.getUserFromAccessToken();
    this.rolePerfil = (await this.usuarioService.getUserRole(this.usuarioPerfilId)).role;
    this.buscarPerfil();
  }
  buscarPerfil() {
    this.perfilLogado();
    if (this.rolePerfil == "CLIENTE") {
      this.btnEditar = "/cliente/alterar";
      this.perfilCliente();
    } else
      if (this.rolePerfil == "PRESTADOR" || this.rolePerfil == "INATIVO") {
        this.btnEditar = "/prestador/alterar";
        this.perfilPrestador();
      }
  }

  perfilLogado() {
    if (this.user.id == this.usuarioPerfilId) {
      this.isVisible = true;
    } else {
      this.isVisible = false;
    }
  }
  async perfilCliente() {
    this.cliente = await this.clienteService.getCliente(this.usuarioPerfilId);
    this.isCliente = true;
    this.carregaDadosCliente();
    this.carregarFoto();

  }
  async perfilPrestador() {
    this.isCliente = false;
    this.prestador = await this.prestadorService.getPrestador(this.usuarioPerfilId);
    this.carregaDadosPrestador();
    this.carregarFoto();
  }
  carregarFoto() {
    console.log(this.cliente.imagemUrl);
      console.log(this.prestador.imagemUrl);
    if (!this.cliente.imagemUrl && !this.prestador.imagemUrl) {
      this.isImageVisible = false;
    } else {      
      if ((this.cliente.imagemUrl).includes("https") || (this.prestador.imagemUrl).includes("https")) {
        this.fotoPerfilUsuario = this.cliente.imagemUrl ? this.cliente.imagemUrl : this.prestador.imagemUrl;
      } else {
        this.fotoPerfilUsuario = environment.apiHostAddress + '/' + (this.cliente.imagemUrl ? this.cliente.imagemUrl : this.prestador.imagemUrl);
      }
    }
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
    this.isImageVisible = true;
  }



  carregaDadosPrestador() {
    this.nome = this.prestador.nome;
    this.email = this.prestador.email;
    this.telefone = this.prestador.telefone;
    this.prestador.mediaAvaliacao = 3.5;
    this.mediaAvaliacao(this.prestador.mediaAvaliacao);
    this.av.comentario = "comentario 1 teste testetes tetestetestetestetestetes stetestetesteteste!!!";
    this.av.nota = 5;
    this.av2.comentario = "comentario 2 !!!";
    this.av2.nota = 4;
    this.prestador.avaliacoes.push(this.av);
    this.prestador.avaliacoes.push(this.av2);

  }

  carregaDadosCliente() {
    this.nome = this.cliente.nome;
    this.email = this.cliente.email;
    this.telefone = this.cliente.telefone;
    this.cidadeCliente = this.cliente.cidade;
    this.estadoCliente = this.cliente.estado;
    this.mediaAvaliacao(this.cliente.mediaAvaliacao);
  }

  mediaAvaliacao(media: number) {
    if (media >= 0.5) this.star1 = this.starMetade;
    if (media >= 1) this.star1 = this.starCheia;
    if (media >= 1.5) this.star2 = this.starMetade;
    if (media >= 2) this.star2 = this.starCheia;
    if (media >= 2.5) this.star3 = this.starMetade;
    if (media >= 3) this.star3 = this.starCheia;
    if (media >= 3.5) this.star4 = this.starMetade;
    if (media >= 4) this.star4 = this.starCheia;
    if (media >= 4.5) this.star5 = this.starMetade;
    if (media == 5) this.star5 = this.starCheia;
  }

  denuncia() {
    const swalWithBootstrapButtons = Swal.mixin({
      customClass: {
        title: 'titles',
        popup: 'popus',
        input: 'inputs',
        confirmButton: 'botaoDenuncia',
        cancelButton: 'botaoCancela'
      },
      buttonsStyling: false
    })

    swalWithBootstrapButtons.fire({
      title: 'Descreva o motivo da denúncia',
      input: 'text',
      inputAttributes: {
        autocapitalize: 'off'
      },
      showCancelButton: true,
      confirmButtonText: 'Denunciar',
      showLoaderOnConfirm: true,
      preConfirm: (texto) => {
        console.log(texto)
        let denuncia = new Denuncia();
        denuncia.denunciadorId = this.user.id;

        denuncia.denunciadoId = this.usuarioPerfilId;
        denuncia.descricao = texto;
        console.log(denuncia);
        this.denunciaService.cadastrarDenuncia(denuncia);
      }
    })
  }

}
