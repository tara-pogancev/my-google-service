import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs/internal/BehaviorSubject';
import { Photo } from '../model/Photo';

@Injectable({
  providedIn: 'root'
})
export class SelectedService {
  private storageValue: BehaviorSubject<Array<Photo>> = new BehaviorSubject<Photo[]>([]);
  currentValue = this.storageValue.asObservable();

  private actionValue: BehaviorSubject<string> = new BehaviorSubject<string>("");
  currentAction = this.actionValue.asObservable();

  constructor() { }

  changeValue(selected: Photo[]) {
    this.storageValue.next(selected)
  }

  changeAction(action: string) {
    this.actionValue.next(action)
  }
}
