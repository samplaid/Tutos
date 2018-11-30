import { AgentLite } from './agent-lite';


export class AgentHierarchy {
    public aghId: number;
	public masterBroker: AgentLite;
    public subBroker: AgentLite;
    public type?: number;
    public status?: number;
    public rateOrigin?: number;
    public rateApplication?: number;
    public rate?: string;
    public startDate?: Date;
    public endDate?: Date;
    public updated?: boolean;

    [other:string]: any;
}