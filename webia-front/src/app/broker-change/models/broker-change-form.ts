import { FormData, PartnerForm } from '../../_models';
import { PolicyValuation } from '../../_models/policy/policy-valuation';

export class BrokerChangeForm extends FormData {
    changeDate?: Date;
    broker: PartnerForm;
    subBroker: PartnerForm;
    brokerContact: PartnerForm;
    brokerRefContract: string;
    policyValuation: PolicyValuation;
    sendingRules: string;
    mailToAgent: string;
    readonly type ="BROKER_CHANGE_FORM";
}
