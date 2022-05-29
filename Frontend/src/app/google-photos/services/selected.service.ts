import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs/internal/BehaviorSubject';

@Injectable({
  providedIn: 'root'
})
export class SelectedService {
  private storageValue: BehaviorSubject<Array<string>> = new BehaviorSubject<string[]>([]);
  currentValue = this.storageValue.asObservable();

  private actionValue: BehaviorSubject<string> = new BehaviorSubject<string>("");
  currentAction = this.actionValue.asObservable();

  constructor() { }

  changeValue(selected: string[]) {
    this.storageValue.next(selected)
  }

  changeAction(action: string) {
    this.actionValue.next(action)
  }
}
