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
    comments: Comments;

    constructor(
        private route: ActivatedRoute,
        private router: Router,
        private communityService: CommentService
    ) {
        this.comments = new Comments();
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
        const formData = new FormData();
        formData.append('text', this.comments.text);
        formData.append('post_id', this.post_id.toString()); 

        this.communityService.createComment(formData).subscribe(
            data => {
                console.log(data);
                this.comments = new Comments();
                console.log(this.comments);
                this.redirectToCommunitys();
            },
            error => console.log(error)
        );
    }

    onSubmit() {
        this.createComment();
    }
}
