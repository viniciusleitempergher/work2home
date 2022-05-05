import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AlterarPrestadorScreenComponent } from './alterar-prestador-screen.component';

describe('AlterarPrestadorScreenComponent', () => {
  let component: AlterarPrestadorScreenComponent;
  let fixture: ComponentFixture<AlterarPrestadorScreenComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AlterarPrestadorScreenComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AlterarPrestadorScreenComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
