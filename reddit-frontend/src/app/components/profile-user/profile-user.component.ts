import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { User } from 'src/app/models/User';
import { UserServiceService } from 'src/app/services/userService/user.service';

@Component({
  selector: 'app-profile-user',
  templateUrl: './profile-user.component.html',
  styleUrls: ['./profile-user.component.css']
})
export class ProfileUserComponent implements OnInit {

  id:number;
  user: User;

  constructor(private route: ActivatedRoute, private router:Router, private userService: UserServiceService) { }

  ngOnInit(): void {
    this.user = new User()

    this.id = this.route.snapshot.params['id'];

    this.userService.getUserById(this.id).subscribe(
    {
      next: data => this.user = data , 
      error: error => console.log(error)
    }
    );
  }
  goHome(){
    this.router.navigate(['']);
  }
}
