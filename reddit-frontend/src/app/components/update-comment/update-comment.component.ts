import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Comments } from 'src/app/models/Comments';
import { CommentService } from 'src/app/services/commentService/comment.service';

@Component({
  selector: 'app-update-comment',
  templateUrl: './update-comment.component.html',
  styleUrls: ['./update-comment.component.css']
})
export class UpdateCommentComponent implements OnInit {

  comment_id: number;
  commentData: any

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private commentService: CommentService
  ) {
    this.commentData = { text: '' }; 
  }

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      this.comment_id = +params['comment_id']; 
    });
  }

  redirectToCommentList() {
    this.router.navigate(['/home']); 
  }

  updateComment() {
    this.commentService.updateCommunity(this.comment_id, this.commentData).subscribe(
      data => {
        console.log(data);
        this.redirectToCommentList();
      },
      error => {
        console.log(error);
      }
    );
  }

  onSubmit() {
    this.updateComment();
  }

}