import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs/internal/BehaviorSubject';

@Injectable({
  providedIn: 'root'
})
export class StorageService {
  private storageValue = new BehaviorSubject(0);
  currentValue = this.storageValue.asObservable();

  constructor() { }

  changeValue(value: number) {
    this.storageValue.next(value)
  }
}
