import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { catchError, tap, throwError } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class RegistrationService {

  constructor(private _http: HttpClient,private router: Router) { }

  baseurl = "http://localhost:8180";
  authkey = "Authtoken";


  registerUser(data:any){

    return this._http.post<any>(`${this.baseurl}/register`,data).pipe(
      tap((response)=>{
        this.saveToken(this.authkey,response.token)
        localStorage.setItem('currentUser', JSON.stringify(response.user));
        this.router.navigate(['']);
      }),
      catchError((error: HttpErrorResponse) => {
        if (error.status === 409 ) {
          return throwError(() => new Error(error.error.message));
        }
        else{
          console.log(error)
          return throwError(()=>new Error("Something Went Wrong "))
        }
      
      })
    )
  }
  
  saveToken(key:string, token: string) {
    localStorage.setItem(key, token);
  }
  
}
