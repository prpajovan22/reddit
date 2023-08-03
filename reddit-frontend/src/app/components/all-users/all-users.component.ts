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
    this.userService.getUsers().subscribe((users) => this.users = users)
  }
  public updateUser(user_id:number){
    this.router.navigate(['updateUser', user_id]);
  }

}
