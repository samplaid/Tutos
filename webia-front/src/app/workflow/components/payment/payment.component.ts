import { StateMode } from './../../../utils/mode';
import { Observable } from 'rxjs/Observable';
import { CountryService } from './../../../_services/country.service';
import { Input, Component, OnInit, Output, EventEmitter } from '@angular/core';
import { PolicyTransfer } from '../../../_models/policy-transfer';
import { Country } from '../../../_models';

@Component({
    selector: 'payment',
    templateUrl: 'payment.tpl.html'
})
export class PaymentComponent implements OnInit {

    @Input() premiumCountryCd: string;
    @Input() paymentTransfer: boolean;
    @Input() policyTransferForms: PolicyTransfer[];
    @Input() policyTransfer: boolean;
    @Input() paymentAmt: number;
    @Input() paymentDt: Date;
    @Input() expectedPremium: number;
    @Input() contractCurrency: string;
    @Input() mode: string;
    @Input() transfertMode: string;
    countriesObservable: Observable<Country[]>;
    readonly stateMode = StateMode;

    @Output() premiumCountryCdChange = new EventEmitter<string>();
    @Output() paymentTransferChange = new EventEmitter<boolean>();
    @Output() policyTransferChange = new EventEmitter<boolean>();
    @Output() policyTransferFormsChange = new EventEmitter<PolicyTransfer[]>();
    @Output() paymentAmtChange = new EventEmitter<number>();
    @Output() paymentDtChange = new EventEmitter<Date>();



    constructor(private countryService: CountryService) {}

    ngOnInit(): void {
        this.countriesObservable = this.countryService.loadAllCountries();
    }

    onPolicyTransferChange(event: boolean): void {
        if(!event) {
            this.onPolicyTransferFormsChange([]);
        }
        this.policyTransfer = event;
        this.policyTransferChange.emit(event);
    }

    onPremiumCountryCdChange(event: string): void {
        this.premiumCountryCd = event;
        this.premiumCountryCdChange.emit(event);
    }

    onPaymentTransferChange(event: boolean): void {
        if(!event) {
            this.onPolicyTransferFormsChange([]);
        }
        this.paymentTransfer = event;
        this.paymentTransferChange.emit(event);
    }

    onPolicyTransferFormsChange(event: PolicyTransfer[]): void {
        this.policyTransferForms = event;
        this.policyTransferFormsChange.emit(event);
    }

    onNumberChange(event: number) {
        this.paymentAmt = event;
        this.paymentAmtChange.emit(event);
    }

    onPaymentDtChange(event: Date) {
        this.paymentDt = event;
        this.paymentDtChange.emit(event);
    }
}