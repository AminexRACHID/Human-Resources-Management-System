import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HomestartassetsComponent } from './homestartassets.component';

describe('HomestartassetsComponent', () => {
  let component: HomestartassetsComponent;
  let fixture: ComponentFixture<HomestartassetsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [HomestartassetsComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(HomestartassetsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
