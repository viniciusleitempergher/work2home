import { Component, Input, OnInit } from '@angular/core';
import { Usuario } from 'src/models/Usuario';

@Component({
  selector: 'app-admin-main-screen',
  templateUrl: './admin-main-screen.component.html',
  styleUrls: ['./admin-main-screen.component.css']
})
export class AdminMainScreenComponent implements OnInit {

  @Input() user:Usuario = {} as Usuario;

  constructor() { }

  ngOnInit(): void {
    console.log(this.user);
  }

}
