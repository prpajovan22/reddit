import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { Router } from '@angular/router';
import { Post } from 'src/app/models/Post';
import { PostSearchCriteria } from 'src/app/models/Searchers/PostSearchCriteria';
import { PostServiceService } from 'src/app/services/postService/post.service';

@Component({
  selector: 'app-all-posts',
  templateUrl: './all-posts.component.html',
  styleUrls: ['./all-posts.component.css']
})
export class AllPostsComponent implements OnInit {

  searchForm: FormGroup;
  searchResults: Post[] = [];

  constructor(private formBuilder: FormBuilder, private postService: PostServiceService,private router:Router) {}

  ngOnInit(): void {
    this.searchForm = this.formBuilder.group({
      title: [''],
      text: ['']
    });
    this.search();
  }

  search(): void {
    const searchCriteria: PostSearchCriteria = this.searchForm.value;
    this.postService.searchPosts(searchCriteria).subscribe(results => {
      this.searchResults = results;
    });
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
    response.subscribe((searchResults)=> this.searchResults = searchResults)
  }

  public showComments(post_id:number){
    this.router.navigate(['showComments', post_id]);
  }

}
