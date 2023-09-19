import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { User } from 'src/app/models/User';
import { UserServiceService } from 'src/app/services/userService/user.service';

@Component({
  selector: 'app-all-users',
  templateUrl: './all-users.component.html',
  styleUrls: ['./all-users.component.css']
})
export class AllUsersComponent implements OnInit {

  users:User[] = [];
  user:any;

  constructor(private userService: UserServiceService, private router: Router) { }

  ngOnInit(): void {
    this.fetchUsers();
  }

  fetchUsers(): void {
    this.userService.getUsers().subscribe(
      (users) => {
        this.users = users;
      },
      (error) => {
        console.error(error);
      }
    );
  }
  public updateUser(){
    this.router.navigate(['changePassword']);
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
}

