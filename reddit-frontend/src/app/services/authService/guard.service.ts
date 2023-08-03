import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, Router } from '@angular/router';
import { AuthService } from './auth.service';
import jwtDecode from 'jwt-decode';

@Injectable({
  providedIn: 'root'
})
export class GuardService implements CanActivate {

  constructor(public auth: AuthService, public router: Router) {}

  canActivate(route: ActivatedRouteSnapshot): boolean {

    const expectedRole = route.data['expectedRole'];// ovde je problem takodje
    const {token} = JSON.parse(localStorage.getItem('user') || '{}');

    const tokenPayload:any = jwtDecode(token);
    console.log(expectedRole)
    if (
      !this.auth.isAuthenticated() || 
      tokenPayload.role.authority !== expectedRole
    ) {
      this.router.navigate(['login']);
      return false;
    }
    return true;
  }
}
