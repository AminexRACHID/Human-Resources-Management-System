import { TestBed } from '@angular/core/testing';

import { AlertluncherService } from './alertluncher.service';

describe('AlertluncherService', () => {
  let service: AlertluncherService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AlertluncherService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
