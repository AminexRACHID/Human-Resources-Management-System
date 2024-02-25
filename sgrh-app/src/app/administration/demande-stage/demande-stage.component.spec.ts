import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DemandeStageComponent } from './demande-stage.component';

describe('DemandeStageComponent', () => {
  let component: DemandeStageComponent;
  let fixture: ComponentFixture<DemandeStageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [DemandeStageComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(DemandeStageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
