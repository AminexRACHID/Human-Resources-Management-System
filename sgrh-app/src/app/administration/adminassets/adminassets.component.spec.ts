import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminassetsComponent } from './adminassets.component';

describe('AdminassetsComponent', () => {
  let component: AdminassetsComponent;
  let fixture: ComponentFixture<AdminassetsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [AdminassetsComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(AdminassetsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
