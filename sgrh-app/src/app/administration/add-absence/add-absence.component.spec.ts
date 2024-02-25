import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddAbsenceComponent } from './add-absence.component';

describe('AddAbsenceComponent', () => {
  let component: AddAbsenceComponent;
  let fixture: ComponentFixture<AddAbsenceComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [AddAbsenceComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(AddAbsenceComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
