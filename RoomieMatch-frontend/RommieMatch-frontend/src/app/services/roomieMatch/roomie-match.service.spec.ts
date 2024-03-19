import { TestBed } from '@angular/core/testing';

import { RoomieMatchService } from './roomie-match.service';

describe('RoomieMatchService', () => {
  let service: RoomieMatchService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(RoomieMatchService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
