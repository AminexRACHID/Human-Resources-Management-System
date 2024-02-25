import { ComponentFixture, TestBed } from '@angular/core/testing';

import { StagiaireAtestationComponent } from './stagiaire-atestation.component';

describe('StagiaireAtestationComponent', () => {
  let component: StagiaireAtestationComponent;
  let fixture: ComponentFixture<StagiaireAtestationComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [StagiaireAtestationComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(StagiaireAtestationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
