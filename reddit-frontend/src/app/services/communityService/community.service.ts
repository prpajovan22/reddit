import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Community } from 'src/app/models/Community';
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

  updateCommunity(id:number,communitys: Community) : Observable<Community>{
    return this.http.put<Community>(`${this.apiCommunityUrl}/${id}`, JSON.stringify(communitys),uploadHeader);

  }

  createCommunity(communitys: Community) : Observable<Community>{
    return this.http.post<Community>(this.apiCommunityUrl, communitys, createHeader);
  }

  deleteCommunity(id:any) : Observable<any>{
    return this.http.delete<Community>(`${this.apiCommunityUrl}/${id}`);
  }
}