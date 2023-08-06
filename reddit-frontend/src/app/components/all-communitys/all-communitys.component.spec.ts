import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AllCommunitysComponent } from './all-communitys.component';

describe('AllCommunitysComponent', () => {
  let component: AllCommunitysComponent;
  let fixture: ComponentFixture<AllCommunitysComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AllCommunitysComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AllCommunitysComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
