import { Http, Response } from '@angular/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/Observable';
import { ApiService, MessageService, HttpService, HandleErrorOptions, DateUtils } from '../../utils';
import { CliOtherRelationShipRole, Policy, PolicyEntreeFees, PolicyChange, Insured, PolicyHolder } from '../../_models/index';
import { PolicyValuation } from '../../_models/policy/policy-valuation';
import { Transaction, PolicyTransactionsDetails, PolicyTransactionsDetailsInput, PolicyTransactionsDetailsOutput, TransactionsCommissions } from '../../_models/transaction';
import { BeneficiaryChange } from '../../beneficiary-change/models/beneficiary-change';
import { BrokerChange } from '../../broker-change/models/broker-change';

const cessionRoles = [CliOtherRelationShipRole.CESSION_CHANGING_STRATEGY_RIGTHS,
                        CliOtherRelationShipRole.CESSION_SURRENDER_RIGTHS,
                        CliOtherRelationShipRole.CESSION_SWITCH_RIGTHS];

@Injectable()
export class PolicyService extends HttpService {
    constructor($http: Http, messageService: MessageService, private api: ApiService) {
        super($http, messageService);
        this.domain = 'PolicyService';
    }

    getPolicy(polId) {
        return this.GET(this.api.getURL('getPolicy', polId), 'getPolicy');
    }

    getOptionalPolicy(polId: string): Observable<Policy> {
        return this.GET_SILENT(this.api.getURL('getPolicy', polId));
    }

    getPolicyLight(polId) {
        return this.GET(this.api.getURL('getPolicyLight', polId), 'getPolicyLight');
    }

    getPolicyValuation(polId: string, currency: string = '', date: string = ''): Observable<PolicyValuation> {
        return this.GET(this.api.getURL('getPolicyValuation', polId, currency, date), 'getPolicyValuation');
    }

    getPolicyClauses(polId: string, productCd: string, lang: string) {
        return this.GET(this.api.getURL('getPolicyClauses', polId, productCd, lang), 'getPolicyClauses');
    }

    isExist(polId: string){
        return this.functionWithStore('isExist'+polId, this.api.getURL('isPolicyExist', polId), 'isPolicyExist' );
    }

    isInCessionRole(clientRelationType: number): boolean {
        return cessionRoles.some(clientRelationType => clientRelationType === clientRelationType);
    }

    getEntryFees(policyId: string): Observable<PolicyEntreeFees> {
        return this.GET(this.api.getURL('getEntryFees', policyId), 'getEntryFees');
    }

    getDeceasedInsureds(policyId: string): Observable<Insured[]> {
        return this.GET(this.api.getURL('getDeceasedInsureds', policyId), 'getDeceasedInsureds');
    }

    getDeceasedHolders(policyId: string): Observable<PolicyHolder[]> {
        return this.GET(this.api.getURL('getDeceasedHolders', policyId), 'getDeceasedHolders');
    }
    getTransactionsByPolicy(policyId: string): Observable<Transaction[]> {
        return this.GET(this.api.getURL('getPoliciesTransactions', policyId), 'getPoliciesTransactions');
    }

    getPolicyTransactionsDetails(policyId:string, transaction: Transaction): Observable<PolicyTransactionsDetailsOutput>{

        if(!(transaction.netAmount > 0) && !(transaction.eventType == 19 || transaction.eventType == 23) ){
            return Observable.of({
                detailsIn: [],
                detailsOut: []
            });
        }

        const detailsInput : PolicyTransactionsDetailsInput = {
            policyId: policyId,
            transaction: transaction
        };

        return this.POST(this.api.getURL('getPolicyTransactionsDetails'), detailsInput,'getPolicyTransactionsDetails').map(result => this.mapTransactionDetails(transaction, result));
    }

    mapTransactionDetails(transaction: Transaction, result: PolicyTransactionsDetails): PolicyTransactionsDetailsOutput {
        const commission: TransactionsCommissions = {
            commission : result.commission,
            commissionCurrency : result.commissionCurrency,
            agentLabel : result.agentLabel
        }
        let detailsIn;
        let detailsOut;
        let isSwitch;
        if(transaction.eventType == 19 || transaction.eventType == 23 ){
            detailsIn = result.transactionHistoryDetails == null? []: result.transactionHistoryDetails.filter(detail => detail.eventType == 38 );
            detailsOut = result.transactionHistoryDetails == null ? []: result.transactionHistoryDetails.filter(detail =>  detail.eventType ==37 );
        }
        else if (transaction.eventType == 6) {
            detailsIn = result.transactionHistoryDetails == null? []: result.transactionHistoryDetails.filter(detail => detail.eventType == 38 && detail.split !==0);
            detailsOut = result.transactionHistoryDetails == null ? []: result.transactionHistoryDetails.filter(detail =>  detail.eventType ==37 && detail.split !==0 );
            if(detailsIn.length  == 0 || detailsOut.length  == 0)  {
                detailsIn = result.transactionHistoryDetails == null? []:result.transactionHistoryDetails.filter(detail =>  detail.split !== 0 );;
                detailsOut = [];
                isSwitch = false;
            }
        }
        else{
            detailsIn = result.transactionHistoryDetails == null? []:result.transactionHistoryDetails.filter(detail =>  detail.split !==0 );
            detailsOut = [];
        }

        return {
            commission : commission,
            detailsIn: detailsIn,            
            detailsOut: detailsOut,  
            isSwitch: isSwitch         
        }
    }

    getChangesByPolicy(policyId: string): Observable<PolicyChange[]> {
        return this.GET(this.api.getURL('getPolicyChanges', policyId), 'getPolicyChanges');
    }

    getBeneficiaryChange( policyId:string, workflowItemId:number, productCd:string, lang:string ): Observable<BeneficiaryChange>{
        return this.GET(this.api.getURL('getBeneficiaryChange',policyId, workflowItemId, productCd, lang), 'getBeneficiaryChange');       
    }

    getBrokerChange(workflowItemId:number): Observable<BrokerChange>{
        return this.functionWithStore('BrokerChange'+workflowItemId,this.api.getURL('getBrokerChange', workflowItemId), 'getBrokerChange');
    }

    getBrokerChangeBefore(workflowItemId:number): Observable<BrokerChange>{
        return this.functionWithStore('BrokerChangeBefore'+workflowItemId,this.api.getURL('getBrokerChangeBefore', workflowItemId), 'getBrokerChangeBefore');
    }

}