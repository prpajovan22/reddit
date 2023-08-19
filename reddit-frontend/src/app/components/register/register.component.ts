import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { User } from 'src/app/models/User';
import { UserServiceService } from 'src/app/services/userService/user.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {

  user:User;
  constructor(private router:Router,private route: ActivatedRoute,private userServce:UserServiceService) { }
  selectedUserRole : string;

  ngOnInit(): void {
    this.user = new User();
  }

  redirectToLogin(){
    this.router.navigate(['/login']);
  }
  redirectToHome(){
    this.router.navigate(['/home']);
  }
  Register(){
    this.userServce.registerUser(this.user).subscribe(data=>{
      console.log(data);
      this.user = new User();
      console.log(this.user);
      this.redirectToLogin();
    },
    error=>console.log(error));
  }
  onSubmit(){
    this.Register();
  }
}