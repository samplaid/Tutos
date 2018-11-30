import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
@Component({
    selector: 'fees',
    templateUrl: 'fees.tpl.html'
})
export class FeesComponent implements OnInit {

    @Input()
    mode: string;
    @Input()
    fees: number;
    @Input()
    brokerFees: number;
    @Input()
    feesLabel: string;
    @Input()
    brokerFeesLabel: string;

    @Output()
    feesChange = new EventEmitter<number>();
    @Output()
    brokerFeesChange = new EventEmitter<number>();

    constructor() { }

    ngOnInit(): void { }

    onFeesChange(event: number): void {
        this.fees = event;
        this.feesChange.emit(event);
    }

    onBrokerFeesChange(event: number): void {
        this.brokerFees = event;
        this.brokerFeesChange.emit(event);
    }
}