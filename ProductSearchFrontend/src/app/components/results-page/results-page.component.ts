import { Component, Input } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { AlertService } from 'src/app/services/alert.service';
import { AuthenticationService } from 'src/app/services/authentication.service';
import { SearchService } from 'src/app/services/search-service.service';

@Component({
  selector: 'app-results-page',
  templateUrl: './results-page.component.html',
  styleUrls: ['./results-page.component.css']
})
export class ResultsPageComponent {

  isLoggedIn: boolean = true; // Placeholder for login status
  brands: string[] = []; // Placeholder for available brands
  selectedBrand: string = ''; // Placeholder for selected brand filter
  minPrice: number = Infinity; // Placeholder for minimum price filter
  maxPrice: number = 0; // Placeholder for maximum price filter
  searchResults: any = []; // Placeholder for search results
  notResultMsg = ""
  noMatchingProduct = ""

  constructor(private searchService: SearchService, private _alert: AlertService, private authService: AuthenticationService, private route: ActivatedRoute) { }

  ngOnInit() {
    this.isLoggedIn = this.authService.isAuthenticated();
    this.route.queryParams.subscribe(params => {
      this.performSearch(params)
    });
  }

  performSearch(searchParams: any) {
    this.searchService.searchProducts(searchParams).subscribe(
      (response: any) => {
        if (!response) {
          this.notResultMsg = "No Result Found";
          this._alert.showError("No Matching Product Found")
        } else {
          this.searchResults = response;
          this.notResultMsg = ''
          this.processproductSearchResult(this.searchResults)
        }

      },
      (error) => {
        console.log(error);
        this.notResultMsg = 'Something Went Wrong'
        this._alert.showError(error.message || 'Something Went Wrong');
      }
    )
  }


  getImage(data: any) {
    let imagebasr64 = 'data:image/jpeg;base64,' + data;
    return imagebasr64;
  }



  filterByPriceRange() {

    // Convert the minPrice and maxPrice to numbers
    const minPriceValue = +this.minPrice;
    const maxPriceValue = +this.maxPrice;

    // Check if the minPrice and maxPrice are valid numbers
    if (!isNaN(minPriceValue) && !isNaN(maxPriceValue)) {
      // Filter the search results based on the price range
      this.searchResults = this.searchService.getSearchResults().filter((product: any) => {
        return product.price >= minPriceValue && product.price <= maxPriceValue;
      });

      // Check if any search results are found
      if (this.searchResults.length === 0) {
        this.noMatchingProduct = "No Matching Product Found";
      } else {
        this.noMatchingProduct = "";
      }
    } else {
      // Reset the search results and display an error message
      this.searchResults = [];
      this.notResultMsg = "Invalid price range. Please enter valid numbers.";
    }
  }


  filterByBrand() {

    // Check if a brand filter is selected
    if (this.selectedBrand) {
      // Filter the search results based on the selected brand
      this.searchResults = this.searchService.getSearchResults().filter((product: any) => {
        return product.brand.toLowerCase() === this.selectedBrand.toLowerCase();
      });

      // Check if any search results are found
      if (this.searchResults.length === 0) {
        this.noMatchingProduct = "No Matching Product Found";
      } else {
        this.notResultMsg = "";
      }
    } else {
      // Reset the brand filter and display all search results
      this.searchResults = this.searchService.getSearchResults();
      this.notResultMsg = "";
    }
  }


  processproductSearchResult(productSearchResult: any) {
    this.brands = []; // Clear the brands array before populating it

    for (let product of productSearchResult) {
      this.minPrice = Math.min(this.minPrice, product.price);
      this.maxPrice = Math.max(this.maxPrice, product?.price);
      this.brands.push(product.brand);
    }

    this.brands = [...new Set(this.brands)];

  }
}


