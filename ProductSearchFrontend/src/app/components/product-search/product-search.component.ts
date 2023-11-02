import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { Router } from '@angular/router';
import { AlertService } from 'src/app/services/alert.service';
import { SearchService } from 'src/app/services/search-service.service';

@Component({
  selector: 'app-product-search',
  templateUrl: './product-search.component.html',
  styleUrls: ['./product-search.component.css']
})
export class ProductSearchComponent implements OnInit {
  constructor(private formBuilder: FormBuilder, private searchService: SearchService, private router: Router, private _alert:AlertService) {}

  @Output() searchTriggered: EventEmitter<any> = new EventEmitter<any>();

  searchForm!: FormGroup;

  ngOnInit() {
    this.searchForm = this.formBuilder.group({
      name: [''],
      code: [''],
      brand: ['']
    });
   }


 


  onSubmit() {
    if (this.searchForm.valid) {

      const searchParams = {
        name: this.searchForm.value.name,
        code: this.searchForm.value.code ,
        brand: this.searchForm.value.brand 
      };
      this.router.navigate(['/results'], {queryParams: searchParams})
    }
  }
}
