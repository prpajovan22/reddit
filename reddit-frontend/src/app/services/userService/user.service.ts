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

  getUserById(id:number): Observable<any>{
    return this.http.get(`http://localhost:8080/api/user/loggedin`);
    
  }

  getLoggedInUserProfile(): Observable<any> {
    return this.http.get(`http://localhost:8080/api/user/loggedin`,{withCredentials:true});
  }
  
  updateUser(id:number, user:User) : Observable<User>{
    return this.http.put<User>(`${this.apiUsersUrl}/${id}`,JSON.stringify(user), headers2);
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
}
