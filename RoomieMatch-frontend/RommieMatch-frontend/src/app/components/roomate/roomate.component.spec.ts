import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RoomateComponent } from './roomate.component';

describe('RoomateComponent', () => {
  let component: RoomateComponent;
  let fixture: ComponentFixture<RoomateComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [RoomateComponent]
    });
    fixture = TestBed.createComponent(RoomateComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
