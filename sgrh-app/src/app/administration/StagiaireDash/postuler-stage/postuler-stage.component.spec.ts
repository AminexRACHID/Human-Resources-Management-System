import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PostulerStageComponent } from './postuler-stage.component';

describe('PostulerStageComponent', () => {
  let component: PostulerStageComponent;
  let fixture: ComponentFixture<PostulerStageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [PostulerStageComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(PostulerStageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
