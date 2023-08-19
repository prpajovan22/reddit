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

getRepliesForComment(comment_id: number): Observable<Comments[]> {
  return this.http.get<Comments[]>(`${this.apiCommentUrl}/${comment_id}/replies`);
}

searchComments(searchCriteria: CommentSearchCriteria): Observable<Comments[]> {
  return this.http.post<Comments[]>(`${this.apiCommentUrl}/search`, searchCriteria);
}
}
