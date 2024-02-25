import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CarteStagiaireComponent } from './carte-stagiaire.component';

describe('CarteStagiaireComponent', () => {
  let component: CarteStagiaireComponent;
  let fixture: ComponentFixture<CarteStagiaireComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [CarteStagiaireComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(CarteStagiaireComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
