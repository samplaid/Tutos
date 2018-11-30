import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { TransferState } from '../modules/transfer-state/transfer-state';

import { views } from './app-nav-views';
import { MOBILE } from './services/constants';

import '../styles/styles.scss';
import { ConfigService } from "./utils/index";

@Component({
  selector: 'my-app',
  styleUrls: [
    './app.component.scss',    
    '../../node_modules/animate.css/animate.min.css',
    '../../node_modules/ngx-bootstrap/datepicker/bs-datepicker.css',
    '../../node_modules/ng-busy/src/style/busy.css'
  ],
  template: `

  <div class="browserChrome alert alert-warning" *ngIf="!isChrome"><i class=" text-danger fa fa-exclamation-triangle fa-2x" aria-hidden="true"></i><strong>Warning!</strong> : Your are not using Chrome</div>
  
  <menu-dev *ngIf="displayMenu==true"></menu-dev>

    <main class="main-container container-fluid clearfix relative">
      <div *ngIf="server && server!='prod'" class="server" [ngClass]="'srv-'+server">{{server | capitalize}}</div>
      <router-outlet></router-outlet>
    </main>


  `,
  encapsulation: ViewEncapsulation.None
})
export class AppComponent implements OnInit {
  showMonitor = (ENV === 'development' && !AOT &&
    ['monitor', 'both'].includes(STORE_DEV_TOOLS) // set in constants.js file in project root
  );
  mobile = MOBILE;
  sideNavMode = MOBILE ? 'over' : 'side';
  views = views;

  public navheight: number = 100;
  public displayMenu:boolean;
  public isChrome = !!window['chrome'] && !!window['chrome'].webstore;
  public server:string = "prod";

  constructor(
    private cache: TransferState,
    public route: ActivatedRoute,
    public router: Router,
    private config: ConfigService
  ) { }

  ngOnInit() {
    this.cache.set('cached', true);
//    this.displayMenu = ('development'==ENV);  //  ENV = build mode
    this.displayMenu = false;  //  ENV = build mode
    this.server = this.config.getProps('environment') || "prod";  //  = server environment (local, dev, tst, uat, prod)
  }

  activateEvent(event) {
    if (ENV === 'development') {
      console.log('Activate Event:', event);
    }
  }

  deactivateEvent(event) {
    if (ENV === 'development') {
      console.log('Deactivate Event', event);
    }
  }
}
