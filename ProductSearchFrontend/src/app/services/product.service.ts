import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class ProductService {
  private apiUrl = 'http://localhost:8180';

  constructor(private http: HttpClient) { }

  getProductDetails(productId: string) {
    const url = `${this.apiUrl}/products/${productId}`;
    return this.http.get(url);
  }

  getProductAvailability(productCode: string, pincode: string) {
    const url = `${this.apiUrl}/product-available`;
    const params = new HttpParams()
      .set('productCode', productCode)
      .set('pincode', pincode);

    return this.http.get(url, { params });
  }
}
