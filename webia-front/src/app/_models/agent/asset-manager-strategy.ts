import { AssetManagerStrategyStatus, AgentLite } from "./index";

export class AssetManagerStrategy {
    readonly linkedToFund: boolean;
    amsId: number;
    agentNo: string;
    riskProfile: string;
    riskProfileDescription: string;
    riskType: string;
    classOfRisk?: string;
    status: string;
    agent: AgentLite;

    [other: string]: any;

    constructor(){
        this.status = AssetManagerStrategyStatus.ENABLED;
    }

    /**
     * @deprecated
     * @param fields 
     */
    static create(...fields: { name: string, value: any }[]):  AssetManagerStrategy{
        let obj = new AssetManagerStrategy();
        if(fields){
            fields.forEach(field => {
                if(field.name){
                    obj[field.name] = field.value;
                }
            });
        }
        return obj;
    }
}