import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';

import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { NgxMaskModule } from 'ngx-mask';
import { HTTP_INTERCEPTORS } from '@angular/common/http';
import { AddTokenInterceptor } from './interceptors/AddTokenInterceptor';
import { AdminMainScreenComponent } from './screens/admin/admin-main-screen/admin-main-screen.component';
import { CadastrarCategoriaComponent } from './screens/admin/cadastrar-categoria/cadastrar-categoria.component';
import { EscolherTipoComponent } from './screens/auth/escolher-tipo/escolher-tipo.component';
import { EsqueceuSenhaScreenComponent } from './screens/auth/esqueceu-senha-screen/esqueceu-senha-screen.component';
import { LoginScreenComponent } from './screens/auth/login-screen/login-screen.component';
import { CadastrarClienteComponent } from './screens/cliente/cadastrar-cliente/cadastrar-cliente.component';
import { CadastrarEnderecoComponent } from './screens/cliente/cadastrar-endereco/cadastrar-endereco.component';
import { ClienteMainScreenComponent } from './screens/cliente/cliente-main-screen/cliente-main-screen.component';
import { CadastrarPrestadorComponent } from './screens/prestador/cadastrar-prestador/cadastrar-prestador.component';
import { PrestadorCategoriaScreenComponent } from './screens/prestador/prestador-categoria-screen/prestador-categoria-screen.component';
import { PrestadorCidadeScreenComponent } from './screens/prestador/prestador-cidade-screen/prestador-cidade-screen.component';
import { PrestadorMainScreenComponent } from './screens/prestador/prestador-main-screen/prestador-main-screen.component';

@NgModule({
  
  declarations: [
    AppComponent,
    LoginScreenComponent,
    AdminMainScreenComponent,
    CadastrarCategoriaComponent,
    EscolherTipoComponent,
    CadastrarPrestadorComponent,
    CadastrarClienteComponent,
    CadastrarEnderecoComponent,
    ClienteMainScreenComponent,
    PrestadorMainScreenComponent,
    EsqueceuSenhaScreenComponent,
    PrestadorCategoriaScreenComponent,
    PrestadorCidadeScreenComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule,
    NgxMaskModule.forRoot()
  ],
  providers: [{
    provide: HTTP_INTERCEPTORS,
    useClass: AddTokenInterceptor,
    multi: true,
  }],
  bootstrap: [AppComponent],
})
export class AppModule { }
