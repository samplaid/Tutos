import { FundForm, PartnerForm, BenefClauseForm } from ".";
import { ClientForm, BeneficiaryForm, PolicyHolderForm, InsuredForm } from "./client";
import { FormData } from "./form-data";
import { PolicyTransfer } from "../policy-transfer";

export class AppForm extends FormData{
    
    productCd?: string;
    applicationForm?: string;
    clientName?: string;
    countryCd?: string;
    expectedPremium?:number;
    contractCurrency?: string;
    entryFeesPct?:number;
    entryFeesAmt?:number;
    companyEntryFeesPct?:number;
    companyEntryFeesAmt?:number;
    mngtFeesPct?:number;
    companyMngtFeesPct?:number;
    creationUser?: string;
    creationDt?: Date;
    updateUser?: string;
    updateDt?: Date;
    term?:number;
    noCoolOff:boolean;
	surrenderFees?: number;
	sendingRules?: string;
	mailToAgent?: string;
	orderByFax?: boolean;
	mandate?: boolean;
	scudo?: boolean;
	tax?: boolean;
	taxRate?: number;
	nonSurrenderClauseDt?: Date;
	mandatoAllincasso?: boolean;
	brokerRefContract?: string;
	paymentAmt?: number;
	paymentDt?:Date;
    paymentTransfer?: boolean;
    policyTransfer?: boolean;
	deathCoverageTp?: number;
	deathCoveragePct?: number;
	deathCoverageAmt?: number;
	lives?: number;
	multiplier?: number;
	score?: number;
    existedFid?:boolean;
    existedFas?:boolean;
    existedFe?:boolean;
    existedFic?:boolean;
    premiumCountryCd?:string;
    deathCoverageStd?:boolean;

    mandat?:boolean;  // TODO : WRONG NAME : need to be defined !!! 
    
    fundForms?: FundForm[];

    broker?: PartnerForm;
    subBroker?: PartnerForm;
    brokerContact?: PartnerForm;
    countryManagers?: PartnerForm[];
    businessIntroducer?: PartnerForm;  
    partners ?: PartnerForm[];

    policyHolders?: PolicyHolderForm[];
    insureds?: InsuredForm[];
    deathBeneficiaries?: BeneficiaryForm[];
    lifeBeneficiaries?: BeneficiaryForm[];
    otherClients?: ClientForm[];

    deathBenefClauseForms: BenefClauseForm[];
    lifeBenefClauseForms: BenefClauseForm[];

    policyTransferForms: PolicyTransfer[];
    readonly type = 'APP_FORM';
    coverage: number;
    

    // to allow dynamically add property.
    [other: string]: any;

    constructor(){
        super();
        this.countryManagers = [];
        this.broker = new PartnerForm(null, 'BK');
        this.subBroker = new PartnerForm(null, 'SB');
        this.businessIntroducer = new PartnerForm(null, 'IN');
        this.fundForms = [];
        this.policyHolders = [];
        this.insureds = [];
        this.deathBeneficiaries = [];
        this.lifeBeneficiaries = [];
        this.otherClients = [];
        this.deathBenefClauseForms = [];
        this.lifeBenefClauseForms = [];
        this.policyTransferForms = [];
        this.entryFeesPct = 0;
    }
}
