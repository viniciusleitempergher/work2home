import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PrestadorCategoriaScreenComponent } from './prestador-categoria-screen.component';

describe('PrestadorCategoriaScreenComponent', () => {
  let component: PrestadorCategoriaScreenComponent;
  let fixture: ComponentFixture<PrestadorCategoriaScreenComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PrestadorCategoriaScreenComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(PrestadorCategoriaScreenComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
