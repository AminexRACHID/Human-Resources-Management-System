import { ComponentFixture, TestBed } from '@angular/core/testing';

import { InternloginComponent } from './internlogin.component';

describe('InternloginComponent', () => {
  let component: InternloginComponent;
  let fixture: ComponentFixture<InternloginComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [InternloginComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(InternloginComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
