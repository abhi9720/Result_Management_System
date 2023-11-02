import { Injectable } from '@angular/core';
import { HttpInterceptor, HttpRequest, HttpHandler, HttpEvent } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { Router } from '@angular/router';
import { AlertService } from './services/alert.service';


@Injectable()
export class AuthInterceptor implements HttpInterceptor {
  constructor(private _router: Router, private _alert : AlertService) {}

  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    const token = localStorage.getItem('token');

    if (token) {
      // Clone the request and add the token to the headers
      const clonedRequest = request.clone({
        setHeaders: {
          Authorization: `Bearer ${token}`
        }
      });

      // Pass the cloned request with the token to the next handler
      return next.handle(clonedRequest).pipe(
        catchError((error: any) => {
          if (error.status === 401) {
            this._alert.showError("Unauthorized! Access Denied")
            // Token is invalid, remove token from local storage
            localStorage.removeItem('token');
            this._router.navigate(['login']);
            return throwError(() => new Error('Unauthorized'));
          }
          return throwError(() => error);
        })
      );
    } else {
      // If there is no token, pass the original request to the next handler
      return next.handle(request);
    }
  }
}
