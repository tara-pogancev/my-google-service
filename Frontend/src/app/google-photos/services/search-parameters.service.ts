import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs/internal/BehaviorSubject';
import { SearchParametersDTO } from '../DTO/SearchParametersDTO';

@Injectable({
  providedIn: 'root'
})
export class SearchParametersService {

  private storageValue: BehaviorSubject<SearchParametersDTO> = new BehaviorSubject<SearchParametersDTO>({searchValue: '', beforeDate: '', afterDate: '', action: ''});
  currentValue = this.storageValue.asObservable();

  constructor() { }

  changeValue(value: SearchParametersDTO) {
    this.storageValue.next(value)
  }
}
