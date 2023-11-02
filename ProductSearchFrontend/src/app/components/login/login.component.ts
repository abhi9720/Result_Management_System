import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { AlertService } from 'src/app/services/alert.service';
import { AuthenticationService } from 'src/app/services/authentication.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  loginForm!: FormGroup;
  redirectURL: any;
  constructor(private formBuilder: FormBuilder, private authService:AuthenticationService,private _alert: AlertService,private route: ActivatedRoute) { }

  ngOnInit() {
    this.loginForm = this.formBuilder.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', Validators.required]
    });
  }

  get email() {
    return this.loginForm.get('email');
  }

  get password() {
    return this.loginForm.get('password');
  }

  onSubmit() {
    if (this.loginForm.invalid) {
      return;
    }

    const email = this.loginForm.value.email;
    const password = this.loginForm.value.password;

    const loginRequest =  {email,password}

    this.authService.loginUser(loginRequest).subscribe(
      (response)=>{
        console.log(response)
        this._alert.showSuccess("Login Successfully")
      },
      (error)=>{
        this._alert.showError(error.message);
        console.dir(error);
      }
    )
   
  }
}
