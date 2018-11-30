
import { SecurityTransferLine } from './securities-transfer';
import { TransferCandidate } from './transfer-candidate';

export interface TransferForm{
    transferId?: number;
    trfCurrency?: string;
    libDonOrd?: string;
    ibanDonOrd?: string;
    swiftDonOrd?: string;
    libBenef?: string;
    ibanBenef?: string;
    swiftBenef?: string;
    trfComm?: string;
    transferStatus?: string;
    transferType?: string;
    userCompta?: string;
    comptaDt?: Date;
    rejectComment?: string;
    trfMt?: number;
    transferSecurities?: SecurityTransferLine[];
    mode: string;
    policyId: string;
    policyOut: string;    
    workflowItemId: number;
    cps1Dt: Date;
    transferSecuritiesComment: string;
    fdsId: string;
    editingId: number;
}

export interface TransferFormData extends SurrenderTransferFormData {
    securitiesTransfer: TransferForm;    
}

export interface SurrenderTransferFormData {
    cashTransfer: TransferForm;    
    transferCandidates: TransferCandidate[];
}