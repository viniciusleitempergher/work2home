import { OrcamentoComponent } from './orcamento/orcamento.component';
import { AvaliacaoComponent } from './avaliacao/avaliacao.component';
import { OrdemServicoComponent } from './ordem-servico/ordem-servico.component';
import { FazerSolicitacaoComponent } from './fazer-solicitacao/fazer-solicitacao.component';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AdminMainScreenComponent } from './screens/admin/admin-main-screen/admin-main-screen.component';
import { AlterarSenhaScreenComponent } from './screens/auth/alterar-senha-screen/alterar-senha-screen.component';
import { EscolherTipoComponent } from './screens/auth/escolher-tipo/escolher-tipo.component';
import { EsqueceuSenhaScreenComponent } from './screens/auth/esqueceu-senha-screen/esqueceu-senha-screen.component';
import { LoginScreenComponent } from './screens/auth/login-screen/login-screen.component';
import { CadastrarClienteComponent } from './screens/cliente/cadastrar-cliente/cadastrar-cliente.component';
import { CadastrarEnderecoComponent } from './screens/cliente/cadastrar-endereco/cadastrar-endereco.component';
import { ClienteMainScreenComponent } from './screens/cliente/cliente-main-screen/cliente-main-screen.component';
import { PerfilUsuarioComponent } from './screens/perfil-usuario/perfil-usuario.component';
import { CadastrarPrestadorComponent } from './screens/prestador/cadastrar-prestador/cadastrar-prestador.component';
import { PrestadorCategoriaScreenComponent } from './screens/prestador/prestador-categoria-screen/prestador-categoria-screen.component';
import { PrestadorCidadeScreenComponent } from './screens/prestador/prestador-cidade-screen/prestador-cidade-screen.component';
import { PrestadorMainScreenComponent } from './screens/prestador/prestador-main-screen/prestador-main-screen.component';
import { AlterarClienteScreenComponent } from './screens/cliente/alterar-cliente-screen/alterar-cliente-screen.component';
import { AlterarPrestadorScreenComponent } from './screens/prestador/alterar-prestador-screen/alterar-prestador-screen.component';
import { ChangeThemeComponent } from './screens/change-theme/change-theme.component';
import { ChatComponent } from './screens/chat/chat.component';

const routes: Routes = [
  {path: 'login', component: LoginScreenComponent},
  {path: 'admin', component: AdminMainScreenComponent},
  {path: 'escolher-tipo', component: EscolherTipoComponent},
  {path: 'cadastrar-prestador', component: CadastrarPrestadorComponent},
  {path: 'cidade', component: PrestadorCidadeScreenComponent},
  {path: 'prestador-categoria', component: PrestadorCategoriaScreenComponent},
  {path: 'cadastrar-cliente', component: CadastrarClienteComponent},
  {path: 'cadastrar-endereco', component: CadastrarEnderecoComponent},
  {path: 'cliente', component: ClienteMainScreenComponent},
  {path: 'prestador', component: PrestadorMainScreenComponent},
  {path: 'prestador/alterar', component: AlterarPrestadorScreenComponent},
  {path: 'perfil/:usuarioId', component: PerfilUsuarioComponent},
  {path: 'esqueceu-senha', component: EsqueceuSenhaScreenComponent},
  {path: 'alterar-senha/:accessToken', component: AlterarSenhaScreenComponent},
  {path: 'cliente/fazer-solicitacao/:categoriaId', component: FazerSolicitacaoComponent},
  {path: 'cliente/alterar', component: AlterarClienteScreenComponent},
  {path: 'ordem-servico/:id', component: OrdemServicoComponent},
  {path: 'ordem-servico/:id/avaliacao', component: AvaliacaoComponent},
  {path: 'ordem-servico/:id/orcamento', component: OrcamentoComponent},
  {path: 'tema', component: ChangeThemeComponent},
  {path: 'chat/:usuarioId', component: ChatComponent},
  {path: '', redirectTo: 'login', pathMatch: 'full'},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
