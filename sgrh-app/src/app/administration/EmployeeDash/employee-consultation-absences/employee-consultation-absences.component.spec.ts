import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EmployeeConsultationAbsencesComponent } from './employee-consultation-absences.component';

describe('EmployeeConsultationAbsencesComponent', () => {
  let component: EmployeeConsultationAbsencesComponent;
  let fixture: ComponentFixture<EmployeeConsultationAbsencesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [EmployeeConsultationAbsencesComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(EmployeeConsultationAbsencesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
