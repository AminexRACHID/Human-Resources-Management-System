import { ComponentFixture, TestBed } from '@angular/core/testing';

import { InternsDetailsModalComponent } from './interns-details-modal.component';

describe('InternsDetailsModalComponent', () => {
  let component: InternsDetailsModalComponent;
  let fixture: ComponentFixture<InternsDetailsModalComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [InternsDetailsModalComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(InternsDetailsModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
