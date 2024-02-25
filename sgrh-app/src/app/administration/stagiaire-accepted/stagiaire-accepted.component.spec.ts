import { ComponentFixture, TestBed } from '@angular/core/testing';

import { StagiaireAcceptedComponent } from './stagiaire-accepted.component';

describe('StagiaireAcceptedComponent', () => {
  let component: StagiaireAcceptedComponent;
  let fixture: ComponentFixture<StagiaireAcceptedComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [StagiaireAcceptedComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(StagiaireAcceptedComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
