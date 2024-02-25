import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AbsencesNonJustifierComponent } from './absences-non-justifier.component';

describe('AbsencesNonJustifierComponent', () => {
  let component: AbsencesNonJustifierComponent;
  let fixture: ComponentFixture<AbsencesNonJustifierComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [AbsencesNonJustifierComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(AbsencesNonJustifierComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
