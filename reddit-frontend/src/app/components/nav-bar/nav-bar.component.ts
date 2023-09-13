import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { AuthService } from 'src/app/services/authService/auth.service';

@Component({
  selector: 'app-nav-bar',
  templateUrl: './nav-bar.component.html',
  styleUrls: ['./nav-bar.component.css']
})
export class NavBarComponent implements OnInit {

  user: any;
  isUserLoggedIn:boolean;

  constructor(private route: ActivatedRoute, private authService: AuthService) { }

  ngOnInit(): void {
    this.authService.getUserLoggedIn().subscribe(value =>{this.isUserLoggedIn = value})
  }

  logout(){
    this.authService.logout();
    this.authService.setUserLoggedIn(false);
  }
}
