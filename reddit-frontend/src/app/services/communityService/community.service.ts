import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Community } from 'src/app/models/Community';
import { CommunitySearchCriteria } from 'src/app/models/Searchers/CommunitySearchCriteria';
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
export class CommunityService {

  private apiCommunityUrl = `${environment.apiURL}/community`;

  constructor(private http: HttpClient) { }

  getCommunitys(): Observable<Community[]>{
    return this.http.get<Community[]>(this.apiCommunityUrl);
  }

  getCommunityById(id:number): Observable<any>{
    return this.http.get(`${this.apiCommunityUrl}/${id}`);
    
  }

  getCommunityByPostId(post_id: number): Observable<Community> {
    const url = `${this.apiCommunityUrl}/community/${post_id}`;
    return this.http.get<Community>(url);
  }

  updateCommunity(community_id:number,communitys: Community) : Observable<Community>{
    return this.http.put<Community>(`${this.apiCommunityUrl}/${community_id}`, JSON.stringify(communitys),uploadHeader);

  }

  createCommunity(communitys: Community) : Observable<Community>{
    return this.http.post<Community>(this.apiCommunityUrl, communitys, createHeader);
  }

  deleteCommunity(id:any) : Observable<any>{
    return this.http.delete<Community>(`${this.apiCommunityUrl}/${id}`);
  }

  searchCommunities(searchCriteria: CommunitySearchCriteria): Observable<Community[]> {
    return this.http.post<Community[]>(`${this.apiCommunityUrl}/search`, searchCriteria);
}

getPostCountForCommunity(communityId: number): Observable<number> {
  return this.http.get<number>(`${this.apiCommunityUrl}/${communityId}/postCount`);
}

getTotalReactionsForCommunity(communityId: number): Observable<number> {
  const url = `${this.apiCommunityUrl}/${communityId}/totalReactions`;
  return this.http.get<number>(url);
}

getCommunity(communityId: number): Observable<any> {
  const url = `${this.apiCommunityUrl}/${communityId}`;
  return this.http.get<any>(url);
}
}