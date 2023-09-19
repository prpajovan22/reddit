import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Comments } from 'src/app/models/Comments';
import { Post } from 'src/app/models/Post';
import { CommentSearchCriteria } from 'src/app/models/Searchers/CommentSearchCriteria';
import { AuthService } from 'src/app/services/authService/auth.service';
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
  posts: Post = new Post();
  isUserLoggedIn:boolean;

  constructor(
    private route: ActivatedRoute,
    private formBuilder: FormBuilder,
    private postService: CommentService,
    private router: Router,
    private commentService: CommentService,
    private postService1 :PostServiceService,
    private authService :AuthService
  ) {
    this.searchForm = this.formBuilder.group({
      text: ['']
    });
  }

  ngOnInit(): void {
    this.authService.getUserLoggedIn().subscribe(value =>{this.isUserLoggedIn = value})
    const post_id = this.route.snapshot.params['post_id'];
    this.postService1.getPostById(post_id).subscribe((posts) => {
      this.posts = posts;})
    this.postService.getCommentsByPost(post_id).subscribe(comments => {
      this.comments = comments; 
      this.search();
    });
  }

  isModeratorOrAdmin(): boolean {
    const userRole = localStorage.getItem('userRole');
    return userRole === 'MODERATOR' || userRole === 'ADMIN';
}

  isAdmin(): boolean {
    const userRole = localStorage.getItem('userRole');
    return userRole === 'ADMIN';
}
  search(): void {
    const searchCriteria: CommentSearchCriteria = this.searchForm.value;
    this.searchResults = this.comments.filter(comments =>
      comments.text.includes(searchCriteria.text)
    );
  }

  navigateToReplies(comment_id: number): void {
    this.router.navigate(['/replies', comment_id], { relativeTo: this.route });
  }

  upvoteComment(comment_id: number): void {
    this.commentService.upvoteComment(comment_id).subscribe(response => {
      const updatedPost = this.searchResults.find(post => post.comment_id === comment_id);
      if (updatedPost) {
        updatedPost.netReactions++;
      }
    });
  }

  downvoteComment(comment_id: number): void {
    this.postService.downvoteComment(comment_id).subscribe(response => {
      const updatedPost = this.searchResults.find(post => post.comment_id === comment_id);
      if (updatedPost) {
        updatedPost.netReactions--; 
      }
    });
  }

  public updateComment(comment_id:number){
    this.router.navigate(['updateComment', comment_id]);
  }

  public reportComment(comment_id:number){
    this.router.navigate(['reportComment', comment_id]);
  }
  
  createReply(comment_id:number){
    this.router.navigate(['createReply', comment_id]);
  }
}