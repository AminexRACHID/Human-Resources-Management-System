import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ManageStagesComponent } from './manage-stages.component';

describe('ManageStagesComponent', () => {
  let component: ManageStagesComponent;
  let fixture: ComponentFixture<ManageStagesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ManageStagesComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(ManageStagesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
