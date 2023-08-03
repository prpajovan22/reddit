import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Post } from 'src/app/models/Post';
import { PostServiceService } from 'src/app/services/postService/post.service';

@Component({
  selector: 'app-all-posts',
  templateUrl: './all-posts.component.html',
  styleUrls: ['./all-posts.component.css']
})
export class AllPostsComponent implements OnInit {

  posts:Post[] = [];
  post:any;

  constructor(private postService:PostServiceService, private router:Router) { }

  ngOnInit(): void {
    this.postService.getPosts().subscribe((posts) => this.posts = posts)
  }

  public updatePost(post_id:number){
    this.router.navigate(['updatePost', post_id]);
  }

  public createPost() {
    this.router.navigate(['createPost']);
  }

  public deletePost(post_id:number) {
    console.log(post_id);
    let response = this.postService.deletePost(post_id);
    response.subscribe((posts)=> this.posts = posts)
  }

}
