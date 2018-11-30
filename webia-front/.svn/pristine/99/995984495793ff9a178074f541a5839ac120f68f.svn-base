import { ClientService } from '../../client.service';
import { Component, Input } from '@angular/core';
import { WebiaService } from '../../../_services';
import { PolicyActiveStatus } from '../../../_models/policy/policy-active-status.enum';


@Component({
    selector: 'client-policies',
    templateUrl: './client-policies.tpl.html'
})
export class ClientPoliciesComponent  {

    policies: any[];
    _policies: any[];

    busy;
    page = 1;
    pageSize = 10;
    show = {active:true, pending: true, terminated: true};
    codeMapper:any[];

    portfolio = 0;
    currency = 'EUR';
   
    @Input() set cliId(val:number){ 
        if (val) {
            this.busy = this.clientService.getRolesByPolicies(val).then(p => {
                if(p){
                    this._policies = p.rolesByPolicy;
                    this.updateList();
                    this.portfolio = p.portfolio;
                }                    
            });
        } 
    };

    constructor( private clientService:ClientService, private webiaService:WebiaService) { 
        this.policies=[];
        this.webiaService.getClientCodeLabels().then((codeMapper:any[])=>this.codeMapper=codeMapper);
    }

    updateList(){
        if(this._policies) {
            this.policies= this._policies.filter(r=> {
                if (r.policy && r.policy.policyStatus){
                    if (r.policy.activeStatus == PolicyActiveStatus.ACTIVE && this.show.active)
                        return true;
                    if (r.policy.activeStatus == PolicyActiveStatus.PENDING && this.show.pending)
                        return true;
                    if (r.policy.activeStatus == PolicyActiveStatus.INACTIVE && this.show.terminated)
                        return true;                        
                }
                return false;
            });
        }
    }
    

}