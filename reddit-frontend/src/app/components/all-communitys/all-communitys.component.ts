import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { Router } from '@angular/router';
import { Community } from 'src/app/models/Community';
import { CommunitySearchCriteria } from 'src/app/models/Searchers/CommunitySearchCriteria';
import { CommunityService } from 'src/app/services/communityService/community.service';

@Component({
  selector: 'app-all-communitys',
  templateUrl: './all-communitys.component.html',
  styleUrls: ['./all-communitys.component.css']
})
export class AllCommunitysComponent implements OnInit {

  searchForm: FormGroup;
  searchResults: Community[] = [];

  constructor(private formBuilder: FormBuilder, private communityService: CommunityService,private router:Router) {}

  ngOnInit(): void {
    this.searchForm = this.formBuilder.group({
      name: [''],
      description: ['']
    });
    this.search();
  }

  search(): void {
    const searchCriteria: CommunitySearchCriteria = this.searchForm.value;
    this.communityService.searchCommunities(searchCriteria).subscribe(results => {
      this.searchResults = results;
    });
  }


  public showPost(community_id:number){
    this.router.navigate(['communityPosts', community_id]);
  }
}

  

