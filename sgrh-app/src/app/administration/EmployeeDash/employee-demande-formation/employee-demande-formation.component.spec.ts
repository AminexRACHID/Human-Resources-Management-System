import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EmployeeDemandeFormationComponent } from './employee-demande-formation.component';

describe('EmployeeDemandeFormationComponent', () => {
  let component: EmployeeDemandeFormationComponent;
  let fixture: ComponentFixture<EmployeeDemandeFormationComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [EmployeeDemandeFormationComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(EmployeeDemandeFormationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
