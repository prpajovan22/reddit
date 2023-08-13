import { Component, OnInit } from '@angular/core';
import { UserServiceService } from 'src/app/services/userService/user.service';

@Component({
  selector: 'app-change-password',
  templateUrl: './change-password.component.html',
  styleUrls: ['./change-password.component.css']
})
export class ChangePasswordComponent{

  oldPassword: string = '';
  newPassword: string = '';

  constructor(private userService: UserServiceService) {}

  changePassword() {
    this.userService.changePassword(this.oldPassword, this.newPassword)
      .subscribe(
        response => console.log(response),
        error => console.error(error)
      );
  }

}
