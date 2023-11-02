import { Component } from '@angular/core';
import { RouterLinkActive } from '@angular/router';
import { Router, NavigationEnd, ActivatedRoute } from '@angular/router';
import { AlertService } from 'src/app/services/alert.service';
import { AuthenticationService } from 'src/app/services/authentication.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent {

  navbarOpen = false;

  

  
constructor(private router: Router, private route: ActivatedRoute, private _alert : AlertService,
  private _authservice:AuthenticationService
  ) { }

isHomeActive: boolean = false;

ngOnInit() {
  this.router.events.subscribe((event) => {
    if (event instanceof NavigationEnd) {
      const isHomeRoute = this.route.root.firstChild?.snapshot.data['isHomeRoute'];
      this.isHomeActive = (event.url === '/' && isHomeRoute);
    }
  });
}

toggleNavbar() {  
  this.navbarOpen = !this.navbarOpen;
}

isLoggedIn(){
  return this._authservice.isAuthenticated();
}

logoutUser() {
  this._alert.showSuccess("Logout Sucessfully")
  this._authservice.logOutUser();
}
}
