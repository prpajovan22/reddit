import { HttpClient } from '@angular/common/http';
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
  selectedUserRole : string;

  constructor(
    private router: Router,
    private route: ActivatedRoute,
    private userService: UserServiceService,
    private http: HttpClient
  ) { }

  ngOnInit(): void {
    this.user = new User();
  }

  redirectToLogin(){
    this.router.navigate(['/login']);
  }
  redirectToHome(){
    this.router.navigate(['/home']);
  }
  Register() {
    const formData = new FormData();
    for (const key in this.user) {
      if (this.user.hasOwnProperty(key)) {
        formData.append(key, this.user[key]);
      }
    }

    formData.forEach((value,key) => {
      console.log("This is value: "+ value )
      console.log("For the key: " + key )
    })

    if (this.user.avatar instanceof File) {
      formData.append('avatar', this.user.avatar);
    }

    this.http.post('http://localhost:8080/api/login/registration', formData).subscribe(
      (data) => {
        console.log(data);
        this.user = new User();
        this.redirectToLogin();
      },
      (error) => {
        console.log(error);
      }
    );
  }
  onSubmit(){
    this.Register();
  }

  onAvatarChange(event: any) {
    if (event.target.files && event.target.files.length > 0) {
      this.user.avatar = event.target.files[0] as File;
    }
  }
}