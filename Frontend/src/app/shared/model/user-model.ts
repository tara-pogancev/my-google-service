import { UserPhoneNumber } from './user-phone-number';

export class User {
  constructor(
    public id: number = -1,
    public firstName: string = '',
    public lastName: string = '',
    public email: string = '',
    public defaultApplication: string = '',
    public phoneNumbers: UserPhoneNumber[] = []
  ) {}
}
