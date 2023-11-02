import { TestBed } from '@angular/core/testing';

import { LoggedInAuthGuardGuard } from './logged-in-auth-guard.guard';

describe('LoggedInAuthGuardGuard', () => {
  let guard: LoggedInAuthGuardGuard;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    guard = TestBed.inject(LoggedInAuthGuardGuard);
  });

  it('should be created', () => {
    expect(guard).toBeTruthy();
  });
});
