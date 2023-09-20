import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Post } from 'src/app/models/Post';
import { PostServiceService } from 'src/app/services/postService/post.service';

@Component({
  selector: 'app-update-post',
  templateUrl: './update-post.component.html',
  styleUrls: ['./update-post.component.css']
})
export class UpdatePostComponent implements OnInit {

  post_id:number;
  posts: Post;

  constructor(private route: ActivatedRoute, private router: Router, private postService: PostServiceService) { }

  ngOnInit(): void {
    this.posts = new Post();

    this.post_id = this.route.snapshot.params['post_id'];

    this.postService.getPostById(this.post_id).subscribe(data=>{
      console.log(data)
      this.posts = data
    }, error => console.log(error));
  }
  redirectToListOfAllPosts(){
    this.router.navigate(['/home']);
  }

  updateDepartment(){
    const formData = new FormData();
    formData.append("postPDFPath", this.posts.postPDFPath);
    formData.append("title", this.posts.title);
    formData.append("text", this.posts.text);
    this.postService.updatePost(this.post_id, formData).subscribe(data=>{
      console.log(data);
      this.posts = new Post();
      this.redirectToListOfAllPosts();
    }, error=>console.log(error));
  }
  onSubmit(){
    this.updateDepartment();
  }

  handleFileInput(event: any) {
    this.posts.postPDFPath = event.target.files[0];
}
}