import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';

import { NgxContextModule } from 'ngx-context';
import { AuthContextComponent } from './auth-context/auth-context.component';
import { ConsumerTestComponent } from './consumer-test/consumer-test.component';

@NgModule({
  declarations: [
    AppComponent,
    AuthContextComponent,
    ConsumerTestComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    NgxContextModule
  ],
  providers: [],
  bootstrap: [AppComponent],
})
export class AppModule { }
