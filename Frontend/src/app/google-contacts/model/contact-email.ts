export class ContactEmail {
  constructor(
    public id: number = -1,
    public contactId: number = -1,
    public email: string = '',
    public type: string = ''
  ) {}
}
