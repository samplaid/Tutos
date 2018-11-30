import { Injectable } from '@angular/core';
import { Http } from '@angular/http';
import { HttpService, MessageService, ApiService } from '../../utils';
import { Observable } from 'rxjs/Observable';
import { BrokerChangeForm } from '../models/broker-change-form';
import { AgentLite, PartnerForm } from '../../_models';

@Injectable()
export class BrokerChangeFormService extends HttpService {    
    
    static readonly AMENDMENT_STATUS:number = 3;

    constructor($http: Http, messageService: MessageService, private api: ApiService) {
        super($http, messageService);
        this.domain = 'BrokerChangeFormService';  
    }

    initBrokerChangeForm(policyId: string, workflowItemId: number, secondCps: string): Observable<BrokerChangeForm> {
        return this.GET(this.api.getURL('initBrokerChangeForm', policyId, workflowItemId), 'initBrokerChangeForm').map((result: BrokerChangeForm) => {            
            this.updateNullPartnerForms(result);
            result.secondCpsUser = secondCps;
            return result;
        });
    }

    getMailToAgentList(brokerId: string, subBrokerId: string): Observable<AgentLite> {
        return this.GET(this.api.getURL('getMailToAgentList', brokerId, subBrokerId), 'getMailToAgentList');
    }

    // initialize agents. this init should be backend ?!
    updateNullPartnerForms(form: BrokerChangeForm): void {
        if(!form.broker) {
            form.broker = new PartnerForm(null, 'BK');
        }
        if(!form.subBroker) {
            form.subBroker = new PartnerForm(null, 'SB');
        }
        if(!form.brokerContact) {
            form.brokerContact = new PartnerForm(null, 'PR');
        }                    
    }

    removeEmptyPartnerForms(form: BrokerChangeForm): void {
        // remove empty brokers
        if(this.isPartnerNotSet(form.broker)){
            form.broker = null;
        }
        // remove empty subbroker
        if(this.isPartnerNotSet(form.subBroker)){
            form.subBroker = null;
        }
        // remove empty broker contact
        if(this.isPartnerNotSet(form.brokerContact)){
            form.brokerContact = null;
        }
    }

    setAmendmentStatusToPartnerForms(form: BrokerChangeForm): any {
        if(form.broker) {
            form.broker.status = BrokerChangeFormService.AMENDMENT_STATUS;
        }
        if(form.subBroker) {
            form.subBroker.status = BrokerChangeFormService.AMENDMENT_STATUS;
        }
        if(form.brokerContact) {
            form.brokerContact.status = BrokerChangeFormService.AMENDMENT_STATUS;
        } 
    }

    private isPartnerNotSet(partner: PartnerForm): boolean {
        return partner && !partner.partnerId;
    }
}