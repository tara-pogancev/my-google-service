import { Component, Input, OnInit } from '@angular/core';

@Component({
  selector: 'gap',
  templateUrl: './gap.component.html',
  styleUrls: ['./gap.component.scss'],
})
export class GapComponent implements OnInit {
  @Input() height: string = '50';
  @Input() width: string = '0';

  constructor() {}

  ngOnInit(): void {}
}
