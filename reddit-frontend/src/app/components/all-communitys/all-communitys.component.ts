import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Community } from 'src/app/models/Community';
import { CommunityService } from 'src/app/services/communityService/community.service';

@Component({
  selector: 'app-all-communitys',
  templateUrl: './all-communitys.component.html',
  styleUrls: ['./all-communitys.component.css']
})
export class AllCommunitysComponent implements OnInit {

  communitys:Community[] = [];
  community:any;

  constructor(private communityService:CommunityService, private router:Router) { }

  ngOnInit(): void {
    this.communityService.getCommunitys().subscribe((communitys) => this.communitys = communitys)
  }

  public updateCommunity(community_id:number){
    this.router.navigate(['updateCommunity', community_id]);
  }

  public createCommunity() {
    this.router.navigate(['createCommunity']);
  }

  public createPost(community_id:number){
    this.router.navigate(['createPost', community_id]);
  }

  public showPost(community_id:number){
    this.router.navigate(['communityPosts', community_id]);
  }

  public deleteCommunity(community_id:number) {
    console.log(community_id);
    let response = this.communityService.deleteCommunity(community_id);
    response.subscribe((communitys)=> this.communitys = communitys)
  }

}
