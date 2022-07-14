import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs/internal/BehaviorSubject';

@Injectable({
  providedIn: 'root'
})
export class SidebarService {

  private storageValue: BehaviorSubject<string> = new BehaviorSubject<string>('');
  currentValue = this.storageValue.asObservable();


  constructor() { }

  changeValue(selected: string) {
    this.storageValue.next(selected)
  }
}

