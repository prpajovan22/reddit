import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SuspendCommnuityComponent } from './suspend-commnuity.component';

describe('SuspendCommnuityComponent', () => {
  let component: SuspendCommnuityComponent;
  let fixture: ComponentFixture<SuspendCommnuityComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SuspendCommnuityComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SuspendCommnuityComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
