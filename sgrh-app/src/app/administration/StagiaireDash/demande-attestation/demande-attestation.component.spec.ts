import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DemandeAttestationComponent } from './demande-attestation.component';

describe('DemandeAttestationComponent', () => {
  let component: DemandeAttestationComponent;
  let fixture: ComponentFixture<DemandeAttestationComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [DemandeAttestationComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(DemandeAttestationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
