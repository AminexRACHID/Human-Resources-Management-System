import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ManageDemandeFormationComponent } from './manage-demande-formation.component';

describe('ManageDemandeFormationComponent', () => {
  let component: ManageDemandeFormationComponent;
  let fixture: ComponentFixture<ManageDemandeFormationComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ManageDemandeFormationComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(ManageDemandeFormationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
