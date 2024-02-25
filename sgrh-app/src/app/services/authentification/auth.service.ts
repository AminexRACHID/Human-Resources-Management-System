import { Injectable } from '@angular/core';
import {HttpClient, HttpParams} from "@angular/common/http";
import {jwtDecode} from "jwt-decode";
import {Router} from "@angular/router";

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  isAuthenticated : boolean = false;
  roles : any;
  username : any;
  accessToken : any;

  constructor(private http:HttpClient, private router:Router) { }


  public login(email:string, password:string){
    let params= new HttpParams().set("username",email).set("password",password);
    // console.log(params);
    let bod = {"username":email,"password":password}
    return this.http.post("http://localhost:8090/api/auth/login", bod)
  }

  public create(form:any){
    return this.http.post("http://localhost:8090/manage/user", form)
  }


  loadProfile(data:any) {
    this.isAuthenticated = true
    this.accessToken = data['token']
    let decodeJwt : any = jwtDecode(this.accessToken);
    this.username = decodeJwt.sub;
    this.roles = decodeJwt.roles;
    // console.log(this.roles);
    window.localStorage.setItem("jwt-token",this.accessToken);
  }

  logout() {
    this.isAuthenticated=false;
    this.accessToken= undefined;
    this.username = undefined;
    this.roles = undefined;
    window.localStorage.removeItem("jwt-token");
    this.router.navigateByUrl("/login")
  }

    deleteToken() {
        this.isAuthenticated=false;
        this.accessToken= undefined;
        this.username = undefined;
        this.roles = undefined;
        window.localStorage.removeItem("jwt-token");
    }

  loadJwtTokenFromLocalStorage() {
    let token = window.localStorage.getItem("jwt-token");
    if(token){
      this.loadProfile({"token":token})
      // this.router.navigateByUrl("/admin/all-employees");
    }
  }
}
