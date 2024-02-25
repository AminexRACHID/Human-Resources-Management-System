import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EmployeeDemandeAbsencesComponent } from './employee-demande-absences.component';

describe('EmployeeDemandeAbsencesComponent', () => {
  let component: EmployeeDemandeAbsencesComponent;
  let fixture: ComponentFixture<EmployeeDemandeAbsencesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [EmployeeDemandeAbsencesComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(EmployeeDemandeAbsencesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
