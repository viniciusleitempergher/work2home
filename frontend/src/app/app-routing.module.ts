import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AdminMainScreenComponent } from './screens/admin-main-screen/admin-main-screen.component';
import { CadastrarCategoriaComponent } from './screens/cadastrar-categoria/cadastrar-categoria.component';
import { CadastrarClienteComponent } from './screens/cadastrar-cliente/cadastrar-cliente.component';
import { CadastrarEnderecoComponent } from './screens/cadastrar-endereco/cadastrar-endereco.component';
import { CadastrarPrestadorComponent } from './screens/cadastrar-prestador/cadastrar-prestador.component';
import { ClienteMainScreenComponent } from './screens/cliente-main-screen/cliente-main-screen.component';
import { EscolherTipoComponent } from './screens/escolher-tipo/escolher-tipo.component';
import { EsqueceuSenhaScreenComponent } from './screens/esqueceu-senha-screen/esqueceu-senha-screen.component';
import { LoginScreenComponent } from './screens/login-screen/login-screen.component';
import { PrestadorCategoriaScreenComponent } from './screens/prestador-categoria-screen/prestador-categoria-screen.component';
import { PrestadorCidadeScreenComponent } from './screens/prestador-cidade-screen/prestador-cidade-screen.component';
import { PrestadorMainScreenComponent } from './screens/prestador-main-screen/prestador-main-screen.component';

const routes: Routes = [
  {path: 'login', component: LoginScreenComponent},
  {path: 'admin', component: AdminMainScreenComponent},
  {path: 'cadastrar-categoria', component: CadastrarCategoriaComponent},
  {path: 'escolher-tipo', component: EscolherTipoComponent},
  {path: 'cadastrar-prestador', component: CadastrarPrestadorComponent},
  {path: 'cidade', component: PrestadorCidadeScreenComponent},
  {path: 'prestador-categoria', component: PrestadorCategoriaScreenComponent},
  {path: 'cadastrar-cliente', component: CadastrarClienteComponent},
  {path: 'cadastrar-endereco', component: CadastrarEnderecoComponent},
  {path: 'cliente', component: ClienteMainScreenComponent},
  {path: 'prestador', component: PrestadorMainScreenComponent},
  {path: 'esqueceu-senha', component: EsqueceuSenhaScreenComponent},
  {path: '', redirectTo: 'login', pathMatch: 'full'}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
