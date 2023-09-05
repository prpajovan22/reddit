import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Comments } from 'src/app/models/Comments';
import { CommentService } from 'src/app/services/commentService/comment.service';

@Component({
  selector: 'app-replies',
  templateUrl: './replies.component.html',
  styleUrls: ['./replies.component.css']
})
export class RepliesComponent implements OnInit {

  comment_id: number;
  replies: Comments[];

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private commentService: CommentService
  ) {
    
  
  }

  ngOnInit(): void {
    this.route.params.subscribe((param) =>{
      if(param && param["comment_id"]) {
        this.comment_id = this.route.snapshot.params['comment_id'];
        this.commentService.getRepliesForComment(this.comment_id).subscribe(replies => {
          this.replies = replies;
        });
      }
    })
  }

  navigateToReplies(commentId: number): void {
    this.router.navigate(['/replies', commentId], { relativeTo: this.route });
  }
}
