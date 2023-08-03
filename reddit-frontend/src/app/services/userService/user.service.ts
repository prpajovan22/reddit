import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { User } from 'src/app/models/User';
import { environment } from 'src/environments/environment';

const headers2 = {
  headers: new HttpHeaders({
    'method':'PUT',
    'Content-Type': 'application/json',
  }),
};

const createHeader = {
  headers: new HttpHeaders({
    'method':'POST',
    'Content-Type': 'application/json',
  }),
};

@Injectable({
  providedIn: 'root'
})
export class UserServiceService {

  private apiUsersUrl = `${environment.apiURL}/korisnici`;

  constructor(private http: HttpClient) { }

  getUsers(): Observable<User[]>{
    return this.http.get<User[]>(this.apiUsersUrl);
  }

  getUserById(id:number): Observable<any>{
    return this.http.get(`${this.apiUsersUrl}/${id}`);
    
  }

  updateUser(id:number, user:User) : Observable<User>{
    return this.http.put<User>(`${this.apiUsersUrl}/${id}`,JSON.stringify(user), headers2);
  }

  createUser(korisnik: User) : Observable<User>{
    return this.http.post<User>(this.apiUsersUrl, korisnik, createHeader);
  }

  getUserByUsername(korisnicko: string): Observable<any>{
    return this.http.get(`${this.apiUsersUrl}/korisnicko/${korisnicko}`);
  }
}
