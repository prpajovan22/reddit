import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { User } from 'src/app/models/User';
import { AuthService } from 'src/app/services/authService/auth.service';
import { UserServiceService } from 'src/app/services/userService/user.service';

@Component({
  selector: 'app-profile-user',
  templateUrl: './profile-user.component.html',
  styleUrls: ['./profile-user.component.css']
})
export class ProfileUserComponent implements OnInit {

  user: any;
  isUserLoggedIn:boolean;

  constructor(private userService: UserServiceService,private authService:AuthService) {}

  ngOnInit() {
    this.userService.getLoggedInUserProfile().subscribe(
      (response) => {
        console.log(response);
        this.user = response;
      },
      (error) => {
        console.error('Error:', error);
      }
    );
  }
}
