//import { UserService } from './user/user.service';
import { RouterStateSerializer } from '@ngrx/router-store';

import { Title } from '@angular/platform-browser';
import { KeycloakHttp, KEYCLOAK_HTTP_PROVIDER } from './_guards/keycloak.http';
import { KeycloakService  } from './_guards/keycloak.service';
import { AuthGuard, RequiredRole  } from './_guards/index';
import { EditingService } from './_services/editing.service';
export const APP_PROVIDERS = [
//  UserService,
  Title,
  AuthGuard,
  KeycloakService, 
  KEYCLOAK_HTTP_PROVIDER, // this overright http native module to add token in every header
  EditingService,
];
