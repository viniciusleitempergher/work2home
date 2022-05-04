import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ClienteMainScreenComponent } from './cliente-main-screen.component';

describe('ClienteMainScreenComponent', () => {
  let component: ClienteMainScreenComponent;
  let fixture: ComponentFixture<ClienteMainScreenComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ClienteMainScreenComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ClienteMainScreenComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
