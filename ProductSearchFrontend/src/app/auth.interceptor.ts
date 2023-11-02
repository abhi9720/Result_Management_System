import { Injectable } from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor
} from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { Router } from '@angular/router';
import { AlertService } from './services/alert.service';
import { AuthenticationService } from './services/authentication.service';

@Injectable()
export class AuthInterceptor implements HttpInterceptor {
  constructor(
    private _router: Router,
    private _alert: AlertService,
    private _authservice: AuthenticationService
  ) {}

  intercept(
    request: HttpRequest<any>,
    next: HttpHandler
  ): Observable<HttpEvent<any>> {
    const token = this._authservice.getToken('Authtoken');

    if (token) {
      // Clone the request and add the token to the headers
      const clonedRequest = request.clone({
        setHeaders: {
          Authorization: `Bearer ${token}`,
          'Access-Control-Allow-Origin': 'http://localhost:4200' 
        }
      });

      // Pass the cloned request with the token to the next handler
      return next.handle(clonedRequest).pipe(
        catchError((error: any) => {
          console.log(error.status);

          if (error.status === 401) {
            this._alert.showError('Unauthorized! Access Denied');
            // Token is invalid, remove token from local storage
            this._authservice.logOutUser();

            return throwError(() => new Error('Unauthorized'));
          } else {
            return throwError(() => error);
          }
        })
      );
    } else {
      // If there is no token, pass the original request to the next handler
      return next.handle(request);
    }
  }
}
