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
      description: [''],
      communityPDF: [''],
      fromPostCount: [0], 
      toPostCount: [0],
      fromReactionCount:[0],
      toReactionCount:[0]
    });
    this.search();
  }


  search(): void {
    const searchCriteria: CommunitySearchCriteria = this.searchForm.value;
    this.communityService.searchCommunities(searchCriteria).subscribe(results => {
      this.searchResults = results;

      this.fetchPostCountsForCommunities();
      this.fetchTotalReactionsForCommunities();
    });
  }

  deleteCommunity(community_id: any): void {
    this.communityService.deleteCommunity(community_id).subscribe(() => {
      this.searchResults = this.searchResults.filter((community) => community.community_id !== community_id);
    });
  }

  private async fetchPostCountsForCommunities(): Promise<void> {
    for (const community of this.searchResults) {
      try {
        const count = await this.communityService.getPostCountForCommunity(community.community_id).toPromise();
        community.postCount = count;
      } catch (error) {
        console.error(`Error fetching post count for community ${community.name}:`, error);
      }
    }
  }

  private async fetchTotalReactionsForCommunities(): Promise<void> {
    for (const community of this.searchResults) {
      try {
        const totalReactions = await this.communityService.getTotalReactionsForCommunity(community.community_id).toPromise();
        community.totalReaction = totalReactions;
      } catch (error) {
        console.error(`Error fetching total reactions for community ${community.name}:`, error);
      }
    }
  }

  public createCommunity() {
    this.router.navigate(['createCommunity']);
  }

  public showPost(community_id:number){
    this.router.navigate(['communityPosts', community_id]);
  }

  public createPost(community_id:number){
    this.router.navigate(['createPost', community_id]);
  }

  public updateCommunity(community_id:number){
    this.router.navigate(['updateCommunity', community_id]);
  }

  
}

  

