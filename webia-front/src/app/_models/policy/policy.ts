import { Product } from '../product';
import { PolicyAgentShare } from './policy-agent-share';
import { PolicyCoverage } from './policy-coverage';
import { PolicyNote } from './policy-note';
import { Beneficiary } from '../client/beneficiary';
import { PolicyHolder } from '../client/policyHolder';
import { Insured } from '../client/insured';
import { OtherClient } from '../client/otherClient';
import { FullClient, Transfer } from '../index';

export class Policy {
	polId: string;
	product: Product;
	dateOfApplication: Date;
	dateOfCommencement: Date;
	status: number;
	activeStatus: "ACTIVE" | "PENDING" | "INACTIVE";
	additionalId: string; // = application Form
	currency: string;
	broker: PolicyAgentShare;
	brokerContact: PolicyAgentShare;
	subBroker: PolicyAgentShare;
	brokerRefContract: string;
	businessIntroducer: PolicyAgentShare;
	countryManagers: PolicyAgentShare[];
	firstPolicyCoverages: PolicyCoverage;
	policyHolders: PolicyHolder[];
	insureds: Insured[];
	lifeBeneficiaries: Beneficiary[];
	deathBeneficiaries: Beneficiary[];
	otherClients: OtherClient[];
	category: any;
	orderByFax: number;
	mailToAgent: any;
	nonSurrenderClauseDate: Date;
	scudo: boolean;
	mandatoAllIncasso: boolean;
	assignmentPledge: boolean;
	assignmentPledgeDetails: string;
	scoreNewBusiness: number;
	scoreLastTrans: number;
	policyNotes: PolicyNote[];
	exMandate?: boolean;
	policyTransfers: Transfer[];

	[other: string]: any;
}