import {ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree} from "@angular/router";
import {Observable} from "rxjs";
import {Injectable} from "@angular/core";
import {AuthService} from "../services/authentification/auth.service";


@Injectable({
  providedIn: 'root'
})
export class AuthorizationGuard implements CanActivate{

  constructor(private authService : AuthService, private router :Router) {
  }
  canActivate(route: ActivatedRouteSnapshot,
              state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {

    if(this.authService.roles == "Admin"){
      return true;
    } else {
      this.router.navigateByUrl("/notAuthorized");
      return false;
    }

  }

}
