import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Comments } from 'src/app/models/Comments';
import { CommentSearchCriteria } from 'src/app/models/Searchers/CommentSearchCriteria';
import { environment } from 'src/environments/environment';

const createHeader = {
  headers: new HttpHeaders({
    'method':'POST',
    'Content-Type': 'application/json',
  }),
};

const uploadHeader = {
  headers: new HttpHeaders({
    'method':'PUT',
    'Content-Type': 'application/json',
  }),
};

@Injectable({
  providedIn: 'root'
})
export class CommentService {

  private apiPostUrl = `${environment.apiURL}/api/post`;

  private apiCommentUrl = `${environment.apiURL}/api/comment`;

  constructor(private http: HttpClient) { }

getCommentsByPost(postId: number): Observable<Comments[]> {
  return this.http.get<Comments[]>(`${this.apiPostUrl}/${postId}/comments`);
}

getCommentById(comment_id_id:number): Observable<any>{
  return this.http.get(`${this.apiCommentUrl}/${comment_id_id}`);
  
}

updateCommunity(comment_id:number,formData: FormData) : Observable<Comments>{
  return this.http.put<Comments>(`${this.apiCommentUrl}/${comment_id}`, formData);

}

getRepliesForComment(comment_id: number): Observable<Comments[]> {
  return this.http.get<Comments[]>(`${this.apiCommentUrl}/${comment_id}/replies`);
}

searchComments(searchCriteria: CommentSearchCriteria): Observable<Comments[]> {
  return this.http.post<Comments[]>(`${this.apiCommentUrl}/search`, searchCriteria);
}

upvoteComment(comment_id: number): Observable<any> {
  return this.http.post<any>(`${this.apiCommentUrl}/upvote/${comment_id}`, {});
}

downvoteComment(comment_id: number): Observable<any> {
  return this.http.post<any>(`${this.apiCommentUrl}/downvote/${comment_id}`, {});
}

createComment(post_id: number, commentData: any): Observable<any> {
  const url = `${this.apiCommentUrl}/createComment/${post_id}`; 
  return this.http.post(url, commentData);
}

createReply(comment_id: number, commentData: any): Observable<any> {
  const url = `${this.apiCommentUrl}/createReply/${comment_id}`; 
  return this.http.post(url, commentData);
}
}
