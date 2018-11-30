import { Component, OnInit, Input } from '@angular/core';
import { EntityType } from "../../../_models/constant";
import { Fund } from '../../../_models';


@Component({
    selector: 'fund-footer',
    templateUrl: './fund-footer.tpl.html'
})
export class FundFooterComponent implements OnInit {

    ownerType =  EntityType.FUND;
    
    @Input() fund: Fund;
    @Input() mode;
    
    constructor() { }

    ngOnInit() {  }


}