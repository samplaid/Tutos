import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpService, MessageService, ApiService } from '../../../utils';
import { Http } from '@angular/http';
import { CommissionReconciliationSearchCriteria } from '../models/commission-reconciliation-search-criteria';
import { Page, defaultPageSize } from '../../../_models';
import { CommissionRequest } from '../models/commission-request';
import { PageRequest } from '../../../_models/paging/page-request';
import { CommissionReconciliationGroup } from '../models/commission-reconciliation-group';

@Injectable()
export class CommissionService extends HttpService {

    /**
     * Create a new commission service.
     * @param $http the http client
     * @param messageService message service
     * @param api the api that expose the webservice url
     */
    constructor(public $http: Http,
        protected messageService: MessageService,
        protected api: ApiService) {
        super($http, messageService);
    }

    /**
     * Changes the status of selected commissions to 'Validated'.
     * @param selected list of reconciled commissions
     */
    validateReconciled(selected: CommissionReconciliationGroup[]): Observable<Page> {
        let request: CommissionRequest = new CommissionRequest();
        request.commissions = selected;
        const path: string = this.api.getURL('validateReconciled');
        return this.POST(path, request, 'validateReconciled');
    }

    /**
     * Changes the status of selected commissions to 'Validated'.
     * @param selected list of reconciled commissions
     */
    doneValidated(selected: CommissionReconciliationGroup[]): Observable<Page> {
        let request: CommissionRequest = new CommissionRequest();
        request.commissions = selected;
        const path: string = this.api.getURL('doneValidated');
        return this.POST(path, request, 'doneValidated');
    }

    /**
     * Search agent commissions based on the criteria in parameter.
     * @see CommissionSearchCriteria
     * @param criteria the criteria
     */
    search(criteria: CommissionReconciliationSearchCriteria): Observable<Page> {
        if (!criteria.pageable) {
            criteria.pageable = new PageRequest(1, defaultPageSize);
        }
        const args: any[] = [criteria.type, criteria.text, criteria.pageable.page, criteria.pageable.size];
        const path: string = this.api.getURL('reconciliationSearchGroup', ...args);
        return this.GET(path, 'search');
    }

    validateReconciledWithConfirmation(selected: CommissionReconciliationGroup[]): Observable<Page> {
        let commissionWithoutCrmIds = selected.filter(commissionGroup => !commissionGroup.aggregate.crmId);
        if (commissionWithoutCrmIds.length > 0) {
            let message = this.prepareValidationConfirmationMessage(commissionWithoutCrmIds);
            return this.messageService.confirm(message, 'Comfirm commissions without CRM Id validation')
                .flatMap(confirmed => {
                    console.log(confirmed);
                    if (confirmed) {
                        return this.validateReconciled(selected);
                    } else {
                        return Observable.of();
                    }
                });
        } else {
            return this.validateReconciled(selected);
        }
    }

    private prepareValidationConfirmationMessage(commissionWithoutCrmIds: CommissionReconciliationGroup[]): string {
        let message = "The following commissions have not a CRM Id:";
        message += "<ul>";
        commissionWithoutCrmIds.forEach(item => message += "<li> Agent id = " + item.aggregate.agentId + ", Name = " + item.aggregate.name + " and status = " + item.aggregate.status + "</li>");
        message += "</ul>";
        return message;
    }


    /**
     * this function will launch the process to create all the new reconcilated commissions to generate the documents
     * @param request GenerateStatementCommissionRequest
     */
    launchGenerateStatementComJob( request:GenerateStatementCommissionRequest){
        return this.POST(this.api.getURL('generateStatementComJob'), request, 'generateStatementComJob');
    }
}

export class GenerateStatementCommissionRequest {
    "statementId":string;  // should not be used in front
    "type":string;  //"ENTRY" or "ADM"
	"period": string; // ex : "201809" (for ENTRY)  or "2018Q2" (for ADM)
    "broker":string;  // optional
    constructor(type:string){
        
        let d = new Date();
        if (type == 'ENTRY'){
            this.type = type;
            d.setMonth(d.getMonth() - 1); // we care of the previous mounth ended
            this.period = d.toISOString().substr(0, 7).replace("-","");
        }
        if (type == 'PORTFOLIO'){
            this.type = 'ADM';
            d.setMonth(d.getMonth() - 2); // we care of the previous quarter ended
            let quarter = Math.floor((d.getMonth() + 3) / 3);
            this.period = d.getUTCFullYear()+"Q"+quarter;
        }        
    }
}
