import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ConsulterEmployeeFormationComponent } from './consulter-employee-formation.component';

describe('ConsulterEmployeeFormationComponent', () => {
  let component: ConsulterEmployeeFormationComponent;
  let fixture: ComponentFixture<ConsulterEmployeeFormationComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ConsulterEmployeeFormationComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(ConsulterEmployeeFormationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
