import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AdminMainScreenComponent } from './screens/admin-main-screen/admin-main-screen.component';
import { CadastrarCategoriaComponent } from './screens/cadastrar-categoria/cadastrar-categoria.component';
import { LoginScreenComponent } from './screens/login-screen/login-screen.component';

const routes: Routes = [
  {path: 'login', component: LoginScreenComponent},
  {path: 'admin', component: AdminMainScreenComponent},
  {path: 'cadastrar-categoria', component: CadastrarCategoriaComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
