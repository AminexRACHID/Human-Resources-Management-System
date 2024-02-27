import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddNewStageComponent } from './add-new-stage.component';

describe('AddNewStageComponent', () => {
  let component: AddNewStageComponent;
  let fixture: ComponentFixture<AddNewStageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [AddNewStageComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(AddNewStageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
