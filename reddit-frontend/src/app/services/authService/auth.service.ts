import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { LoginRequest } from 'src/app/models/LoginRequest';
import { environment } from 'src/environments/environment';
import { JwtHelperService } from '@auth0/angular-jwt';

export interface AuthenticationResponse{
  message:string,
  successfull:boolean,
  role:string
}

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

  private userLoggedIn = new BehaviorSubject<boolean>(false);

  constructor(private http: HttpClient, public jwtHelper: JwtHelperService) { }

  setUserLoggedIn(value: boolean){
    this.userLoggedIn.next(value);
  }
  getUserLoggedIn(){
    return this.userLoggedIn.asObservable();
  }

  login(request: LoginRequest) : Observable<AuthenticationResponse>{
    return this.http.post<AuthenticationResponse>(this.apiUrl,  request, httpOptions)
  }



  getToken(){
    let user = JSON.parse(localStorage.getItem('token') || '{}');
    return user;
  }

  isUserLoggedIn(){
    return localStorage.getItem("isUserLoggedIn") == "true";
  }

  logout(){
    localStorage.removeItem("isUserLoggedIn");
    localStorage.removeItem("role");
  }

  public isAuthenticated(): boolean {
    const {token} = JSON.parse(localStorage.getItem('token') || '{}');
    return !this.jwtHelper.isTokenExpired(token);
  }
  
}
