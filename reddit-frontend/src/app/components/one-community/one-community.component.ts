import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Community } from 'src/app/models/Community';
import { CommunityService } from 'src/app/services/communityService/community.service';

@Component({
  selector: 'app-one-community',
  templateUrl: './one-community.component.html',
  styleUrls: ['./one-community.component.css']
})
export class OneCommunityComponent implements OnInit {

  community_id:number;
  community: Community;

  constructor(private route: ActivatedRoute, private router:Router, private userService: CommunityService) { }

  ngOnInit(): void {
    this.community = new Community()

    this.community_id = this.route.snapshot.params['community_id'];

    this.userService.getCommunityById(this.community_id).subscribe(
    {
      next: data => this.community = data , 
      error: error => console.log(error)
    }
    );
  }
  goHome(){
    this.router.navigate(['home']);
  }
}