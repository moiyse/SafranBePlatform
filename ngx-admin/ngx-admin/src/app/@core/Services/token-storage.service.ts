import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { User } from '../Models/User';


const TOKEN_KEY = 'auth-token';
const USER_INFO = 'auth-user'

@Injectable({
  providedIn: 'root'
})
export class TokenStorageService {

  constructor(private router:Router) { }

  signOut(): void {
    localStorage.clear();
    this.router.navigateByUrl("/auth")
  }

  public saveToken(token: string): void {
    localStorage.removeItem(TOKEN_KEY);
    localStorage.setItem(TOKEN_KEY, token);
  }

  public getToken(): string {
    return localStorage.getItem(TOKEN_KEY);
  }

  /*public saveUser(user:any): void {
    localStorage.removeItem(USER_KEY);
    localStorage.setItem(USER_KEY, JSON.stringify(user));
  }*/

  public saveUser(user:any): void {
    localStorage.removeItem(USER_INFO);
    console.log("user type",typeof user)
    localStorage.setItem(USER_INFO,user);
  }

  public updateUser(user:any): void {
    localStorage.removeItem(USER_INFO);
    console.log("user type",typeof user)
    localStorage.setItem(USER_INFO, JSON.stringify(user));
  }

  public getUser(): any {
    return JSON.parse(localStorage.getItem(USER_INFO));
  }

  public updateSignature(signature:String):any {
    let user = this.getUser();
    user.signature = signature
    this.updateUser(user);
  }

}