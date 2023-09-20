import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { User } from 'src/app/models/User';
import { UserServiceService } from 'src/app/services/userService/user.service';

@Component({
  selector: 'app-change-user',
  templateUrl: './change-user.component.html',
  styleUrls: ['./change-user.component.css']
})
export class ChangeUserComponent implements OnInit {

  user_id:number;
  communities: User;

  constructor(private route: ActivatedRoute, private router: Router, private communityService: UserServiceService) { }

  ngOnInit(): void {
    this.user_id = this.route.snapshot.params['user_id'];
    this.communityService.getUserById2(this.user_id).subscribe(
      (data) => {
        console.log(data);
        this.communities = data;
      },
      (error) => console.log(error)
    );
  }
  redirectToListOfAllDepartments(){
    this.router.navigate(['/users']);
  }

  updateDepartment(){
    const formData = new FormData();
    formData.append("username", this.communities.username);
    formData.append("email", this.communities.email);
    this.communityService.updateCommunity(this.user_id, formData).subscribe(data=>{
      console.log(data);
      this.communities = new User();
      this.redirectToListOfAllDepartments();
    }, error=>console.log(error));
  }
  onSubmit(){
    this.updateDepartment();
  }

  onAvatarChange(event: any) {
    if (event.target.files && event.target.files.length > 0) {
      this.communities.avatar = event.target.files[0] as File;
    }
  }


}