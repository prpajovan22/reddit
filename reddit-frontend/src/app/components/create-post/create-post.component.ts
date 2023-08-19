import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Post } from 'src/app/models/Post';
import { PostServiceService } from 'src/app/services/postService/post.service';

@Component({
  selector: 'app-create-post',
  templateUrl: './create-post.component.html',
  styleUrls: ['./create-post.component.css']
})
export class CreatePostComponent{

  createPostForm: FormGroup;
  community_id: number;

  constructor(
    private route: ActivatedRoute,
    private formBuilder: FormBuilder,
    private postService: PostServiceService
  ) {
    this.community_id = this.route.snapshot.params['community_id'];
    this.createPostForm = this.formBuilder.group({
      title: ['', Validators.required],
      text: ['', Validators.required]
    });
  }

  onSubmit(): void {
    if (this.createPostForm.invalid) {
      return;
    }

    this.postService.createPost(this.community_id, this.createPostForm.value)
      .subscribe(response => {
      });
  }
}
