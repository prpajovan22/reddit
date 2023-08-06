import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Post } from 'src/app/models/Post';
import { PostServiceService } from 'src/app/services/postService/post.service';

@Component({
  selector: 'app-create-post',
  templateUrl: './create-post.component.html',
  styleUrls: ['./create-post.component.css']
})
export class CreatePostComponent implements OnInit {

  community_id:number;
  post_id:number;
  posts: Post;

  constructor(private route: ActivatedRoute, private router: Router, private postService: PostServiceService) { }

  ngOnInit(): void {
    this.posts = new Post();
  }
  redirectToPosts(){
    this.router.navigate(['/home']);
  }

  createPost(){
    this.postService.createPost(this.posts).subscribe(data=>{
      console.log(data);
      this.posts = new Post();
      console.log(this.posts);
      this.redirectToPosts();
    }, error=>console.log(error));
  }
  onSubmit(){
    this.createPost();
  }
}
