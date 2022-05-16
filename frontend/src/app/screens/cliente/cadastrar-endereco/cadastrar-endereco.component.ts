import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { ClienteService } from 'src/app/services/cliente.service';
import { EnderecoApiService } from 'src/app/services/endereco-api.service';
import { EnderecoService } from 'src/app/services/endereco.service';
import { UserService } from 'src/app/services/user.service';
import { Cidade } from 'src/models/Cidade';
import { Cliente } from 'src/models/Cliente';
import { CustomAlerts } from 'src/models/CustomAlerts';
import { Endereco } from 'src/models/Endereco';
import { Estado } from 'src/models/Estado';
import { Usuario } from 'src/models/Usuario';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-cadastrar-endereco',
  templateUrl: './cadastrar-endereco.component.html',
  styleUrls: ['./cadastrar-endereco.component.css']
})
export class CadastrarEnderecoComponent implements OnInit {

  user: Usuario = new Usuario();
  cliente:Cliente = new Cliente();
  estado: Estado = new Estado();
  estados: Estado[] = [];
  cidades: Cidade[] = [];
  endereco: Endereco = new Endereco();

  bairroInvalida = false;
  estadoInvalida = false;
  cidadeInvalida = false;
  ruaInvalida = false;
  numeroInvalida = false;
  complementoInvalida = false;

  btnConfirmar:string='Cadastrar';

  cadastroEnderecoForm = new FormGroup({
    cbxEstado: new FormControl(null, Validators.required),
    cbxCidade: new FormControl(null, Validators.required),
    bairro: new FormControl(null, Validators.required),
    logradouro: new FormControl(null, Validators.required),
    numero: new FormControl(null, [Validators.required, Validators.pattern(/^-?(0|[1-9]\d*)?$/)]),
    complemento: new FormControl(null, Validators.required)
  });


  constructor(private clienteService:ClienteService,private endercoService: EnderecoService, private usuarioServico: UserService, private serviceEnderecoApi: EnderecoApiService, private enderecoService: EnderecoService, private router: Router) { }

  async ngOnInit(): Promise<void> {
    this.user = await this.usuarioServico.getUserFromAccessToken();
    this.cliente = await this.clienteService.getCliente(this.user.id);
    this.limparCombo();
    this.obterEstados();
    this.obterCidades("");
    try {
      this.endereco = await this.endercoService.getEndereco(this.user.id);
      this.carregarCampos();
    } catch (error) {
    }
  }
  cancelar() {
    this.router.navigate(['login']);
  }
  async cadastrar() {
    try {
      this.validaCbxEstado();
      this.validaCbxCidade();
      this.validaBairro();
      this.validaRua();
      this.validaNumero();
      this.validaComplemento();
    } catch (e: any) {
      CustomAlerts.primaryAlert.fire('Erro!', e.message, 'error')
    }
    if (this.cadastroEnderecoForm.valid) {
      await this.enderecoService.cadastrarEndereco(this.endereco);
      CustomAlerts.primaryAlert.fire({
        position: 'center',
        icon: 'success',
        title: 'Endereço atualizado!',
        showConfirmButton: false,
        timer: 1500
      })
      this.router.navigate(['cliente']);
    }
  }
  async carregarCampos() {
    this.btnConfirmar="Atualizar";
    this.cadastroEnderecoForm.get("cbxEstado")?.setValue(this.cliente.estado);
    await this.obterCidades(this.cadastroEnderecoForm.value.cbxEstado);
    this.cadastroEnderecoForm.get("cbxCidade")?.setValue(this.cliente.cidade);
    this.cadastroEnderecoForm.get("bairro")?.setValue(this.endereco.bairro);
    this.cadastroEnderecoForm.get("logradouro")?.setValue(this.endereco.logradouro);
    this.cadastroEnderecoForm.get("numero")?.setValue(this.endereco.numero);
    this.cadastroEnderecoForm.get("complemento")?.setValue(this.endereco.complemento);
    }
  obterEstados = () => {
    this.serviceEnderecoApi.listarEstados()
      .subscribe(retorno => this.estados = retorno);
  }
  selecaoEstado = () => {
    this.obterCidades(this.cadastroEnderecoForm.value.cbxEstado);
    this.cadastroEnderecoForm.get('cbxCidade')?.setValue("");
    this.validaCbxEstado()
  }
  obterCidades = (sigla: string) => {
    return new Promise((resolve, reject) => {
      this.serviceEnderecoApi.listarCidades(sigla)
        .subscribe(retorno => {
          this.cidades = retorno;
          resolve(retorno);
        });
    })

  }
  validaCbxEstado = () => {
    if (this.cadastroEnderecoForm.value.cbxEstado == '') {
      this.estadoInvalida = true;
      throw new Error("Escolha um Estado!");
    } else {
      this.endereco.estado = this.cadastroEnderecoForm.value.cbxEstado;
      this.estadoInvalida = false;
    }
  }
  validaCbxCidade = () => {
    if (this.cadastroEnderecoForm.value.cbxCidade == '') {
      this.cidadeInvalida = true;
      throw new Error("Escolha um Cidade");
    } else {
      this.endereco.cidade = this.cadastroEnderecoForm.value.cbxCidade;
      this.cidadeInvalida = false;
    }
  }
  validaBairro() {
    if (!this.cadastroEnderecoForm.get('bairro')?.valid) {
      this.bairroInvalida = true;
      throw new Error("Bairro inválido!!!");
    } else {
      this.endereco.bairro = this.cadastroEnderecoForm.value.bairro;
      this.bairroInvalida = false;

    }
  }
  validaRua() {
    if (!this.cadastroEnderecoForm.get('logradouro')?.valid) {
      this.ruaInvalida = true;
      throw new Error("Rua inválida!!!");
    } else {
      this.endereco.logradouro = this.cadastroEnderecoForm.value.logradouro;
      this.ruaInvalida = false;
    }
  }
  validaNumero() {
    if (!this.cadastroEnderecoForm.get('numero')?.valid) {
      this.numeroInvalida = true;
      throw new Error("Numero inválido");
    } else {
      this.endereco.numero = this.cadastroEnderecoForm.value.numero;
      this.numeroInvalida = false;
    }
  }
  validaComplemento() {
    if (!this.cadastroEnderecoForm.get('complemento')?.valid) {
      this.complementoInvalida = true;
      throw new Error("Complemento inválido");
    } else {
      this.endereco.complemento = this.cadastroEnderecoForm.value.complemento;
      this.complementoInvalida = false;
    }
  }


  limparCombo() {
    this.cadastroEnderecoForm.reset();
    this.cadastroEnderecoForm.get("cbxEstado")?.setValue("");
    this.cadastroEnderecoForm.get("cbxCidade")?.setValue("");

  }

}
