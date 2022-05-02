import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PrestadorMainScreenComponent } from './prestador-main-screen.component';

describe('PrestadorMainScreenComponent', () => {
  let component: PrestadorMainScreenComponent;
  let fixture: ComponentFixture<PrestadorMainScreenComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PrestadorMainScreenComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(PrestadorMainScreenComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
