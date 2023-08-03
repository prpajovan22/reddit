import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { LoginRequest } from 'src/app/models/LoginRequest';
import { environment } from 'src/environments/environment';
import { JwtHelperService } from '@auth0/angular-jwt';


const httpOptions = {
  headers: new HttpHeaders({
    'Content-Type': 'application/json',
  }),
};

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private apiUrl = `${environment.apiURL}/api/auth/login`;

  constructor(private http: HttpClient, public jwtHelper: JwtHelperService) { }

  login(request: LoginRequest) : Observable<LoginRequest>{
    return this.http.post<LoginRequest>(this.apiUrl,  request, httpOptions)
  }

  getToken(){
    let user = JSON.parse(localStorage.getItem('user') || '{}');
    return user;
  }

  logout(){
    localStorage.removeItem("user");
  }

  public isAuthenticated(): boolean {
    const {token} = JSON.parse(localStorage.getItem('user') || '{}');
    return !this.jwtHelper.isTokenExpired(token);
  }
}
