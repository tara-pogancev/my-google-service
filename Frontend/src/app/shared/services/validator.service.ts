import {
  AbstractControl,
  AsyncValidatorFn,
  FormControl,
  ValidationErrors,
  ValidatorFn,
} from '@angular/forms';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { AuthenticationRequest } from '../model/authentication-request';
import { AuthService } from './auth.service';

export function ConfirmedValidator(field: string): ValidatorFn {
  return (control: AbstractControl): ValidationErrors | null => {
    const group = control.parent;
    const fieldToCompare = group!.get(field);
    const areEqual = control.value === fieldToCompare?.value;
    return areEqual ? null : { confirmFailed: true };
  };
}

export function EmailTakenValidator(
  authService: AuthService
): AsyncValidatorFn {
  return (control: AbstractControl): Observable<ValidationErrors | null> => {
    let email = '';
    if (control.value != null) email = control.value;
    let request = new AuthenticationRequest(email, 'password');

    return authService
      .checkIfEmailExists(request)
      .pipe(map((result) => (result ? { emailIsTaken: true } : null)));
  };
}

export function EmailDoesntExistValidator(
  authService: AuthService
): AsyncValidatorFn {
  return (control: AbstractControl): Observable<ValidationErrors | null> => {
    let email = '';
    if (control.value != null) email = control.value;
    let request = new AuthenticationRequest(email, 'password');

    return authService
      .checkIfEmailExists(request)
      .pipe(map((result) => (result ? null : { emailDoesntExist: true })));
  };
}

/*
export function IncorrectPasswordValidator(
  authService: AuthService,
  email: string
): AsyncValidatorFn {
  return (control: AbstractControl): Observable<ValidationErrors | null> => {
    let password = '';
    if (control.value != null) password = control.value;
    let request = new AuthenticationRequest(email, password);

    console.log(request);

    return authService
      .login(request)
      .pipe(
        map((response) =>
          response.headers['status'] == 401 ? { incorrectPassword: true } : null
        )
      );
  };
}
*/
