import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OneCommunityComponent } from './one-community.component';

describe('OneCommunityComponent', () => {
  let component: OneCommunityComponent;
  let fixture: ComponentFixture<OneCommunityComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ OneCommunityComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(OneCommunityComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
