import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Comments } from 'src/app/models/Comments';
import { CommentService } from 'src/app/services/commentService/comment.service';
import { PostServiceService } from 'src/app/services/postService/post.service';

@Component({
  selector: 'app-all-comments',
  templateUrl: './all-comments.component.html',
  styleUrls: ['./all-comments.component.css']
})
export class AllCommentsComponent implements OnInit {

  comments: any[];

  constructor(private route:ActivatedRoute, private postService: CommentService){}

  ngOnInit(): void {
      const post_id = this.route.snapshot.params['post_id'];
      this.postService.getCommentsByPost(post_id).subscribe(data => {
        this.comments = data;
      })
  }
}