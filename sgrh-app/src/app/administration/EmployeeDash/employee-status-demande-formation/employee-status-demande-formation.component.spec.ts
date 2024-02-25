import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EmployeeStatusDemandeFormationComponent } from './employee-status-demande-formation.component';

describe('EmployeeStatusDemandeFormationComponent', () => {
  let component: EmployeeStatusDemandeFormationComponent;
  let fixture: ComponentFixture<EmployeeStatusDemandeFormationComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [EmployeeStatusDemandeFormationComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(EmployeeStatusDemandeFormationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
