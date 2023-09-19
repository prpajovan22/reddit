import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { User } from 'src/app/models/User';
import { AuthService } from 'src/app/services/authService/auth.service';
import { UserServiceService } from 'src/app/services/userService/user.service';

@Component({
  selector: 'app-all-users',
  templateUrl: './all-users.component.html',
  styleUrls: ['./all-users.component.css']
})
export class AllUsersComponent implements OnInit {

  users:User[] = [];
  user:any;
  isUserLoggedIn:boolean;

  constructor(private userService: UserServiceService,
    private authService:AuthService, private router: Router) { }

  ngOnInit(): void {
    this.user = this.authService.getLoggedInUser();
    this.fetchUsers();
  }

  fetchUsers(): void {
    this.userService.getUsers().subscribe(
      (users) => {
        this.users = users;
        console.log(users); 
      },
      (error) => {
        console.error(error);
      }
    );
  }
  public updateUser(){
    this.router.navigate(['changePassword']);
  }

  isAdmin(): boolean {
    const userRole = localStorage.getItem('userRole');
    return userRole === 'ADMIN';
}

isModeratorOrAdmin(): boolean {
  const userRole = localStorage.getItem('userRole');
  return userRole === 'MODERATOR' || userRole === 'ADMIN';
}

  switchUserRole(user_id: number): void {
    this.userService.switchUserRole(user_id).subscribe(
      response => {
        console.log(response); 
      },
      error => {
        console.error(error);
      }
    );
  }

  suspendUser(user_id: number): void {
    this.userService.suspendUser(user_id).subscribe(
      response => {
        console.log(response);
      },
      error => {
        console.error(error); 
      }
    );
  }

  returnUser(user_id: number): void {
    this.userService.returnUser(user_id).subscribe(
      response => {
        console.log(response);
      },
      error => {
        console.error(error); 
      }
    );
  }
}

