import { Component, Input, OnInit } from '@angular/core';

@Component({
  selector: 'app-consumer-test',
  templateUrl: './consumer-test.component.html',
  styleUrls: ['./consumer-test.component.css']
})
export class ConsumerTestComponent implements OnInit {

  @Input() testVar: string = '';

  constructor() { }

  ngOnInit(): void {
    
    
  }

}
