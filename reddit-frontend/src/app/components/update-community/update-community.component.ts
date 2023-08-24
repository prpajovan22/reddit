import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Community } from 'src/app/models/Community';
import { CommunityService } from 'src/app/services/communityService/community.service';

@Component({
  selector: 'app-update-community',
  templateUrl: './update-community.component.html',
  styleUrls: ['./update-community.component.css']
})
export class UpdateCommunityComponent implements OnInit {

  id:number;
  communities: Community;

  constructor(private route: ActivatedRoute, private router: Router, private communityService: CommunityService) { }

  ngOnInit(): void {

    this.communities = new Community();

    this.id = this.route.snapshot.params['community_id'];

    this.communityService.getCommunityById(this.id).subscribe(data=>{
      console.log(data)
      this.communities = data
    }, error=> console.log(error));
  }
  redirectToListOfAllDepartments(){
    this.router.navigate(['/communitys']);
  }

  updateDepartment(){
    this.communityService.updateCommunity(this.id, this.communities).subscribe(data=>{
      console.log(data);
      this.communities = new Community();
      this.redirectToListOfAllDepartments();
    }, error=>console.log(error));
  }
  onSubmit(){
    this.updateDepartment();
  }

}