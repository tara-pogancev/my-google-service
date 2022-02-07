export class ActiveUser {
  constructor(
    public id: number = -1,
    public name: string = '',
    public jwt: string = '',
    public email: string = ''
  ) {}
}
