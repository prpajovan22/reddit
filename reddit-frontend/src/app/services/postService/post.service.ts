import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Post } from 'src/app/models/Post';
import { environment } from 'src/environments/environment';
import { Observable } from 'rxjs';
import { PostSearchCriteria } from 'src/app/models/Searchers/PostSearchCriteria';
import { PostRequestDto } from 'src/app/models/Searchers/PostRequestDTO';

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
export class PostServiceService {

  private apiPostUrl = `${environment.apiURL}/api/post`;

  constructor(private http: HttpClient) { }

  getPosts(): Observable<Post[]>{
    return this.http.get<Post[]>(this.apiPostUrl);
  }

  getPostById(id:number): Observable<any>{
    return this.http.get(`${this.apiPostUrl}/${id}`);
    
  }

  getPostsByCommunity(id:number): Observable<any> {
    return this.http.get(`${this.apiPostUrl}/byCommunity/${id}`);
  }

  getPostByUserId(id:number):Observable<any>{
    return this.http.get(`${this.apiPostUrl}/user/${id}`);
  }

  updatePost(post_id:number,posts: Post) : Observable<Post>{
    return this.http.put<Post>(`${this.apiPostUrl}/${post_id}`, JSON.stringify(posts),uploadHeader);

  }

  createPost(communityId: number, formData: FormData): Observable<any> {
    const url = `${this.apiPostUrl}/create/${communityId}`;
    return this.http.post(url, formData);
  }

  deletePost(id:any) : Observable<any>{
    return this.http.delete<Post>(`${this.apiPostUrl}/${id}`);
  }

  searchPosts(searchCriteria: PostSearchCriteria): Observable<Post[]> {
    return this.http.post<Post[]>(`${this.apiPostUrl}/search`, searchCriteria);
}

  getCommentsByPost(post_id: number): Observable<Comment[]> {
    return this.http.get<Comment[]>(`${this.apiPostUrl}/${post_id}/comments`);
}

upvotePost(post_id: number): Observable<any> {
  return this.http.post<any>(`${this.apiPostUrl}/upvote/${post_id}`, {});
}

downvotePost(post_id: number): Observable<any> {
  return this.http.post<any>(`${this.apiPostUrl}/downvote/${post_id}`, {});
}

getCommentCountForPost(post_id: number): Observable<number> {
  return this.http.get<number>(`${this.apiPostUrl}/${post_id}/commentCount`);
}
}

