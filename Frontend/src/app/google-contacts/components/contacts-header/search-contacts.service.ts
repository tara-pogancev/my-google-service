import { Injectable } from '@angular/core';
import { Subject } from 'rxjs';
import { Contact } from '../../model/contact';

@Injectable()
export class SearchContactsService {
  // Observable string sources
  private searchActionSource = new Subject<string>();

  // Observable string streams
  doSearch$ = this.searchActionSource.asObservable();

  // Service message commands
  announceSearch(text: string) {
    this.searchActionSource.next(text);
  }

  //Search service
  searchContacts(contacts: Contact[], text: string) {
    let result: Contact[] = [];
    for (let contact of contacts) {
      let isAdded = false;
      if (
        contact.firstName.toLowerCase().includes(text.toLowerCase()) ||
        contact.lastName.toLowerCase().includes(text.toLowerCase())
      ) {
        result.push(contact);
        isAdded = true;
        continue;
      }

      if (isAdded == false) {
        for (let email of contact.emails) {
          if (email.email.toLowerCase().includes(text.toLowerCase())) {
            result.push(contact);
            isAdded = true;
            break;
          }
        }
      }

      if (isAdded == false) {
        for (let phoneNumber of contact.phoneNumbers) {
          if (
            phoneNumber.phoneNumber
              .toLowerCase()
              .replace(/ /g, '')
              .includes(text.toLowerCase())
          ) {
            result.push(contact);
            isAdded = true;
            break;
          }
        }
      }
    }

    return result;
  }
}
