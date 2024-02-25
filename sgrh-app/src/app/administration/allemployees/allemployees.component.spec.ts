import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AllemployeesComponent } from './allemployees.component';

describe('AllemployeesComponent', () => {
  let component: AllemployeesComponent;
  let fixture: ComponentFixture<AllemployeesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [AllemployeesComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(AllemployeesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
