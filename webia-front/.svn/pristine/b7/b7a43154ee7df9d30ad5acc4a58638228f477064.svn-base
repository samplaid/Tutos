import { Input, Component, OnInit } from '@angular/core';
import { PolicyHolder, Beneficiary, OtherClient, FullClient, clientClassifierRegExp } from "../../../_models/index";
import { Product } from "../../../_models/product";
import { Insured } from "../../../_models/formData/client/insured";
import { WebiaService } from '../../../_services';
import { PolicyService } from '../../services/policy.service';

@Component({
    selector: 'policy-clients',
    templateUrl: 'policy-clients.tpl.html',
    styles: [`
        .section-client{}
        .reduced-margin {
            margin-bottom: 5px;
        }
    `]
})

export class PolicyClientsComponent implements OnInit {

    codeMapper:any[]=[];
    clientRegXp: RegExp;

    @Input() product: Product;
    @Input() policyHolders: PolicyHolder[];
    @Input() insureds: Insured[];
    @Input() deathBeneficiaries: Beneficiary[];
    @Input() lifeBeneficiaries: Beneficiary[];
    @Input() otherClients: OtherClient[];
    @Input() labelClass: string = "col-md-2";

    constructor(
                private policyService: PolicyService,
                private webiaService:WebiaService) {
    }

    ngOnInit(): void {

        // define the regex for the text formatting
        this.clientRegXp = clientClassifierRegExp;

        this.webiaService.getClientCodeLabels().then((codeMapper:any[])=> this.codeMapper = codeMapper);
    }

    hasCessionRole(otherClient: FullClient): boolean {
       return this.policyService.isInCessionRole(otherClient.roleNumber);
    }

}