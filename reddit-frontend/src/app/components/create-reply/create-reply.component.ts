import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { CommentService } from 'src/app/services/commentService/comment.service';

@Component({
  selector: 'app-create-reply',
  templateUrl: './create-reply.component.html',
  styleUrls: ['./create-reply.component.css']
})
export class CreateReplyComponent implements OnInit {

  comment_id: number;
  comments: any;

    constructor(
        private route: ActivatedRoute,
        private router: Router,
        private communityService: CommentService
    ) {
        this.comments = { text: '' };
    }

    ngOnInit(): void {
        this.route.params.subscribe(params => {
            this.comment_id = +params['comment_id'];
        });
    }

    redirectToCommunitys() {
        this.router.navigate(['/home']);
    }

    createComment(): void {
        const commentData = { text: this.comments.text }; // Use an object for JSON data
    
        this.communityService.createReply(this.comment_id, commentData).subscribe(
          data => {
            console.log(data);
            this.comments.text = ''; // Clear the text field
            this.redirectToCommunitys();
          },
          error => console.log(error)
        );
      }

    onSubmit() {
        this.createComment();
    }
}