import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { catchError, tap, throwError } from 'rxjs';
import jwt_decode from "jwt-decode";

@Injectable({
  providedIn: 'root'
})

export class AuthenticationService {

  constructor(private _http: HttpClient, private router: Router) { }

  baseurl = "http://localhost:8180";
  authkey = "Authtoken";
  redirectUrl: string | null = null;


  loginUser(loginRequest: any) {
    const url = `${this.baseurl}/authenticate`;
    return this._http.post<any>(url, loginRequest).pipe(
      tap((response) => {
        this.saveToken(this.authkey,response.token)
        const redirectUrl = this.redirectUrl || '/';
        this.redirectUrl = null;
        this.router.navigateByUrl(redirectUrl);
      }),
      catchError((error: HttpErrorResponse) => {
        if (error.status === 401) {
          return throwError(() => new Error('Invalid email or password'));
        } else {
          return throwError(() => new Error('Something Went Wrong'));
        }
      })
    )
  }

  isAuthenticated(): boolean {
    const token = this.getToken(this.authkey);
    if (token) {
      try {
        // Decode the JWT token
        const decodedToken:any = jwt_decode(token);
  
        // Check if the token is valid and not expired
        if (decodedToken && decodedToken.exp && decodedToken.exp * 1000 > Date.now()) {
          // Token is valid and not expired
          return true;
        }
      } catch (error) {
        
        console.error('Error decoding JWT token:', error);
      }
    }

     
    return false;
  }


  logOutUser() {
    this.removeToken(this.authkey)
    this.router.navigate(['/login']);
  }

  saveToken(key:string, token: string) {
    localStorage.setItem(key, token);
  }

  getToken(key : string) {
    const token = localStorage.getItem(key);
    return token; // or return any other default value as per your requirement
  }

  removeToken(key:string) {
    console.log("Token removec");
    
    localStorage.removeItem(key)
  }
}
