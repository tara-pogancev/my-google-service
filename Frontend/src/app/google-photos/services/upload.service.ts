import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs/internal/BehaviorSubject';

@Injectable({
  providedIn: 'root'
})
export class UploadService {

  private storageValue: BehaviorSubject<string> = new BehaviorSubject<string>('');
  currentValue = this.storageValue.asObservable();

  constructor() { }

  changeValue(value: string) {
    this.storageValue.next(value)
  }
}
