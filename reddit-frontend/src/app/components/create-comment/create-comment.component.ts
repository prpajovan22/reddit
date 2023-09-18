import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Comments } from 'src/app/models/Comments';
import { CommentService } from 'src/app/services/commentService/comment.service';

@Component({
  selector: 'app-create-comment',
  templateUrl: './create-comment.component.html',
  styleUrls: ['./create-comment.component.css']
})
export class CreateCommentComponent implements OnInit {

  post_id: number;
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
            this.post_id = +params['post_id'];
        });
    }

    redirectToCommunitys() {
        this.router.navigate(['/home']);
    }

    createComment(): void {
        const commentData = { text: this.comments.text }; // Use an object for JSON data
    
        this.communityService.createComment(this.post_id, commentData).subscribe(
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
