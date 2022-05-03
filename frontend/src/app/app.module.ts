import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';

import { NgxContextModule } from 'ngx-context';
import { AuthContextComponent } from './auth-context/auth-context.component';
import { LoginScreenComponent } from './screens/login-screen/login-screen.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { NgxMaskModule } from 'ngx-mask';
import { HTTP_INTERCEPTORS } from '@angular/common/http';
import { AddTokenInterceptor } from './interceptors/AddTokenInterceptor';
import { AdminMainScreenComponent } from './screens/admin-main-screen/admin-main-screen.component';
import { CadastrarCategoriaComponent } from './screens/cadastrar-categoria/cadastrar-categoria.component';
import { EscolherTipoComponent } from './screens/escolher-tipo/escolher-tipo.component';
import { CadastrarPrestadorComponent } from './screens/cadastrar-prestador/cadastrar-prestador.component';
import { CadastrarClienteComponent } from './screens/cadastrar-cliente/cadastrar-cliente.component';
import { CadastrarEnderecoComponent } from './screens/cadastrar-endereco/cadastrar-endereco.component';
import { ClienteMainScreenComponent } from './screens/cliente-main-screen/cliente-main-screen.component';
import { PrestadorMainScreenComponent } from './screens/prestador-main-screen/prestador-main-screen.component';
import { PrestadorCategoriaScreenComponent } from './screens/prestador-categoria-screen/prestador-categoria-screen.component';
import { PrestadorCidadeScreenComponent } from './screens/prestador-cidade-screen/prestador-cidade-screen.component';

@NgModule({
  
  declarations: [
    AppComponent,
    AuthContextComponent,
    LoginScreenComponent,
    AdminMainScreenComponent,
    CadastrarCategoriaComponent,
    EscolherTipoComponent,
    CadastrarPrestadorComponent,
    CadastrarClienteComponent,
    CadastrarEnderecoComponent,
    ClienteMainScreenComponent,
    PrestadorMainScreenComponent,
    PrestadorCategoriaScreenComponent,
    PrestadorCidadeScreenComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    NgxContextModule,
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
