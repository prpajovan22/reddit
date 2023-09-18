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
      text: ['', Validators.required],
      postPDFPath: [null] 
    });
  }

  onSubmit(): void {
    if (this.createPostForm.invalid) {
      return;
    }

    const formData = new FormData();
    formData.append('title', this.createPostForm.get('title').value);
    formData.append('text', this.createPostForm.get('text').value);
    formData.append('community_id', this.community_id.toString());
    formData.append('postPDFPath', this.createPostForm.get('postPDFPath').value);

    this.postService.createPost(this.community_id, formData)
      .subscribe(response => {
        // Handle the response from your backend
      });
  }

  onFileChange(event: any): void {
    if (event.target.files.length > 0) {
      const file = event.target.files[0];
      this.createPostForm.get('postPDFPath').setValue(file);
    }
  }
}