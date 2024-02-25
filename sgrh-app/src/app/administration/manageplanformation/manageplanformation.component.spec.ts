import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ManageplanformationComponent } from './manageplanformation.component';

describe('ManageplanformationComponent', () => {
  let component: ManageplanformationComponent;
  let fixture: ComponentFixture<ManageplanformationComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ManageplanformationComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(ManageplanformationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
