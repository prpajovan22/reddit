import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Community } from 'src/app/models/Community';
import { Post } from 'src/app/models/Post';
import { Reaction } from 'src/app/models/Reaction';
import { PostSearchCriteria } from 'src/app/models/Searchers/PostSearchCriteria';
import { AuthService } from 'src/app/services/authService/auth.service';
import { CommunityService } from 'src/app/services/communityService/community.service';
import { PostServiceService } from 'src/app/services/postService/post.service';

@Component({
  selector: 'app-community-posts',
  templateUrl: './community-posts.component.html',
  styleUrls: ['./community-posts.component.css']
})
export class CommunityPostsComponent implements OnInit {

  searchForm: FormGroup;
  posts: Post[] = [];
  searchResults: Post[] = [];

  constructor(
    private route: ActivatedRoute,
    private formBuilder: FormBuilder,
    private postService: PostServiceService,
    private router: Router
  ) {
    this.searchForm = this.formBuilder.group({
      text: [''],
      title: [''],
      fromReactionCount: [0], 
      toReactionCount: [0],
      community_id:0
    });
  }

  ngOnInit(): void {
    const community_id = this.route.snapshot.params['id'];
    this.searchForm.patchValue({community_id:community_id})
    this.search();
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

  search(): void {
    const searchCriteria: PostSearchCriteria = this.searchForm.value;
    this.postService.searchPosts(searchCriteria).subscribe(results => {
      this.searchResults = results;
      
      this.fetchCommentCountsForPost();
    });
  }

  public showComments(post_id:number){
    this.router.navigate(['showComments', post_id]);
  }

  calculateTotalReactions(reactions: Reaction[]): number {
    if (!reactions) {
      return 0;
    }
  
    return reactions.length;
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
}}