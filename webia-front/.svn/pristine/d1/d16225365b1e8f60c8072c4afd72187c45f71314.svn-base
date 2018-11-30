import {Injectable} from '@angular/core';
import * as Keycloak from 'keycloak';

//declare var Keycloak: any;

@Injectable()
export class KeycloakService {

  static auth: any = {};



  static init(): Promise<any> {
    const keycloakAuth: any = Keycloak('./keycloak.json');
    KeycloakService.auth.loggedIn = false;

    return new Promise((resolve, reject) => {
      keycloakAuth.init({ onLoad: 'login-required', checkLoginIframe: false })
      .success((resp) => {
        KeycloakService.auth.loggedIn = resp;
        KeycloakService.auth.authz = keycloakAuth;
        KeycloakService.auth.logoutUrl = `${keycloakAuth.authServerUrl}/realms/${keycloakAuth.realm}/protocol/openid-connect/logout?redirect_uri=/`;
        KeycloakService.auth.resetPasswordUrl = `${keycloakAuth.authServerUrl}/realms/${keycloakAuth.realm}/login-actions/reset-credentials`;
        if (KeycloakService.auth.authz.resourceAccess[KeycloakService.auth.authz.clientId])
          KeycloakService.auth.roles = KeycloakService.auth.authz.resourceAccess[KeycloakService.auth.authz.clientId].roles;
        else
          KeycloakService.auth.roles = [];
        resolve();
      })
      .error(() => {
        reject();
      });
    });
  }

  logout() {
    console.log('*** LOGOUT');
    KeycloakService.auth.loggedIn = false;
    KeycloakService.auth.authz.logout();
  }

  changePassword(){
    KeycloakService.auth.loggedIn = false;
    KeycloakService.auth.authz = null;
    window.location.href = KeycloakService.auth.resetPasswordUrl;
    //let url = KeycloakService.auth.authz.authServerUrl+"/realms/Wealins/login-actions/reset-credentials";
    //window.location.href = url;
  }
  
  getLogin(){
    return KeycloakService.auth.authz.tokenParsed.preferred_username;
  }

  getToken(): Promise<string> {
    return new Promise<string>((resolve, reject) => {
      if (KeycloakService.auth.authz && KeycloakService.auth.authz.token) {
        KeycloakService.auth.authz
          .updateToken(5)
          .success(() => {
            resolve(<string>KeycloakService.auth.authz.token);
          })
          .error(() => {
            KeycloakService.auth.authz.logout();
            reject('Failed to refresh token');
          });
      } else {
        KeycloakService.auth.authz.logout();
        reject('Not loggen in');
      }
    });
  }
}