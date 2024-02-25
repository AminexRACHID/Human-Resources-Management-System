import { TestBed } from '@angular/core/testing';

import { StagiaireService } from './stagiaire.service';

describe('StagiaireService', () => {
  let service: StagiaireService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(StagiaireService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
