import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { LoginRequest } from 'src/app/models/LoginRequest';
import { AuthService, AuthenticationResponse } from 'src/app/services/authService/auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  email:string;
  password:string;

  constructor(private authService:AuthService, private router: Router) {

   }

  ngOnInit(): void {
  }

  onSubmit() {
    if (!this.email) {
      alert('Please enter a username!');
      return;
    }else if(!this.password){
      alert('Please enter a password!');
      return;
    }

    const object:LoginRequest = {
      email: this.email,
      password: this.password,
    };

    this.authService.login(object).subscribe((authResponse:AuthenticationResponse) => {
    if(authResponse.successfull){
      localStorage.setItem("isUserLoggedIn","true");
      localStorage.setItem("userRole",authResponse.role);
      this.email = '';
      this.password = '';
      this.authService.setUserLoggedIn(true);
  
      this.router.navigate(['home']);
    }else{
      alert(authResponse.message)
    }
  });
  }

}
