export class UserPhoneNumber {
  constructor(
    public id: number = -1,
    public userId: number = -1,
    public phoneNumber: string = '',
    public type: string = ''
  ) {}
}
