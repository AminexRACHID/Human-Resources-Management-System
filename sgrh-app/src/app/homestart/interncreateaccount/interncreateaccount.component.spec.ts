import { ComponentFixture, TestBed } from '@angular/core/testing';

import { InterncreateaccountComponent } from './interncreateaccount.component';

describe('InterncreateaccountComponent', () => {
  let component: InterncreateaccountComponent;
  let fixture: ComponentFixture<InterncreateaccountComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [InterncreateaccountComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(InterncreateaccountComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
