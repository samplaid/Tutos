import { Component, OnInit  } from '@angular/core';
import {KeycloakService} from '../_guards/keycloak.service';

@Component({
  selector: 'menu-dev',
  providers: [],
  templateUrl: './menu-dev.tpl.html',
  styleUrls: ['./menu-dev.style.scss']
})
export class MenuDEVComponent {

  public isCollapsed: boolean = true;
  public navIsFixed: boolean = false;
  public navheight: number = 100;


  constructor( private kc :KeycloakService) { }

  ngOnInit() {
  }

toggleCollapse(): void {
  this.isCollapsed = !this.isCollapsed;
}


  logout(){
    this.kc.logout();
  }
   
  changePassword(){
    this.kc.changePassword();
  }   

}


