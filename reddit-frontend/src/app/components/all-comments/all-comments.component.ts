import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Comments } from 'src/app/models/Comments';
import { CommentSearchCriteria } from 'src/app/models/Searchers/CommentSearchCriteria';
import { CommentService } from 'src/app/services/commentService/comment.service';
import { PostServiceService } from 'src/app/services/postService/post.service';

@Component({
  selector: 'app-all-comments',
  templateUrl: './all-comments.component.html',
  styleUrls: ['./all-comments.component.css']
})
export class AllCommentsComponent implements OnInit {

  comments: Comments[] = [];
  searchResults: Comments[] = [];
  searchForm: FormGroup;

  constructor(
    private route: ActivatedRoute,
    private formBuilder: FormBuilder,
    private postService: CommentService,
    private router: Router
  ) {
    this.searchForm = this.formBuilder.group({
      text: ['']
    });
  }

  ngOnInit(): void {
    const post_id = this.route.snapshot.params['post_id'];
    this.postService.getCommentsByPost(post_id).subscribe(comments => {
      this.comments = comments; 
      this.search();
    });
  }

  search(): void {
    const searchCriteria: CommentSearchCriteria = this.searchForm.value;
    this.searchResults = this.comments.filter(comments =>
      comments.text.includes(searchCriteria.text)
    );
  }

  navigateToReplies(comment_id: number): void {
    this.router.navigate(['replies', comment_id], { relativeTo: this.route });
  }
}