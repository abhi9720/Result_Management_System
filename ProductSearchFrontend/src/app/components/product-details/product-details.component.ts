import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ProductService } from 'src/app/services/product.service';

@Component({
  selector: 'app-product-details',
  templateUrl: './product-details.component.html',
  styleUrls: ['./product-details.component.css']
})
export class ProductDetailsComponent {
  product: any; // Placeholder for product details
  pincode: string = ''; 
  serviceMessage: string = ''; // Placeholder for serviceability message

  constructor(private route: ActivatedRoute, private productService: ProductService) { }

  ngOnInit() {
    // Fetch the product details from API based on route parameter or use a service to retrieve the data
    const productId = this.route.snapshot.paramMap.get('id');
    if (productId !== null) {
      // Make API call or use a service to fetch the product details based on the productId
      this.product = this.getProductDetails(productId);
    }
  }

  getImage(data: any) {
    let imagebasr64 = 'data:image/jpeg;base64,' + data;
    return imagebasr64;
  }

  checkServiceability() {
    this.productService.getProductAvailability(this.product.productCode, this.pincode).subscribe(
      (data: any) => {
        if (data.serviceable) {
          this.serviceMessage = `Product can be delivered to pincode ${this.pincode}. Expected delivery time: ${data.deliveryTime}.`;
        }
        else {
          this.serviceMessage = `Product can not be delivered to pincode ${this.pincode}`;

        }
      },
      (error: any) => {
        if (error.status === 404 && error.error.message) {
          this.serviceMessage = error.error.message
        }
        console.error('Error:', error);
        // Handle error here, e.g., display an error message to the user
      }
    );
  }

  getProductDetails(productId: string) {
    this.productService.getProductDetails(productId).subscribe(
      (data: any) => {
        this.product = data;
      },
      (error: any) => {
        console.error('Error:', error);
      }
    );
  }

}



