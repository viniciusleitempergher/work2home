import { DatePipe } from '@angular/common';
import { Component, Input, OnInit } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { Router } from '@angular/router';
import { AdminService } from 'src/app/services/admin.service';
import { CategoriaService } from 'src/app/services/categoria.service';
import { DenunciaService } from 'src/app/services/denuncia.service';
import { UserService } from 'src/app/services/user.service';
import { environment } from 'src/environments/environment';
import { Categoria } from 'src/models/Categoria';
import { Denuncia } from 'src/models/dtos/Denuncia';
import { DenunciaResponse } from 'src/models/dtos/DenunciaResponse';
import { Usuario } from 'src/models/Usuario';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-admin-main-screen',
  templateUrl: './admin-main-screen.component.html',
  styleUrls: ['./admin-main-screen.component.css'],
  providers: [DatePipe] //DatePipe como provider
})
export class AdminMainScreenComponent implements OnInit {
  environment = environment;

  screenCategoria: boolean = false;
  screenUsuario: boolean = false;

  categorias: Categoria[] = [];
  usuarios: Usuario[] = [];
  denuncias: DenunciaResponse[] = [];
  botaoBanir:string='Banir';

  categoriaForm = new FormGroup({
    nome: new FormControl(),
    imagem: new FormControl(),
    imagemSrc: new FormControl()
  });

  constructor(private denunciaService: DenunciaService, private datePipe: DatePipe, private userService: UserService, private adminService: AdminService, private router: Router, private categoriaService: CategoriaService) { }

  async ngOnInit(): Promise<void> {
    this.denuncias = await this.denunciaService.getDenunciaPorQuatidade();
    this.usuarios = await this.userService.listarUsuarios();

    this.loadCategories();
  }

  redirectCategoriasPage() {
    this.router.navigate(["cadastrar-categoria"]);
  }

  async relatorioUsuario() {
    const fileURL = URL.createObjectURL(await this.adminService.relatorioUsuario());
    window.open(fileURL);
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

      for (let i = 0; i < this.categorias.length; i++) {
        if (this.categorias[i].id == id) {
          this.categorias.splice(i, 1);
        }
      }
    }
  }

  async handleAddCategoria() {
    let categoria: Categoria = await this.categoriaService.cadastrar(
      this.categoriaForm.value.nome, this.categoriaForm.get("imagemSrc")?.value
    );


    this.categorias.push(categoria);
  }


  logout() {
    localStorage.clear();
    this.router.navigate(['/']);
  }
  converteData = (data: string) => {
    return this.datePipe.transform(data, 'dd/MM/yyyy') as string
  }
 async verInformacao(i: number) {
  let usuariosDenunciado:Usuario = await this.userService.buscarUsuarioId(i);
    
    const swalWithBootstrapButtons = Swal.mixin({
      customClass: {
        title: 'tituloBanir',
        popup: 'popus',
        input: 'inputs',
        confirmButton: 'botaoDenuncia',
        cancelButton: 'botaoCancela'
      },
      buttonsStyling: false
    })

    swalWithBootstrapButtons.fire({
      title:await this.informacoesUsuario(usuariosDenunciado) ,
      showCancelButton: true,
      confirmButtonText: this.botaoBanir
    }).then((result) => {
      if (result.isConfirmed) {
        this.userService.banimentoUsuario(usuariosDenunciado.id);
        if(this.botaoBanir=="Banir"){
          Swal.fire('Usuário banido!', '', 'success')
        }else{
          Swal.fire('Usuário Desbanido!', '', 'success')
        }
        
      }
    })
  }

  informacoesUsuario = async (usuariosDenuciado:Usuario) =>{
    
    let denuncias:Denuncia[] =await this.denunciaService.getDenunciasPorId(usuariosDenuciado.id);
    this.botaoBanir = usuariosDenuciado.role=="BANIDO"?"Desbanir":"Banir"
    let informacoes= "Nome: "+usuariosDenuciado.nome+
    "\nE-mail: "+usuariosDenuciado.email+
    "\nTelefone:"+usuariosDenuciado.telefone+
    "\nCargo: "+usuariosDenuciado.role+
    "\n\nDenúncias";

    for(let  d of denuncias){
      informacoes+="\n- "+d.descricao;
    }
    return informacoes;
  }

}

