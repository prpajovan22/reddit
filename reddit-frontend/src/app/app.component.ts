import { Component, OnInit } from '@angular/core';
import { AuthService } from './services/authService/auth.service';
import { Router } from '@angular/router';


@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit{
  title = 'reddit-frontend';

  constructor(private authService:AuthService, private router: Router) {
  }

  ngOnInit(): void {
    if(localStorage.getItem("isUserLoggedIn") && localStorage.getItem("isUserLoggedIn")=== "true"){
      this.authService.setUserLoggedIn(true);
    }
    
  }
}


