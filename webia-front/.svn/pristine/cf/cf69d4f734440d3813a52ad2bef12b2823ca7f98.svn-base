import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { AgentHierarchy, AgentHierarchyStatus, Page, FullAgent, AgentLiteAdapter } from '../../../../_models/index';
import { AgentHierachyTableService } from './agent-hierarchy-table.service';
import { defaultPageSize } from '../../../../_models/constant';
import { StateMode } from '../../../../utils/mode';
import { PaginationService } from '../../../../utils/index';
import { ChangeDetectionStrategy } from '@angular/core/src/change_detection/constants';

@Component({
    selector: 'agt-hierachy-table',
    templateUrl: 'agent-hierarchy-table.component.html',
    providers: [ AgentHierachyTableService ]
})

export class AgentHierachyTableComponent implements OnInit {
    agtHierarchies: Array<AgentHierarchy>;
    parentAgent: FullAgent;
    inputSearch: string;
    status: boolean;
    page: Page;

    @Output()
    agentHierachiesChange: EventEmitter<AgentHierarchy[]> = new EventEmitter<AgentHierarchy[]>();

    @Output()
    masterBrokerChange: EventEmitter<FullAgent> = new EventEmitter<FullAgent>();

    @Input()
    set agentHierachies(agtHierarchies: Array<AgentHierarchy>) {
        this.agtHierarchies = agtHierarchies;
        this.agentHierachiesChange.emit(this.agtHierarchies);
        this.updateTable(1);
    }

    get agentHierachies() {
        return this.agtHierarchies || new Array<AgentHierarchy>();
    }

    @Input()
    set masterBroker(parentAgent: FullAgent) {
        this.parentAgent = parentAgent;
        this.masterBrokerChange.emit(parentAgent);
    }

    get masterBroker() {
        return this.parentAgent;
    }

    @Input()
    mode: string = StateMode.readonly;

    constructor(private service: AgentHierachyTableService,
        private paginationService: PaginationService) {
        this.status = true;
    }

    ngOnInit() { }

    ////////// Own methods declaration
    onSearchKeyUp(event: KeyboardEvent): void {
        let inputSearch = (<HTMLInputElement>event.target).value;
        this.updateTable();
    }

    isReadOnly(): boolean {
        return this.service.isReadOnly(this.mode);
    }

    clearSearch(): void {
        this.inputSearch = null;
        this.updateTable();
    }

    statusChange(checked: boolean): void {
        this.updateTable(this.page.number);
    }

    setPage(pageNumber: number): void {
        this.updateTable(pageNumber);
    }

    subBrokerUpdated(subBroker: FullAgent): void {
        let item = this.agentHierachies.find(item => item && subBroker && item.subBroker && item.subBroker.agtId === subBroker.agtId);

        if (item) {
            item.subBroker = AgentLiteAdapter.convertToAgentLite(subBroker);
        }

        this.updateTable(this.page.number);
    }

    deactivate(item: AgentHierarchy): void {
        this.service.deactivateItem(item, this.agentHierachies);
        this.agentHierachiesChange.emit(this.agentHierachies);
        this.updateTable(this.page.number);
    }

    createHierarchyLink(subBroker: FullAgent): void {
        this.masterBroker.agentHierarchies = this.service.mergeArray( this.masterBroker.agentHierarchies, this.agtHierarchies);
        this.service.createHierarchyLink(this.masterBroker, subBroker).subscribe(newLink => { 
            this.agtHierarchies = this.agentHierachies.concat(newLink); 
            this.masterBroker.agentHierarchies = this.service.mergeArray( this.masterBroker.agentHierarchies, this.agtHierarchies);
            this.masterBrokerChange.emit(this.masterBroker);
            this.agentHierachies = this.masterBroker.agentHierarchies;
        });
    }

    private updateTable(pageNumber: number = 1): void {
        let results = this.service.search(this.inputSearch, this.agentHierachies)
            .filter(item => this.service.isActiveOnly(this.service.toStatusCode(this.status), item))
            .sort(this.service.sortByName);
        this.page = this.paginationService.nextPage(results, pageNumber, defaultPageSize / 2);
    }
}