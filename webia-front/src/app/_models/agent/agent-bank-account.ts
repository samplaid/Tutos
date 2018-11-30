export class AgentBankAccount{
    public agbId: number;
    public agtId: string;
    public agent?: string;
    public accountName: string;
    public iban?: string;
    public bankName?: string;
    public bic?: string;
    public accountCurrency?: string;
    public status?: string;
    public commPaymentCurrency?: string;

    [other:string]: any;
}