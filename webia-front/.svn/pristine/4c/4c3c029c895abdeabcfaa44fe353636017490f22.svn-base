import { LoadableComponent } from '../loadable.component';
import { OnInit, Component, Input, SimpleChanges, ViewChild, Output, EventEmitter } from '@angular/core';
import { CreateEditingRequest } from '@models/editing/editing-request';
import { Observable } from 'rxjs/Observable';
import { DocumentService } from '@utils/services/document.service';
import { TabDirective } from 'ngx-bootstrap/tabs';
import { SimulationComponent } from '@workflow/components/simulation/simulation.component';


@Component({
    selector: 'multiple-simulations',
    templateUrl: 'multiple-simulations.tpl.html'
})
export class MultipleSimulationsComponent extends LoadableComponent implements OnInit {

    constructor(private documentService: DocumentService){
        super();
    }

    @Input() editingRequests: CreateEditingRequest[];
    fileObservableMap : { [key:string]: Observable<ArrayBuffer> } = {};
    simulationFileObservable: Observable<ArrayBuffer> = null;

    @ViewChild("simulationComponent") simulationComponent: SimulationComponent;

    @Output() onClose = new EventEmitter<void>();
    @Output() onRefresh = new EventEmitter<void>();

    ngOnInit(): void {}

    ngOnChanges(changes: SimpleChanges) {
        if(changes['editingRequests'] && changes['editingRequests'].currentValue) {
            this.fileObservableMap = {};
            const editingRequests : CreateEditingRequest[] = changes['editingRequests'].currentValue;
            for(let editingRequest of editingRequests) {
                this.fileObservableMap[editingRequest.agent] = this.documentService.getDocumentAsBuffer(editingRequest.id);
            }

            // directly open the first tab
            if(this.editingRequests.length > 0){
                this.simulationFileObservable = this.fileObservableMap[this.editingRequests[0].agent];        
            }
        }
    }
    
    selectTab(selectedTab:TabDirective):void {
        this.simulationFileObservable = this.fileObservableMap[selectedTab.heading];
    }

    download(): void {
        this.simulationComponent.download();
    }

    refresh(): void {
        this.onRefresh.emit();
    }

    close(): void {
        this.onClose.emit();
    }
}