import { DatePipe } from '@angular/common';
import { Component, Input, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AdminService } from 'src/app/services/admin.service';
import { CategoriaService } from 'src/app/services/categoria.service';
import { DenunciaService } from 'src/app/services/denuncia.service';
import { UserService } from 'src/app/services/user.service';
import { environment } from 'src/environments/environment';
import { Categoria } from 'src/models/Categoria';
import { CustomAlerts } from 'src/models/CustomAlerts';
import { Denuncia } from 'src/models/dtos/Denuncia';
import { DenunciaResponse } from 'src/models/dtos/DenunciaResponse';
import { UsuarioDenuncia } from 'src/models/dtos/UsuarioDenuncia';
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

  usuarioAdm:Usuario = new Usuario();

  screenCategoria: boolean = false;
  screenUsuario: boolean = false;

  categorias: Categoria[] = [];
  denuncias: DenunciaResponse[] = [];
  botaoBanir:string='Banir';
  nomeImagem: string = ""

  categoriaForm = new FormGroup({
    nome: new FormControl(null, [Validators.required, Validators.minLength(1)]),
    imagemSrc: new FormControl(null, [Validators.required])
  });

  emailInvalido = false;
  nomeInvalido = false;
  senhaInvalida = false;
  telefoneInvalido = false;
  dataNascimentoInvalida = false;

  cadastroAdmForm= new FormGroup({
    email: new FormControl(null, [Validators.required, Validators.pattern("^[a-z0-9._-]+@[a-z0-9.-]+\\.[a-z]{2,4}$")]),
    nomeAdm: new FormControl(null, [Validators.required, Validators.minLength(1), Validators.pattern('^[a-zA-Zà-úÀ-Ú_ ]*$')]),
    senha: new FormControl(null, [Validators.required, Validators.minLength(8)]),
    repetirSenha: new FormControl(null, [Validators.required, Validators.minLength(8)]),
    dataNascimento: new FormControl(null, [Validators.required]),
    telefone: new FormControl(null,[Validators.required])
  });

  constructor(private denunciaService: DenunciaService, private datePipe: DatePipe, private userService: UserService, private adminService: AdminService, private router: Router, private categoriaService: CategoriaService) { }

  async ngOnInit(): Promise<void> {
    this.carregarDenuncias();

    this.loadCategories();
  }

  async carregarDenuncias(){
    this.denuncias = await this.denunciaService.getDenunciaPorQuatidade();
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
      this.nomeImagem = file.name

      this.categoriaForm.patchValue({
        imagemSrc: file
      });
    }
  }

  async handleDeletarCategoria(id: number) {
    let escolha = await CustomAlerts.primaryAlert.fire({
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
    if(this.categoriaForm.valid){

    let categoria: Categoria = await this.categoriaService.cadastrar(
      this.categoriaForm.value.nome, this.categoriaForm.get("imagemSrc")?.value
    );
    this.categorias.push(categoria);
    this.limparCampos();
    }else{
      CustomAlerts.primaryAlert.fire('Erro!', "Informe todos os campos!!!", 'error')
    }
  }
  

  limparCampos(){
    this.categoriaForm.get("imagemSrc")?.setValue("");
    this.categoriaForm.get("nome")?.setValue("");
    this.nomeImagem="";
  }

  logout() {
    localStorage.removeItem("accessToken");
    localStorage.removeItem("refreshToken");
    this.router.navigate(['/']);
    window.location.href = "/login";
  }
  converteData = (data: string) => {
    return this.datePipe.transform(data, 'dd/MM/yyyy') as string
  }
 async verInformacao(i: number) {

  CustomAlerts.primaryAlert.fire({
      title:await this.informacoesUsuario(i) ,
      showCancelButton: true,
      confirmButtonText: this.botaoBanir
    }).then(async (result) => {
      if (result.isConfirmed) {
        this.userService.banimentoUsuario(i);
        
        if(this.botaoBanir=="Banir"){
          CustomAlerts.primaryAlert.fire('Usuário banido!', '', 'success')
        }else{
          CustomAlerts.primaryAlert.fire('Usuário Desbanido!', '', 'success')
        }
        await this.carregarDenuncias();
        
        
      }
    })
  }

  informacoesUsuario = async (i:number) =>{
    
    let usuarioDenuncias:UsuarioDenuncia =await this.denunciaService.getDenunciasPorId(i);
    this.botaoBanir = usuarioDenuncias.cargo=="BANIDO"?"Desbanir":"Banir"
    let informacoes= "Nome: "+usuarioDenuncias.nome+
    "\nE-mail: "+usuarioDenuncias.email+
    "\nTelefone:"+usuarioDenuncias.telefone+
    "\nCargo: "+usuarioDenuncias.cargo+
    "\n\nDenúncias";

    for(let  d of usuarioDenuncias.denuncias){
      informacoes+="\n- "+d.descricao+" , "
      +(this.datePipe.transform(d.dataDenuncia, 'dd/MM/yyyy'));
      
    }
    return informacoes;
  }

  handleEditarImagem() {
    let file = document.getElementById('imagemFile');
    file?.click();
  }
  async cadastrarAdm() {
    try {
      this.validaEmail();
      this.validaNome();
      this.validaSenha();
      this.validaDataNascimento();
      this.validaTelefone();
    } catch (e:any) {
      CustomAlerts.primaryAlert.fire('Erro!', e.message, 'error')
    }
    if (this.cadastroAdmForm.valid) {
      await this.userService.cadastrarAdm(this.usuarioAdm);
      CustomAlerts.primaryAlert.fire({
        position: 'center',
        icon: 'success',
        title: 'Administrador Cadastrado!',
        showConfirmButton: false,
        timer: 1500
      })
      this.cadastroAdmForm.reset();
    }
  }

  cancelarAdm(){

  }
  validaEmail() {
    if (!this.cadastroAdmForm.get('email')?.valid) {
      this.emailInvalido = true;
      throw new Error("Email inválido!");
    }else{
      this.usuarioAdm.email = this.cadastroAdmForm.value.email;
      this.emailInvalido = false;
    }
  }
  validaNome() {
    if (!this.cadastroAdmForm.get('nomeAdm')?.valid) {
      this.nomeInvalido = true;
      throw new Error("Nome inválido!");
    }else{
      this.usuarioAdm.nome = this.cadastroAdmForm.value.nomeAdm;
      this.nomeInvalido = false;
    }
  }
  validaSenha() {
    if (!this.cadastroAdmForm.get('senha')?.valid) {
      this.senhaInvalida = true;
      throw new Error("Senha inválida!");
    }else if(this.cadastroAdmForm.get('senha')?.value!=this.cadastroAdmForm.get('repetirSenha')?.value){
      this.senhaInvalida = true;
      throw new Error("Senhas não conferem");
    }else{
      this.usuarioAdm.senha = this.cadastroAdmForm.value.senha;
      this.senhaInvalida=false;
    }

  }
  validaTelefone() {
    if (!this.cadastroAdmForm.get('telefone')?.valid) {
      this.telefoneInvalido=true;
      throw new Error("Telefone inválido!");
    }else{
      this.usuarioAdm.telefone = this.cadastroAdmForm.value.telefone;
      this.telefoneInvalido=false;
    }
  }
  validaDataNascimento() {
    if(this.datePipe.transform(this.cadastroAdmForm.get("dataNascimento")?.value, 'dd/MM/yyyy')==null){
      this.dataNascimentoInvalida=true;
      throw new Error("Informe a data");
    }else{
      this.usuarioAdm.dtNascimento = this.datePipe.transform(this.cadastroAdmForm.value.dataNascimento, 'dd/MM/yyyy') as string;
      this.dataNascimentoInvalida=false;
    }
  }

}

