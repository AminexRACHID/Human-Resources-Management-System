import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EntretienStagiaireComponent } from './entretien-stagiaire.component';

describe('EntretienStagiaireComponent', () => {
  let component: EntretienStagiaireComponent;
  let fixture: ComponentFixture<EntretienStagiaireComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [EntretienStagiaireComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(EntretienStagiaireComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
