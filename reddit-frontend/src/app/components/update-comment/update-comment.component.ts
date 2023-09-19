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
  commentData: Comments

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private commentService: CommentService
  ) {
  }

  ngOnInit(): void {
    this.commentData = new Comments();

    this.comment_id = this.route.snapshot.params['comment_id'];

    this.commentService.getCommentById(this.comment_id).subscribe(data=>{
      console.log(data)
      this.commentData = data
    }, error=> console.log(error));
  }
  redirectToListOfAllDepartments(){
    this.router.navigate(['/communitys']);
  }

  updateDepartment(){
    const formData = new FormData();
    formData.append("text", this.commentData.text);
    this.commentService.updateCommunity(this.comment_id, formData).subscribe(data=>{
      console.log(data);
      this.commentData = new Comments();
      this.redirectToListOfAllDepartments();
    }, error=>console.log(error));
  }
  onSubmit(){
    this.updateDepartment();
  }


  redirectToCommentList() {
    this.router.navigate(['/home']); 
  }

}