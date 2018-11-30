import { StateMode } from './../../../utils/mode';
import { Input, Component, OnInit, SimpleChanges, Output, EventEmitter } from '@angular/core';
import { PolicyTransfer } from "../../../_models/policy-transfer";

@Component({
    selector: 'policy-transfer-input',
    templateUrl: 'policy-transfer-input.tpl.html'
})
export class PolicyTransferInputComponent implements OnInit {

    constructor() {}

    @Input() policyTransfers: PolicyTransfer[] = [];
    @Input() mode: string;
    readonly stateMode = StateMode;

    @Output() policyTransfersChange = new EventEmitter<PolicyTransfer[]>();

    ngOnInit(): void { }

    pushNewRow():void {
        this.policyTransfers = [...this.policyTransfers, new PolicyTransfer("", null)];
    }

    remove(transferToRemove: PolicyTransfer) {
        this.policyTransfers = this.policyTransfers.filter(transfer => transfer != transferToRemove);
        if(this.policyTransfers.length == 0) {
            this.addNewTransfer();
        }
        this.policyTransfersChange.emit(this.policyTransfers);
    }

    ngOnChanges(changes: SimpleChanges): void {
        if(changes['policyTransfers'] && this.policyTransfers.length == 0) {
            this.addNewTransfer();
        }
    }

    addNewTransfer(): void {
        this.pushNewRow();
        this.policyTransfersChange.emit(this.policyTransfers);
    }
}