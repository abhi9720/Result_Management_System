import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HomepageComponent } from './components/homepage/homepage.component';
import { LoginComponent } from './components/login/login.component';
import { RegistrationComponent } from './components/registration/registration.component';
import { ProductSearchComponent } from './components/product-search/product-search.component';
import { ResultsPageComponent } from './components/results-page/results-page.component';
import { ProductDetailsComponent } from './components/product-details/product-details.component';
import { LogoutComponent } from './components/logout/logout.component';
import { HeaderComponent } from './common/header/header.component';
import { FooterComponent } from './common/footer/footer.component';
import { AlertComponent } from './components/alert/alert.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { BackButtonComponent } from './common/back-button/back-button.component';
import {HTTP_INTERCEPTORS, HttpClientModule} from '@angular/common/http';
import { AuthInterceptor } from './auth.interceptor';
import { AlertService } from './services/alert.service';

@NgModule({
  declarations: [
    AppComponent,
    HomepageComponent,
    LoginComponent,
    RegistrationComponent,
    ProductSearchComponent,
    ResultsPageComponent,
    ProductDetailsComponent,
    LogoutComponent,
    HeaderComponent,
    FooterComponent,
    AlertComponent,
    BackButtonComponent
    
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    NgbModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule
  ],
  providers: [  { provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true }, AlertService],
  bootstrap: [AppComponent]
})
export class AppModule { }
