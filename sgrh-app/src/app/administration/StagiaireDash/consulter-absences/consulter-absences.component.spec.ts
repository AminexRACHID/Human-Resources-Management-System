import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ConsulterAbsencesComponent } from './consulter-absences.component';

describe('ConsulterAbsencesComponent', () => {
  let component: ConsulterAbsencesComponent;
  let fixture: ComponentFixture<ConsulterAbsencesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ConsulterAbsencesComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(ConsulterAbsencesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
