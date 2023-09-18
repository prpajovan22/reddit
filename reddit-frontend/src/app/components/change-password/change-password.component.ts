import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { UserServiceService } from 'src/app/services/userService/user.service';

@Component({
  selector: 'app-change-password',
  templateUrl: './change-password.component.html',
  styleUrls: ['./change-password.component.css']
})
export class ChangePasswordComponent{

  oldPassword: string = '';
  newPassword: string = '';

  constructor(private userService: UserServiceService,
    private router: Router) {}

  /*changePassword() {
    this.userService.changePassword(this.oldPassword, this.newPassword)
      .subscribe(
        response => console.log(response),
        error => console.error(error)
      );
  }*/

  changePassword() {
    this.userService.changePassword(this.oldPassword, this.newPassword)
      .subscribe(
        response => {
          console.log(response);
          this.router.navigate(['home']); 
        },
        error => console.error(error)
      );
  }

}
