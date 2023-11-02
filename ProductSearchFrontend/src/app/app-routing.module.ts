import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomepageComponent } from './components/homepage/homepage.component';
import { LoginComponent } from './components/login/login.component';
import { RegistrationComponent } from './components/registration/registration.component';
import { ResultsPageComponent } from './components/results-page/results-page.component';
import { ProductDetailsComponent } from './components/product-details/product-details.component';
import { AuthGuard } from './guards/auth.guard';
import { LoggedInAuthGuardGuard } from './guards/logged-in-auth-guard.guard';

const routes: Routes = [
  {
    path: "",
    component: HomepageComponent, 
    data: { isHomeRoute: true },    
  },
  {
    path: "login",
    component: LoginComponent,
    canActivate: [LoggedInAuthGuardGuard]
  },
  {
    path: "register",
    component: RegistrationComponent,
    canActivate: [LoggedInAuthGuardGuard]
  },
  {
    path: "results",
    component: ResultsPageComponent,
    // canActivate: [AuthGuard]
  },
  {
    path: "product/:id",
    component: ProductDetailsComponent,
    canActivate: [AuthGuard]
  }
];


@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
