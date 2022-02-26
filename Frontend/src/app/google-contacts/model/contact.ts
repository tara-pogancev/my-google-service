import { ContactEmail } from './contact-email';
import { ContactPhoneNumber } from './contact-phone-number';

export class Contact {
  constructor(
    public id: number = -1,
    public contactOwnerId: number = -1,
    public firstName: string = '',
    public lastName: string = '',
    public fullName: string = '',
    public starred: boolean = false,
    public deleted: boolean = false,
    public emails: ContactEmail[] = [],
    public phoneNumbers: ContactPhoneNumber[] = []
  ) {}
}
