import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { UserPhoneNumber } from '../../model/user-phone-number';
import { UserService } from '../../services/user-service';

@Component({
  selector: 'edit-user-phone-number',
  templateUrl: './edit-user-phone-number.component.html',
  styleUrls: ['./edit-user-phone-number.component.scss'],
})
export class EditUserPhoneNumberComponent implements OnInit {
  @Output() doRefreshUser: EventEmitter<any> = new EventEmitter();
  @Input() phone: UserPhoneNumber = new UserPhoneNumber();
  mobileNumberForm: FormGroup = new FormGroup({});

  constructor(private userService: UserService) {}

  ngOnInit(): void {
    this.mobileNumberForm = new FormGroup({
      phoneNumber: new FormControl(this.phone.phoneNumber, {
        validators: [Validators.required],
        updateOn: 'change',
      }),
      type: new FormControl(this.phone.type, {
        validators: [Validators.required],
        updateOn: 'change',
      }),
    });
  }

  deleteContact() {
    let dto = new UserPhoneNumber();
    dto.id = this.phone.id;
    if (confirm('Are you sure you want to delete this phone number?')) {
      this.userService.deleteUserPhoneNumber(dto).subscribe((data) => {
        this.doRefreshUser.emit(null);
      });
    }
  }

  changeContact() {
    let dto = new UserPhoneNumber();
    dto.id = this.phone.id;
    dto.phoneNumber = this.mobileNumberForm.controls.phoneNumber.value;
    dto.type = this.mobileNumberForm.controls.type.value;
    this.userService.changeUserPhoneNumber(dto).subscribe((data) => {
      this.doRefreshUser.emit(null);
    });
  }
}
