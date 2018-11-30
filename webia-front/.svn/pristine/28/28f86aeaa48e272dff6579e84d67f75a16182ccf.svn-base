import { Injectable } from '@angular/core';
import { AgentHierarchy, AgentHierarchyStatus, FullAgent, AgentLiteAdapter } from '../../../../_models/index';
import { StateMode } from '../../../../utils/mode';
import { AgentService } from '../../../agent.service';
import { Observable } from 'rxjs/Observable';

@Injectable()
export class AgentHierachyTableService {

    constructor(private agentService: AgentService) { }

    search(inputSearch: string, items: Array<AgentHierarchy>): Array<AgentHierarchy> {
        let results: Array<AgentHierarchy>;

        if(inputSearch && items) {
            results = items.filter(agtHierachy => (agtHierachy && agtHierachy.subBroker && agtHierachy.subBroker.name && agtHierachy.subBroker.name.toUpperCase().includes(inputSearch.toUpperCase())));                
        } else {
            results = items || new Array<AgentHierarchy>();
        }

        return results;
    }

    deactivateItem(el: AgentHierarchy, items: Array<AgentHierarchy>): void{
        if(items){
            let item = items.find(item => item.aghId == el.aghId);
            if(item){
                item.status = AgentHierarchyStatus.DISABLED;
                item.updated = true;
            }
        }              
    }

    toStatusCode(flag: boolean): AgentHierarchyStatus {
        return (flag) ? AgentHierarchyStatus.ENABLED : undefined;
    }

    isActiveOnly(status: AgentHierarchyStatus, item: AgentHierarchy): boolean {
        return (status === AgentHierarchyStatus.ENABLED) ? item.status === AgentHierarchyStatus.ENABLED : true;
    }

    isReadOnly(mode: string): boolean {
        return mode === StateMode.readonly;
    }

    sortByName(a: AgentHierarchy, b: AgentHierarchy): number{
        if(!a && !b) return 0;
        if(!a && b)  return -1;
        if(a && !b)  return 1;                
        if(!a.subBroker && !b.subBroker) return 0;
        if(!a.subBroker && b.subBroker)  return -1;
        if(a.subBroker && !b.subBroker)  return 1;
        if(!a.subBroker.name && !b.subBroker.name) return 0;
        if(!a.subBroker.name && b.subBroker.name)  return -1;
        if(a.subBroker.name && !b.subBroker.name)  return 1; 
        return a.subBroker.name.localeCompare(b.subBroker.name);
    }

    /**
     * Create a new link record that links a master agent to a sub agent in agent hierarchy and returns the new link.
     * The parameter 'parentAgent' will not contain the new link.
     * @param parentAgent the master agent
     * @param childAgent the child agent
     */
    createHierarchyLink(parentAgent: FullAgent, childAgent: FullAgent): Observable<AgentHierarchy> {        
        if(parentAgent && childAgent){
            let clonedParent = JSON.parse(JSON.stringify(parentAgent));
            if(!clonedParent.agentHierarchies){
                clonedParent.agentHierarchies = [];
            }

            let link = new AgentHierarchy();
            link.masterBroker = AgentLiteAdapter.convertToAgentLite(clonedParent);
            link.subBroker = AgentLiteAdapter.convertToAgentLite(childAgent);
            link.status = AgentHierarchyStatus.ENABLED;            
            link.updated = true;
            link.type = 1;
            clonedParent.agentHierarchies.push(link);
            
            //47: Bug: Depuis écran broker ajout sub-broker doit fonctionner comme ajout contact.
            return Observable.fromPromise(this.agentService.addAgentHierarchy(clonedParent)).map((agent: FullAgent) => {
                if(agent){
                    return agent.agentHierarchies.find((agh: AgentHierarchy) => 
                        agh && agh.masterBroker && agh.masterBroker.agtId === clonedParent.agtId 
                            && agh.subBroker && agh.subBroker.agtId === childAgent.agtId
                            && agh.status ===  AgentHierarchyStatus.ENABLED
                            && agh.type === 1)
                } else {
                    return null;
                }
            });
        }
    }

    mergeArray(arr1: Array<AgentHierarchy>, arr2: Array<AgentHierarchy>): Array<AgentHierarchy> {

        if(!arr2) {
            return (arr1) ? arr1 : new Array<AgentHierarchy>();
        }

        if(!arr1) {
            return (arr2) ? arr2 : new Array<AgentHierarchy>();
        }

        if(arr1 && arr2) {
            let items1: Array<AgentHierarchy> = JSON.parse(JSON.stringify(arr1));
            arr2.forEach(el2 => {
                let array: Array<AgentHierarchy> = items1.filter(item1 => item1.aghId === el2.aghId);
                if(array && array.length > 0) {
                    array.forEach(el1 => el1 = el2);
                } else {
                    items1.push(el2); 
                }
            });

            return items1;
        } else {
            return new Array<AgentHierarchy>();
        }
    }
}