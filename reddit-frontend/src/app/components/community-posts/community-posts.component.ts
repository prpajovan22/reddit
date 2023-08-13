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

  posts: any[];

  constructor(private route:ActivatedRoute, private postService: PostServiceService){}

  ngOnInit(): void {
      const community_id = this.route.snapshot.params['id'];
      this.postService.getPostsByCommunity(community_id).subscribe(data => {
        this.posts = data;
      })
  }
}