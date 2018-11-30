import { BusyConfig } from 'ng-busy';

export const fundClassifierRegExp = new RegExp(/_#([^_]*)#_/g);
export const clientClassifierRegExp = new RegExp(/_#([^_]*)#_/g);
export const DCClauseRegex = new RegExp(/\[[A-Z]*\]/g);
export const defaultPageSize = 20;
export const days = [{id: 1, label: "Monday"}, 
                     {id: 2, label: "Tuesday"},
                     {id: 3, label: "Wednesday"},
                     {id: 4, label: "Thursday"},
                     {id: 5, label: "Friday"},
                     {id: 6, label: "Saturday"},
                     {id: 7, label: "Sunday"}];
export const emailRegExp= new RegExp(/^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/)

export const wealinsId = 'A01141';

export class EntityType {
    static readonly  FUND = 'FUND'; 
    static readonly  CLIENT = 'CLIENT';
}
export const  defaultTranslationLanguage = 'ENG';

export const transactionAdminManagementHoldmailCodes = [12, 13, 71];
export const transactionMortalityCodes = [3];
export const transactionCancelledStatuses = [2, 5, 6, 7];
export const transactionWithdrawalCode = 15;
export const transactionPremiumPaymentType = 2;
export const tableMessages = {'emptyMessage':'No result found'};
export const accountActiveStatus = 1;
export const accountClosedStatus = 2;
export const accountSuspendedStatus = 3;

export const agentMailSendingRuleCodes = ['MA', 'MCHA',  'MCCA','HMA','HMNFA','MCHCA','MAAC'];
export const clauseTypeList = ["Free","Standard"];

export const transferTypeReinvestCode = 'RE_INVEST';
export const transferTypeReinvestLabel = 'Re-invest';
export const transferTypeTransferCode = 'TRANSFER';
export const transferTypeTransferLabel = 'Transfer';
export const year_term = 'YEAR_TERM';
export const whole_life = 'WHOLE_LIFE';
export const fundFidCode = 'FID';
export const fundFasCode = 'FAS';

export const workflowStatusCompleted = 3;
