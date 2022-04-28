import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';

import { NgxContextModule } from 'ngx-context';
import { AuthContextComponent } from './auth-context/auth-context.component';
import { ConsumerTestComponent } from './consumer-test/consumer-test.component';
import { LoginScreenComponent } from './screens/login-screen/login-screen.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { NgxMaskModule } from 'ngx-mask';
import { HTTP_INTERCEPTORS } from '@angular/common/http';
import { AddTokenInterceptor } from './interceptors/AddTokenInterceptor';

@NgModule({
  
  declarations: [
    AppComponent,
    AuthContextComponent,
    ConsumerTestComponent,
    LoginScreenComponent
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
