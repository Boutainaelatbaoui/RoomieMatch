import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RoomateDetailsComponent } from './roomate-details.component';

describe('RoomateDetailsComponent', () => {
  let component: RoomateDetailsComponent;
  let fixture: ComponentFixture<RoomateDetailsComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [RoomateDetailsComponent]
    });
    fixture = TestBed.createComponent(RoomateDetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
