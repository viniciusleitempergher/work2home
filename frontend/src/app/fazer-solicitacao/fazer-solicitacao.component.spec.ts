import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FazerSolicitacaoComponent } from './fazer-solicitacao.component';

describe('FazerSolicitacaoComponent', () => {
  let component: FazerSolicitacaoComponent;
  let fixture: ComponentFixture<FazerSolicitacaoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ FazerSolicitacaoComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(FazerSolicitacaoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
