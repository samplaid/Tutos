import { Injectable } from '@angular/core';
import { KeycloakService } from "../../_guards/keycloak.service";

export class User {
    login: string;
    firstName: string;
    lastName: string;
    functionalities: Array<Functionality>;
}

export class Functionality {
    name: string;
}


@Injectable()
export class UserService {

    user:User;

    constructor() {        
        this.getUserProfile();
    }

    getUserProfile() {
        try {
            this.setUser( {"functionalities": KeycloakService.auth.roles.map(s => {return {"name":s};}) });
        } catch (e){
            this.setUser({"functionalities":[]});
        }
        //mock user roles
        // this.setUser({functionalities:[ 
        //                                 {name:"WEBIA_APP_FUND_FID_FAS_EDIT"}, 
        //                                 {name:"WEBIA_APP_FUND_FE_FIC_EDIT"}, 
        //                                 {name:"WEBIA_APP_CLIENT_EDIT"}, 
        //                                 {name:"WEBIA_APP_CLIENT_COMPLIANCE_EDIT"}, 
        //                                 {name:"WEBIA_APP_AGENT_COMMISSI_EDIT"}, 
        //                                 {name:"WEBIA_APP_AGENT_SB_EDIT"}, 
        //                                 {name:"WEBIA_APP_AGENT_BK_EDIT"}, 
        //                                 {name:"WEBIA_APP_AGENT_DB_EDIT"}, 
        //                                 {name:"WEBIA_APP_AGENT_AM_EDIT"}
        //                             ]});

    }

    setUser(data){
        this.user = data;
    }

    getUser(){
        return Object.assign({},this.user);
    }
 /**
 * Allow to check if a role is in the list of the user roles
 * ex: hasRole("SUPER_ADMIN")
 */
    hasRole(role: String) : boolean {
        try{
            return (this.user && this.user.functionalities && !!this.user.functionalities.find(o=> o.name == role));
        } catch (e){
            return false;          
        }
    }

    /**
    * Allow to check if current user has at least one of a given list of roles 
    * ex: hasOneRole(["ADMIN","SUPER_ADMIN"])
    */
    hasOneRole(rolesAllowed: Array<string>): boolean {
        try {
            const intersectedRoles = this.user.functionalities.reduce((acc, curr) => {
                return [
                    ...acc,
                    ...rolesAllowed.filter(role => role.trim().toUpperCase() === curr.name.trim().toUpperCase())
                ]
            }, []);
            return intersectedRoles.length > 0;
        } catch (e){
            return false;          
        }
    };

    /**
    * Allow to check if current user has All of a given list of roles 
    * ex: hasAllRoles(["PARTNER","BEARER","COMMISSION"])
    */
    hasAllRoles(roles: Array<string>): boolean {
        if(roles) {
            return roles.every(role => this.hasRole(role));
        } else {
            return false;
        }
    };

    hasNotAllRoles(roles: Array<string>): boolean {
        if(roles) {      
            return !roles.some(role => this.hasRole(role));
        } else {
            return true;
        }
    }


}