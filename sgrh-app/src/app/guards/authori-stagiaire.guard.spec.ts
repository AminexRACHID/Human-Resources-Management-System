import { TestBed } from '@angular/core/testing';
import { CanActivateFn } from '@angular/router';

import { authoriStagiaireGuard } from './authori-stagiaire.guard';

describe('authoriStagiaireGuard', () => {
  const executeGuard: CanActivateFn = (...guardParameters) => 
      TestBed.runInInjectionContext(() => authoriStagiaireGuard(...guardParameters));

  beforeEach(() => {
    TestBed.configureTestingModule({});
  });

  it('should be created', () => {
    expect(executeGuard).toBeTruthy();
  });
});
