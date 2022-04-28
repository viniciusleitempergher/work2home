import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-auth-context',
  templateUrl: './auth-context.component.html',
  styleUrls: ['./auth-context.component.css']
})
export class AuthContextComponent implements OnInit {

  testVar:string = "FUNCIONA KKK";

  constructor() { }

  ngOnInit(): void {
  }

}
