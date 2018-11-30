import { Injectable } from '@angular/core';
import { Router, CanActivate, CanActivateChild , ActivatedRouteSnapshot, RouterStateSnapshot } from '@angular/router';
import { Headers, RequestOptions } from '@angular/http';
import {MessageService, UserService} from '../utils';
import {Observable}  from 'rxjs/Observable';

export enum RequiredRole {ALL,ONE} 

@Injectable()
export class AuthGuard implements CanActivate  {

    private _storageItem = 'wealins_partner';
    private _ACCESS_UNAUTHORIZED = "Unauthorized Access to this page !";

    constructor(private router: Router, 
                private messageService:MessageService , 
                private user: UserService) { 

    }


    canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<boolean>|Promise<boolean>|boolean {    
        let roles = route.data["roles"] as Array<string>;
        let required = route.data["required"] as RequiredRole;
        if (required == null)
            return true;
        if (required == RequiredRole.ONE){
            if (this.user.hasOneRole(roles)){
                return true;
            } else {
                this.messageService.error([this._ACCESS_UNAUTHORIZED]);                 
                return false;
            }
        }
            
        if (required == RequiredRole.ALL && this.user.hasAllRoles(roles)) {
            return true;
        }                

        this.messageService.error([this._ACCESS_UNAUTHORIZED]);
        return false;
    } 

     ngOnDestroy(){
         
     }

}