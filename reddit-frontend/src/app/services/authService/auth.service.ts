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

  private apiUrl = `${environment.apiURL}/api/login/signIn`;

  constructor(private http: HttpClient, public jwtHelper: JwtHelperService) { }

  login(request: LoginRequest) : Observable<AuthenticationResponse>{
    return this.http.post<AuthenticationResponse>(this.apiUrl,  request, httpOptions)
  }


  getToken(){
    let user = JSON.parse(localStorage.getItem('token') || '{}');
    return user;
  }

  logout(){
    localStorage.removeItem("token");
  }

  public isAuthenticated(): boolean {
    const {token} = JSON.parse(localStorage.getItem('token') || '{}');
    return !this.jwtHelper.isTokenExpired(token);
  }
  
}
export interface AuthenticationResponse{
  token:string
}
