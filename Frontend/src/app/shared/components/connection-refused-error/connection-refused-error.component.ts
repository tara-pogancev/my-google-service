import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'connection-refused-error',
  templateUrl: './connection-refused-error.component.html',
  styleUrls: ['./connection-refused-error.component.scss'],
})
export class ConnectionRefusedErrorComponent implements OnInit {
  constructor() {}

  ngOnInit(): void {}

  redirectHomepage() {
    window.location.href = '/';
  }
}
