import { Component, OnInit } from '@angular/core';
import { ExternalFundComponent } from "../external-fund/external-fund.component";
import { ActivatedRoute, Router } from "@angular/router";
import { FundService } from "../fund.service";
import { CurrencyService } from "../../_services/currency.service";
import { AgentService } from "../../agent/agent.service";
import { MessageService, UserService } from "../../utils";
import { UoptDetailService } from "../../_services/uopt-detail.service";
import { Title } from "@angular/platform-browser";
import { FundType } from "../index";
import { WebiaService } from '../../_services/webia.service';

@Component({
    selector: 'fic',
    templateUrl: './fic.component.html',
    styles:[`
        .fund.fic .pricing {
            margin: auto 8%;
        }
    `]
})
export class FicComponent extends ExternalFundComponent implements OnInit {
    constructor(protected route: ActivatedRoute, protected router: Router,
        protected fundService: FundService, protected currencyService: CurrencyService,
        protected uoptDetailService: UoptDetailService, protected agentService: AgentService,
        protected messageService: MessageService, protected userService: UserService, protected titleService: Title,protected webiaService: WebiaService) {
            super(route,router,fundService,currencyService,uoptDetailService,agentService,messageService,userService,titleService,webiaService );
    }

    ngOnInit() { 
        super.ngOnInit();
    }

    initFund() {        
        super.initFund();      
        this.fund.fundSubType = FundType.FIC.key;  
    }
}