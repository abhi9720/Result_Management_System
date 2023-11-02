import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BackButtonComponentComponent } from './back-button.component';

describe('BackButtonComponentComponent', () => {
  let component: BackButtonComponentComponent;
  let fixture: ComponentFixture<BackButtonComponentComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ BackButtonComponentComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(BackButtonComponentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
