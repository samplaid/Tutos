import { FullAgent } from './full-agent';
import { AgentContactStatus } from './agent-status';

export class AgentContact  {

    public agcId: number; 
    public agentId?: string; 
    public contact?: FullAgent;
    public contactFunction?: string;
    public status?: string;   

    [other: string]: any;

    constructor(agentId:string){
        this.agentId = agentId;
        this.contact = new FullAgent();
        this.contactFunction = null;
        this.status = AgentContactStatus.ENABLED;
        this.contact.category = 'PR';  // PR  =	Person Contact                          
    }

}
