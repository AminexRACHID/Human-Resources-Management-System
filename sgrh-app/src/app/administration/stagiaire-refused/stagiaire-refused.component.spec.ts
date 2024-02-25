import { ComponentFixture, TestBed } from '@angular/core/testing';

import { StagiaireRefusedComponent } from './stagiaire-refused.component';

describe('StagiaireRefusedComponent', () => {
  let component: StagiaireRefusedComponent;
  let fixture: ComponentFixture<StagiaireRefusedComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [StagiaireRefusedComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(StagiaireRefusedComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
