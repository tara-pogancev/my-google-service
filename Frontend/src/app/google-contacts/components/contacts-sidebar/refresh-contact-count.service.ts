import { Injectable } from '@angular/core';
import { Subject } from 'rxjs';

@Injectable()
export class RefreshContactsCountService {
  // Observable string sources
  private refreshActionSource = new Subject<void>();

  // Observable string streams
  refreshContacts$ = this.refreshActionSource.asObservable();

  // Service message commands
  announceRefreshing() {
    this.refreshActionSource.next();
  }
}
