import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminDay } from './admin-day';

describe('AdminDay', () => {
  let component: AdminDay;
  let fixture: ComponentFixture<AdminDay>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AdminDay],
    }).compileComponents();

    fixture = TestBed.createComponent(AdminDay);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
