
import { ClientContactDetail } from "./clientContactDetail";
import { ClientLinkedPerson } from "./clientLinkedPerson";
import { ClientClaimsDetail } from "./client-claims-detail";
import { GeneralNote } from '../general-note';
import { ClientLite } from './client-lite';
import { ClientAccount } from './client-account';


export class FullClient extends ClientLite{    
    name?: string;
    documentationLanguage?: string;
    title?: string;
    sex?: number;
    maritalStatus?: number;
    nationality?: string;
    otherFornames?: string;
    profile?: string;
    mobileTelNo?: string;
    workTelNo?: string;
    classification?: string;
    email?: string;
    idExpiryDate?: string;
    placeOfBirth?: string;
    politicallyExposedPerson?: string;
    pepFunction?: string;
    insiderTrading?: string;
    insiderTradingDetails?: string;
    dap?: string;
    countryOfBirth?: string;
    provinceOfBirth?: string;
    profession?: string;
    dateOfRevision?: string;
    fiduciary?: number;
    vatNumber?: string; // new field
    circularLetter?: string;
    classOfRisk?: string;
    statementByEmail?: string;
    mediaExposedPerson?: string;
    mepDetail?: string;
    relativeCloseAssoc?: string;
    rcaDetail?: string;
    crsStatus?: string; // new field
    crsExactStatus?: string; // new field
    commercialEntity?: string; // new field
    nationalIdNo?: string;
    natIdCountry?: string;
    nationalId2?: string;
    natId2Country?: string;
    activityRiskCat?: number;
    classificationDetails?: string;
    riskCat?: string;
    subStatus?:number;
    dateOfSelfCert?: Date;
    crsDateOfLastDec?: Date;
    typeNumber?:number;
    healthDecDate?: string;
    healthDeclaration?: string;
    knowYourCustomer?: string;
    kycDate?: Date;
    gdprStartdate?: Date;
	gdprEnddate?: Date;
	exceptRisk: boolean;
	exceptActivityRisk?: boolean;
    
    deathClaim?: ClientClaimsDetail;
    dead?: boolean;


    clientNote?: GeneralNote;
    idNumber?:GeneralNote;
    homeAddress?: ClientContactDetail;
    correspondenceAddress?: ClientContactDetail;

    personsRepresentingCompany:ClientLinkedPerson[];
    controllingPersons:ClientLinkedPerson[];
    clientClaimsDetails: ClientClaimsDetail[] = [];   // TODO : because of the unique index on table, it can be only one element -> change it as object instead of array
    clientAccounts: ClientAccount[] = [];

    public constructor(){
        super();
        this.clientClaimsDetails = [];
    }
    
    /// Allow to add some filed dynamically.
    [other:string]:any;
}