import { TestBed } from '@angular/core/testing';

import { OffreStageService } from './offre-stage.service';

describe('OffreStageService', () => {
  let service: OffreStageService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(OffreStageService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
