import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { Router } from '@angular/router';
import { Post } from 'src/app/models/Post';
import { Reaction } from 'src/app/models/Reaction';
import { PostSearchCriteria } from 'src/app/models/Searchers/PostSearchCriteria';
import { AuthService } from 'src/app/services/authService/auth.service';
import { CommunityService } from 'src/app/services/communityService/community.service';
import { PostServiceService } from 'src/app/services/postService/post.service';

@Component({
  selector: 'app-all-posts',
  templateUrl: './all-posts.component.html',
  styleUrls: ['./all-posts.component.css']
})
export class AllPostsComponent implements OnInit {

  searchForm: FormGroup;
  posts: Post[] = [];
  searchResults: Post[] = [];
  user: any;
  isUserLoggedIn:boolean;

  constructor(private formBuilder: FormBuilder,
     private postService: PostServiceService,private router:Router,
     private communityService :CommunityService,
     private authService:AuthService) {}

  ngOnInit(): void {
    this.user = this.authService.getLoggedInUser();
    this.searchForm = this.formBuilder.group({
      text: [''],
      title: [''],
      descriptionPDF:[''],
      fromReactionCount: [0], 
      toReactionCount: [0],
      fromCommentCount:[0],
      toCommentCount:[0]
    });
    this.search();
  }

  search(): void {
    this.authService.getUserLoggedIn().subscribe(value =>{this.isUserLoggedIn = value})
    const searchCriteria: PostSearchCriteria = this.searchForm.value;
    this.postService.searchPosts(searchCriteria).subscribe(results => {
      this.searchResults = results;      
      this.fetchCommentCountsForPost();
    });
  }

  private async fetchCommentCountsForPost(): Promise<void> {
    for (const post of this.searchResults) {
      try {
        const count = await this.postService.getCommentCountForPost(post.post_id).toPromise();
        post.commentCount = count;
      } catch (error) {
        console.error(`Error fetching post count for community ${post.title}:`, error);
      }
    }
  }

  isModeratorOrAdmin(): boolean {
    const userRole = localStorage.getItem('userRole');
    return userRole === 'MODERATOR' || userRole === 'ADMIN';
}

  isAdmin(): boolean {
    const userRole = localStorage.getItem('userRole');
    return userRole === 'ADMIN';
}
  
  navigateToCommunity(community_id: number): void {
    this.router.navigate(['one-community', community_id]);
  }

  public updatePost(post_id:number){
    this.router.navigate(['updatePost', post_id]);
  }

  public createPost(community_id) {
    this.router.navigate(['createPost',community_id]);
  }

  public showComments(post_id:number){
    this.router.navigate(['showComments', post_id]);
  }

  navigateToCommentCreation(post_id:number){
    this.router.navigate(['createComment', post_id]);
  }

  upvotePost(post_id: number): void {
    this.postService.upvotePost(post_id).subscribe(response => {
      const updatedPost = this.searchResults.find(post => post.post_id === post_id);
      if (updatedPost) {
        updatedPost.netReactions++;
      }
    });
  }

  downvotePost(post_id: number): void {
    this.postService.downvotePost(post_id).subscribe(response => {
      const updatedPost = this.searchResults.find(post => post.post_id === post_id);
      if (updatedPost) {
        updatedPost.netReactions--; 
      }
    });
  }

  
  public reportPost(post_id:number){
    this.router.navigate(['reportPost', post_id]);
  }

}
