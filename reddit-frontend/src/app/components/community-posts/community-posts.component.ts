import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Community } from 'src/app/models/Community';
import { Post } from 'src/app/models/Post';
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

  posts: Post[] = [];
  searchResults: Post[] = [];
  searchForm: FormGroup;

  constructor(
    private route: ActivatedRoute,
    private formBuilder: FormBuilder,
    private postService: PostServiceService,
    private router: Router
  ) {
    this.searchForm = this.formBuilder.group({
      title: [''],
      text: ['']
    });
  }

  ngOnInit(): void {
    const community_id = this.route.snapshot.params['id'];
    this.postService.getPostsByCommunity(community_id).subscribe(posts => {
      this.posts = posts;
      this.search(); 
    });
  }

  search(): void {
    const searchCriteria: PostSearchCriteria = this.searchForm.value;
    this.searchResults = this.posts.filter(post =>
      post.title.includes(searchCriteria.title) && post.text.includes(searchCriteria.text)
    );
  }

  public showComments(post_id:number){
    this.router.navigate(['showComments', post_id]);
  }
}