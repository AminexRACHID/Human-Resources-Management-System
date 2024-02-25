import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddplanformationComponent } from './addplanformation.component';

describe('AddplanformationComponent', () => {
  let component: AddplanformationComponent;
  let fixture: ComponentFixture<AddplanformationComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [AddplanformationComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(AddplanformationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
