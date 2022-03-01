import { Injectable } from '@angular/core';
import { Subject } from 'rxjs';

@Injectable()
export class ProfileRefreshService {
  // Observable string sources
  private refreshImageActionSource = new Subject<void>();
  private refreshNameActionSource = new Subject<void>();

  // Observable string streams
  doRefreshImage$ = this.refreshImageActionSource.asObservable();
  doRefreshName$ = this.refreshNameActionSource.asObservable();

  // Service message commands
  announceRefreshImage() {
    this.refreshImageActionSource.next();
  }

  // Service message commands
  announceRefreshName() {
    alert('refresh from service');
    this.refreshNameActionSource.next();
  }
}
