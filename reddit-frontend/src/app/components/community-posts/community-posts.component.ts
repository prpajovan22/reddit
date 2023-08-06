import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Community } from 'src/app/models/Community';
import { Post } from 'src/app/models/Post';
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
  postCopy:Post[] = [];
  post: any;
  community: Community[] = [];
  community_id: number;

  constructor(private route: ActivatedRoute, private postService: PostServiceService,private communityService: CommunityService, private router: Router) { }


  ngOnInit(): void {
    this.community_id = this.route.snapshot.params['id'];

    this.post = {community: this.community_id, post:0};

    this.postService.getPosts().subscribe((post) => { 
    this.postCopy = post;
    this.posts = post;
    });
    
  }
}