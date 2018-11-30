import { AgentContact } from "./agent-contact";
import { AgentBankAccount } from "./agent-bank-account";
import { AgentHierarchy } from "./agent-hierarchy";
import { AgentStatusType } from "./agent-status";
import { AssetManagerStrategy } from "./asset-manager-strategy";

export class FullAgent {
    public agtId: string;
	public name?: string;
	public status?: number;
	public crmRefererence?: string;
	public crmStatus?: string;
	public category?: string;
	public addressLine1?: string;
	public addressLine2?: string;
	public addressLine3?: string;
	public addressLine4?: string;
	public town?: string;
	public country?: string;
	public postcode?: string;
	public telephone?: string;
	public fax?: string;
	public email?: string;
	public mobile?: string;
	public documentationLanguage?: number;
	public paymentCode?: number;
	public paymentMethod?: number;
	public paymentFrequency?: number;
	public paymentCcy?: string;
	public paymentSuperiorAgent?: number;
	public firstname?: string;
	public title?: string;
	public desiredReport?: number;
	public paymentCommission?: number;
	public paymentAccountBic?: string;
	public statementByEmail?: boolean;
	public centralizedCommunication?: boolean;
	
	

	// Link to other model
	public agentContacts?: AgentContact[];
	public bankAccounts?: AgentBankAccount[];
	public agentHierarchies?: AgentHierarchy[];
	public assetManagerStrategies?: AssetManagerStrategy[];
	

    [other: string]: any;

	constructor(){
        this.status = AgentStatusType.ENABLED;
		this.agentContacts = [];
		this.bankAccounts = [];
		this.agentHierarchies = [];
		this.assetManagerStrategies = [];
    }
}
