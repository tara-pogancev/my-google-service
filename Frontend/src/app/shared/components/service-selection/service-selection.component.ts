import { Component, OnInit } from '@angular/core';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'service-selection',
  templateUrl: './service-selection.component.html',
  styleUrls: ['./service-selection.component.scss'],
})
export class ServiceSelectionComponent implements OnInit {
  constructor(private authService: AuthService) {}

  ngOnInit(): void {}

  redirectPhotos() {
    window.location.href = '/photos';
  }

  redirectContacts() {
    window.location.href = '/contacts';
  }
}
