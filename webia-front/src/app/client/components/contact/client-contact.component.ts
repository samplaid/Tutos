import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { ClientContactType, ClientContactDetail, ClientType} from "../../../_models";
import { ClientContactModel } from "./client-contact.model";

@Component({
    selector: 'client-contact',
    templateUrl: './client-contact.tpl.html'
})
export class ClientContactComponent implements OnInit {

    _correspPtCode: string;
    _corresp1PtCode: string;
    homePreviousCountry: string;
    correspPreviousCountry: string;

    tmpHomeCtry: string;
    tmpHomePrevCtry: string;
    tmpCorrespCtry: string;
    tmpCorrespPrevCtry: string;

    cm: ClientContactModel;
    @Input() countries: any;
    @Input() mode: "readOnly"|"waiting"|"edit"|"create";
    
    @Output() clientContactChange: EventEmitter<ClientContactModel> = new EventEmitter<ClientContactModel>();
    @Input()
    set clientContact(input: ClientContactModel) {
        this.cm = input;
        if(this.cm && this.cm.homeAddress){
            this.setPostalCodePrefix(this.cm.homeAddress.country,'CORRESP');
            this.setPreviousCountryName(this.cm.homeAddress.previousCountry, 'CORRESP');
            this.tmpHomeCtry = this.cm.homeAddress.country;
            this.tmpHomePrevCtry = this.cm.homeAddress.previousCountry;
        }

        if(this.cm && this.cm.correspondenceAddress){
            this.setPostalCodePrefix(this.cm.correspondenceAddress.country,'CORRESP1');
            this.setPreviousCountryName(this.cm.correspondenceAddress.previousCountry, 'CORRESP1');
            this.tmpCorrespCtry = this.cm.correspondenceAddress.country;
            this.tmpCorrespPrevCtry = this.cm.correspondenceAddress.previousCountry;
        }
        this.clientContactChange.emit(this.cm);
    }
    get clientContact(){
        return this.cm;
    }

    constructor() { }

    setPostalCodePrefix(ctyId, type){
        if(this.countries) {
            let country = this.countries.find(cty => cty && cty.ctyId == ctyId);
            if(type == ClientContactType[ClientContactType.CORRESP] && !!country)
                this._correspPtCode = country.ptCode;
            if(type == ClientContactType[ClientContactType.CORRESP1] && !!country)
                this._corresp1PtCode = country.ptCode;
        }
    }

    setPreviousCountryName(ctyId, type){
        if(this.countries){    
            let country = this.countries.find(cty => cty && cty.ctyId == ctyId);
            if(type == ClientContactType[ClientContactType.CORRESP]){            
                this.homePreviousCountry = (!!country) ? country.name : '' ;
            } else if(type == ClientContactType[ClientContactType.CORRESP1]){
                this.correspPreviousCountry = (!!country) ? country.name : '' ;
            }  
        }
    }

    setPreviousCountry(type){
        if(type == ClientContactType[ClientContactType.CORRESP] && this.cm && this.cm.homeAddress){
            if(this.tmpHomeCtry != this.cm.homeAddress.country){
                this.cm.homeAddress.previousCountry = this.tmpHomeCtry;
                this.setPreviousCountryName(this.cm.homeAddress.previousCountry, 'CORRESP');
            } else {
                this.cm.homeAddress.previousCountry = this.tmpHomePrevCtry;
                this.setPreviousCountryName(this.cm.homeAddress.previousCountry, 'CORRESP');
            }
        } else if(type == ClientContactType[ClientContactType.CORRESP1] && this.cm && this.cm.correspondenceAddress){
           if(this.tmpCorrespCtry != this.cm.correspondenceAddress.country){
                this.cm.correspondenceAddress.previousCountry = this.tmpCorrespCtry;
                this.setPreviousCountryName(this.cm.correspondenceAddress.previousCountry, 'CORRESP1')
           } else {
                this.cm.correspondenceAddress.previousCountry = this.tmpCorrespPrevCtry;
                this.setPreviousCountryName(this.cm.correspondenceAddress.previousCountry, 'CORRESP1');
           }
        }         
    }
  
    countryChanged(ctyId, type) {
        this.setPostalCodePrefix(ctyId, type);
        this.setPreviousCountry(type);        
    }

    handleChange(event){        
        if(event.target.checked) {            
            this.cm.statementByEmail='Y';
        } else {
            this.cm.statementByEmail='N';
        }
    }

    ngOnInit() { 
        if(!this.cm) {
            this.cm = new ClientContactModel();
        }
        if(!this.cm.homeAddress){
            this.cm.homeAddress = new ClientContactDetail();
            this.cm.homeAddress.contactType=ClientContactType[ClientContactType.CORRESP];
        }
        if(!this.cm.correspondenceAddress){
            this.cm.correspondenceAddress = new ClientContactDetail();
            this.cm.correspondenceAddress.contactType=ClientContactType[ClientContactType.CORRESP1];
        }
        
    }
	
	isPhysicalClientType(clientType: number): boolean {
        return clientType == ClientType.PHYSICAL;
    }

    
}