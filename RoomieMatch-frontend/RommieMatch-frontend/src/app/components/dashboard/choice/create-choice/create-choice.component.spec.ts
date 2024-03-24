import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateChoiceComponent } from './create-choice.component';

describe('CreateChoiceComponent', () => {
  let component: CreateChoiceComponent;
  let fixture: ComponentFixture<CreateChoiceComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [CreateChoiceComponent]
    });
    fixture = TestBed.createComponent(CreateChoiceComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
