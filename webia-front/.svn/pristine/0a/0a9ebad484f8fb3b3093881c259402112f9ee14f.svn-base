import { Title } from '@angular/platform-browser';
import { Component, OnInit, HostListener, Inject, Input  } from '@angular/core';
import {KeycloakService} from '../../_guards/keycloak.service';
import { UserService } from '../../utils';
import { Roles } from '../../_models';

@Component({
  selector: 'search-menu',
  providers: [],
  templateUrl: './search-menu.tpl.html',
  styleUrls: ['./search-menu.style.scss']
})
export class SearchMenuComponent {

  public isCollapsed: boolean = true;
  public navIsFixed: boolean = false;
  public navheight: number = 100;

  hasCommissionMgmt: boolean = false;
  hasComptaRole: boolean;

   @Input() path:string = '.';

  constructor(private kc :KeycloakService, private titleService: Title, private userService: UserService) { 
  }

  ngOnInit() { 
    this.setTitle("Search page");
    this.hasCommissionMgmt = this.userService.hasRole(Roles.COMMISSION_MANAGEMENT);
    this.hasComptaRole = this.userService.hasRole(Roles.COMPTA);
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

    public setTitle(newTitle: string) {
        this.titleService.setTitle(newTitle);
    }
}


