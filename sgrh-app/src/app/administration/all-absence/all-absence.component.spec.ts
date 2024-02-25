import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AllAbsenceComponent } from './all-absence.component';

describe('AllAbsenceComponent', () => {
  let component: AllAbsenceComponent;
  let fixture: ComponentFixture<AllAbsenceComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [AllAbsenceComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(AllAbsenceComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
