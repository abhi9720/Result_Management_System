import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { catchError, tap, throwError } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class SearchService {

  constructor(private http: HttpClient) { }
  baseurl = "http://localhost:8180";

  private searchResults: any;

  private searchParams: any = {
    name: '',
    code: '',
    brand: ''
  };

  setSearchResults(data: any) {
    this.searchResults = data;
  }

  getSearchResults() {
    return this.searchResults;
  }


  setSearchParams(data: any) {
    this.searchParams = data;
  }

  getSearchParams() {
    return this.searchParams;
  }

  searchProducts(searchParams:any) {

    const params = {
      name: searchParams.name || '',
      brand:searchParams.brand || '',
      productCode: searchParams.code || ''
    };

    return this.http.get(`${this.baseurl}/products`, { params }).pipe(
      tap((response) => {
         this.setSearchResults(response)
        this.setSearchParams(params)
      }),
      catchError((error: HttpErrorResponse) => {
        console.log(error);
        
        return throwError(() => new Error(error.error.message));
      })
    )
  }
}
