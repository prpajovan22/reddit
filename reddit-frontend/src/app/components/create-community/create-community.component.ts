import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Community } from 'src/app/models/Community';
import { CommunityService } from 'src/app/services/communityService/community.service';

@Component({
  selector: 'app-create-community',
  templateUrl: './create-community.component.html',
  styleUrls: ['./create-community.component.css']
})
export class CreateCommunityComponent implements OnInit {

  community_id:number;
  communitys: Community;

  constructor(private route: ActivatedRoute, private router: Router, private communityService: CommunityService) { }

  ngOnInit(): void {
    this.communitys = new Community();
  }
  redirectToCommunitys(){
    this.router.navigate(['/communitys']);
  }

  createCommunity(){
    this.communityService.createCommunity(this.communitys).subscribe(data=>{
      console.log(data);
      this.communitys = new Community();
      console.log(this.communitys);
      this.redirectToCommunitys();
    }, error=>console.log(error));
  }
  onSubmit(){
    this.createCommunity();
  }

 

}
