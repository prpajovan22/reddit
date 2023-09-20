import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { UserFront } from 'src/app/models/Searchers/UserFront';
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

  private apiUsersUrl = `${environment.apiURL}/api/user`;

  constructor(private http: HttpClient) { }


  changePassword(oldPassword: string, newPassword: string): Observable<any> {
    const payload = {
      oldPassword: oldPassword,
      newPassword: newPassword,
    };
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
      }),
    };
    return this.http.post<any>(`${this.apiUsersUrl}/change-password`, payload, httpOptions);
  }


  getUsers(): Observable<User[]>{
    return this.http.get<User[]>(`http://localhost:8080/api/user/all`);
  }

  getUsers2(): Observable<UserFront[]>{
    return this.http.get<UserFront[]>(`http://localhost:8080/api/user/all`);
  }

  getUserById(id:number): Observable<any>{
    return this.http.get(`http://localhost:8080/api/user/loggedin`);
    
  }

  getUserById2(user_id:number): Observable<any>{
    return this.http.get(`http://localhost:8080/api/user/${user_id}`);
    
  }

  getLoggedInUserProfile(): Observable<any> {
    return this.http.get(`http://localhost:8080/api/user/loggedin`,{withCredentials:true});
  }
  
  updateUser(id:number, user:User) : Observable<User>{
    return this.http.put<User>(`${this.apiUsersUrl}/${id}`,JSON.stringify(user), headers2);
  }

  updateCommunity(user_id:number,formData: FormData) : Observable<User>{
    return this.http.put<User>(`${this.apiUsersUrl}/again/${user_id}`, formData);

  }

  registerUser(korisnik: User) : Observable<User>{
    return this.http.post<User>('http://localhost:8080/api/login/registration', korisnik, createHeader);
  }

  createUser(korisnik: User) : Observable<User>{
    return this.http.post<User>(this.apiUsersUrl, korisnik, createHeader);
  }

  getUserByUsername(korisnicko: string): Observable<any>{
    return this.http.get(`${this.apiUsersUrl}/korisnicko/${korisnicko}`);
  }

  switchUserRole(user_id: number): Observable<string> {
    const url = `${this.apiUsersUrl}/switch-role/${user_id}`;
    return this.http.post<string>(url, {});
  }

  suspendUser(user_id: number): Observable<string> {
    const url = `${this.apiUsersUrl}/suspend-user/${user_id}`;
    return this.http.put<string>(url, {});
  }

  returnUser(user_id: number): Observable<string> {
    const url = `${this.apiUsersUrl}/return/${user_id}`;
    return this.http.put<string>(url, {});
  }
}
