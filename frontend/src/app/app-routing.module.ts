import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AdminMainScreenComponent } from './screens/admin-main-screen/admin-main-screen.component';
import { CadastrarCategoriaComponent } from './screens/cadastrar-categoria/cadastrar-categoria.component';
import { CadastrarClienteComponent } from './screens/cadastrar-cliente/cadastrar-cliente.component';
import { CadastrarEnderecoComponent } from './screens/cadastrar-endereco/cadastrar-endereco.component';
import { CadastrarPrestadorComponent } from './screens/cadastrar-prestador/cadastrar-prestador.component';
import { EscolherTipoComponent } from './screens/escolher-tipo/escolher-tipo.component';
import { LoginScreenComponent } from './screens/login-screen/login-screen.component';

const routes: Routes = [
  {path: 'login', component: LoginScreenComponent},
  {path: 'admin', component: AdminMainScreenComponent},
  {path: 'cadastrar-categoria', component: CadastrarCategoriaComponent},
  {path: 'escolher-tipo', component: EscolherTipoComponent},
  {path: 'cadastrar-prestador', component: CadastrarPrestadorComponent},
  {path: 'cadastrar-cliente', component: CadastrarClienteComponent},
  {path: 'cadastrar-endereco', component: CadastrarEnderecoComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
