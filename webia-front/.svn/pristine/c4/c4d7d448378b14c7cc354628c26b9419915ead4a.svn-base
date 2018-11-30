import { FullAgent } from "./full-agent";
import { AgentLite } from './agent-lite';

export class AgentLiteAdapter {
    
    public static convertToAgentLite(fullAgent: FullAgent): AgentLite{
        if(!fullAgent) return  undefined;
        
        let agentLite: AgentLite = new AgentLite();
        agentLite.agtId = fullAgent.agtId;
        agentLite.category = fullAgent.category;
        agentLite.name= fullAgent.name;
        agentLite.status= "" + fullAgent.status;
        agentLite.addressLine1 = fullAgent.addressLine1;
        agentLite.addressLine2= fullAgent.addressLine2;
        agentLite.addressLine3= fullAgent.addressLine3;
        agentLite.addressLine4= fullAgent.addressLine4;
        agentLite.country= fullAgent.country;
        agentLite.postcode= fullAgent.postcode;
        agentLite.town= fullAgent.town;
        agentLite.telephone= fullAgent.telephone;
        agentLite.fax= fullAgent.fax;
        agentLite.email= fullAgent.email;
        agentLite.mobile= fullAgent.mobile;
        agentLite.documentationLanguage= fullAgent.documentationLanguage;
        agentLite.title= fullAgent.title;
        agentLite.firstname= fullAgent.firstname;

        return agentLite;
    }

    
}