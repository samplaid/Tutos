import { Component, OnInit, Input } from '@angular/core';
import { Router, ActivatedRoute, Params } from '@angular/router';
import { Subscription } from 'rxjs/Subscription';
import { GenerateMandatDeGestionService}  from './generate-mandat-de-gestion.service';
import { AppForm, Fund, CreateEditingRequest, EditingDocumentType, CreateEditingResponse, StepName } from '../_models';
import { MessageService } from "../utils";
import { FundService } from "../fund/fund.service";

@Component({
    selector: 'generate-mandat-de-gestion',
    templateUrl: 'generate-mandat-de-gestion.tpl.html'
})
export class GenerateMandatDeGestionComponent implements OnInit {  
    a: AppForm;
    subs:Array<Subscription> = [];
    stepName: StepName = StepName.Generate_Mandat_de_Gestion;
    
    @Input() mode: "readOnly"|"waiting"|"edit"|"create";

    @Input("generate") set generate(value: AppForm) {
        this.a = value;
    }

    get dispatch() {
        return this.a;
    }


    constructor( private route: ActivatedRoute, 
                private router: Router, 
                private messageService: MessageService, 
                private generateMandatDeGestionService:GenerateMandatDeGestionService,
                private fundService: FundService) { 
 
    }

    generateMandatDeGestion(fund: Fund){
        this.messageService.clearAlert();         

        this.subs.push(this.generateMandatDeGestionService.generateMandatDeGestion(fund, this.a).subscribe( 
                                    (response) => { 
                                        this.messageService.addAlertSuccess('Generate "Mandat de Gestion" has been sent successfully', true); 
                                                     } , 
                                          error => {} // reload as step can be changed
        )); 

    }

    applyManagementMandateDocRequest(fund: Fund){
        let request = new CreateEditingRequest();
        request.fund = fund.fdsId;
        request.policy = this.a.policyId;
        request.product = this.a.productCd;
        request.documentType = EditingDocumentType.MANAGEMENT_MANDATE;
        this.subs.push(this.generateMandatDeGestionService.applyManagementMandateDocRequest(request)
                                                        .subscribe((response: CreateEditingResponse) => {    
                                                            if(response && response.requestId) {
                                                                this.messageService.addAlertSuccess('Generate "Mandat de Gestion" has been sent successfully', true); 
                                                            }                                                        
        }));
        
    }

    ngOnInit() { }

    ngOnDestroy(){
         this.subs.forEach((sub: Subscription) => sub && !sub.closed && sub.unsubscribe());
    }    

}