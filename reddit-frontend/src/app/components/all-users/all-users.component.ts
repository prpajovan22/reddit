import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { User } from 'src/app/models/User';
import { AuthService } from 'src/app/services/authService/auth.service';
import { UserServiceService } from 'src/app/services/userService/user.service';
import { DomSanitizer, SafeResourceUrl } from '@angular/platform-browser';
import { UserFront } from 'src/app/models/Searchers/UserFront';

@Component({
  selector: 'app-all-users',
  templateUrl: './all-users.component.html',
  styleUrls: ['./all-users.component.css']
})
export class AllUsersComponent implements OnInit {

  users:UserFront[] = [];
  user:any;
  isUserLoggedIn:boolean;

  constructor(private userService: UserServiceService,
    private authService:AuthService, private router: Router,private sanitizer: DomSanitizer) { }

  ngOnInit(): void {
    this.user = this.authService.getLoggedInUser();
    this.fetchUsers();
  }

  fetchUsers(): void {
    this.userService.getUsers2().subscribe(
      (users) => {
        this.users = users;
        this.users.forEach(user => {
          user.sanitisedImage = this.sanitizer.bypassSecurityTrustResourceUrl(user.avatar);
        });
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
public updateCommunity(user_id:number){
  this.router.navigate(['changeUser', user_id]);
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

