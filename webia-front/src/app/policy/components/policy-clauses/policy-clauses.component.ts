import { Input, Component, OnInit } from '@angular/core';
import { PolicyHolder, Beneficiary, OtherClient, FullClient, PolicyCoverage } from "../../../_models/index";
import { LoadableComponent } from "../../../workflow/components/loadable.component";

@Component({
    selector: 'policy-death-life-clauses',
    templateUrl: 'policy-clauses.tpl.html'
})
export class PolicyClausesComponent extends LoadableComponent implements OnInit {

    constructor() {
        super();
    }

    @Input() clauses: any;
    @Input() firstPolicyCoverages: PolicyCoverage;    

    ngOnInit(): void {}

}