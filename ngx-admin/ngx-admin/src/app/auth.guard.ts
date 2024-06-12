import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree } from '@angular/router';
import { Observable } from 'rxjs';
import { TokenStorageService } from './@core/Services/token-storage.service';
import jwt_decode from 'jwt-decode'

@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {


  constructor(private router:Router,private tokenStorage: TokenStorageService){

  }
  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
      const targetUrl = state.url;


      if (this.tokenStorage.getToken() != null || this.tokenStorage.getUser() != null) {
        
        console.log("user from stroage : ",this.tokenStorage.getUser())
        /*var decoded:any = jwt_decode(this.tokenStorage.getToken());
        console.log("user from token : ",decoded.data)
        if(this.tokenStorage.getUser().id == decoded.data.id)
        {
          console.log('in the condition')
          return true;
        }*/
        console.log("signature from localstorage : ",this.tokenStorage.getUser().signature)
        console.log("signature : ",this.tokenStorage.getUser().signature)
        if(this.tokenStorage.getUser().signature == null && state.url != "/auth/signature")
        {
          this.router.navigateByUrl("/auth/signature")
          return false
        }
        else if(this.tokenStorage.getUser().signature != null && state.url == "/auth/signature") {
          this.router.navigateByUrl("/")
          return false
        }
        else
        {
          return true;
        }
        /*this.router.navigateByUrl("/")
        this.tokenStorage.signOut()
        return false;*/
      }else{
        this.router.navigateByUrl("/auth")
        return false;
      }
  }
  
}