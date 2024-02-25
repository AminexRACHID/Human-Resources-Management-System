import { ComponentFixture, TestBed } from '@angular/core/testing';

import { InternoffersComponent } from './internoffers.component';

describe('InternoffersComponent', () => {
  let component: InternoffersComponent;
  let fixture: ComponentFixture<InternoffersComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [InternoffersComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(InternoffersComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
