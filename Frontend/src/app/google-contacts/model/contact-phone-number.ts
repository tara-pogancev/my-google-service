export class ContactPhoneNumber {
  constructor(
    public id: number = -1,
    public contactId: number = -1,
    public phoneNumber: string = '',
    public type: string = ''
  ) {}
}
