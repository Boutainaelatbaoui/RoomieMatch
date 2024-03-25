import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RequestDashComponent } from './request-dash.component';

describe('RequestDashComponent', () => {
  let component: RequestDashComponent;
  let fixture: ComponentFixture<RequestDashComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [RequestDashComponent]
    });
    fixture = TestBed.createComponent(RequestDashComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
