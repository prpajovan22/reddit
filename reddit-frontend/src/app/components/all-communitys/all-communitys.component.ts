import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { Router } from '@angular/router';
import { Community } from 'src/app/models/Community';
import { CommunitySearchCriteria } from 'src/app/models/Searchers/CommunitySearchCriteria';
import { AuthService } from 'src/app/services/authService/auth.service';
import { CommunityService } from 'src/app/services/communityService/community.service';

@Component({
  selector: 'app-all-communitys',
  templateUrl: './all-communitys.component.html',
  styleUrls: ['./all-communitys.component.css']
})
export class AllCommunitysComponent implements OnInit {

  searchForm: FormGroup;
  searchResults: Community[] = [];
  isUserLoggedIn:boolean;

  constructor(private formBuilder: FormBuilder, private communityService: CommunityService,
    private authService: AuthService,private router:Router) {}

  ngOnInit(): void {
    this.searchForm = this.formBuilder.group({
      name: [''],
      description: [''],
      communityPDFName: [''],
      fromPostCount: [0], 
      toPostCount: [0],
      fromReactionCount:[0],
      toReactionCount:[0]
    });
    this.search();
  }

  isModeratorOrAdmin(): boolean {
    const userRole = localStorage.getItem('userRole');
    return userRole === 'MODERATOR' || userRole === 'ADMIN';
}

  isAdmin(): boolean {
    const userRole = localStorage.getItem('userRole');
    return userRole === 'ADMIN';
}


  search(): void {
    this.authService.getUserLoggedIn().subscribe(value =>{this.isUserLoggedIn = value})
    const searchCriteria: CommunitySearchCriteria = this.searchForm.value;
    this.communityService.searchCommunities(searchCriteria).subscribe(results => {
      this.searchResults = results;

      this.fetchPostCountsForCommunities();
      this.fetchTotalReactionsForCommunities();
    });
  }

  public deleteCommunity(community_id:number){
    this.router.navigate(['suspendCommunity', community_id]);
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

  

