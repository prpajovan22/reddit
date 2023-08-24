import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { CommunityService } from 'src/app/services/communityService/community.service';

@Component({
  selector: 'app-one-community',
  templateUrl: './one-community.component.html',
  styleUrls: ['./one-community.component.css']
})
export class OneCommunityComponent implements OnInit {

  community: any; 

  constructor(
    private route: ActivatedRoute,
    private communityService: CommunityService
  ) {}

  ngOnInit(): void {
    const community_id = this.route.snapshot.params['community_id'];
    this.communityService.getCommunity(community_id).subscribe(data => {
      this.community = data;
    });
  }
}