import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Post } from 'src/app/models/Post';
import { environment } from 'src/environments/environment';
import { Observable } from 'rxjs';
import { PostSearchCriteria } from 'src/app/models/Searchers/PostSearchCriteria';

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

  updatePost(id:number,departments: Post) : Observable<Post>{
    return this.http.put<Post>(`${this.apiPostUrl}/${id}`, JSON.stringify(departments),uploadHeader);

  }

  createPost(departments: Post) : Observable<Post>{
    return this.http.post<Post>(this.apiPostUrl, departments, createHeader);
  }

  deletePost(id:any) : Observable<any>{
    return this.http.delete<Post>(`${this.apiPostUrl}/${id}`);
  }

  searchPosts(searchCriteria: PostSearchCriteria): Observable<Post[]> {
    return this.http.post<Post[]>(`${this.apiPostUrl}/search`, searchCriteria);
}
}

