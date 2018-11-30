import { OnInit, OnDestroy } from '@angular/core/src/metadata/lifecycle_hooks';
import { UoptDetailService } from '../../../_services/index';
import { Subscription } from 'rxjs';
import { StateMode } from '../../../utils/index';
import { Input, Output, EventEmitter, Component } from '@angular/core';
import { Fund } from '../../../_models/fund';
import { Observable } from 'rxjs/Observable';

@Component({
    selector: 'circular',
    templateUrl: './circular.component.html'
})
export class CircularComponent implements OnInit, OnDestroy {
    
    circularEnabled: boolean = true;    
    circularValue: string;
    circularLetterList: any[] = [];
    subs: Array<Subscription> = []=[];
    
    @Output() valueChange: EventEmitter<string> = new EventEmitter<string>();
    
    @Input() required: boolean = false;
    @Input() mode: string = StateMode.readonly;
    @Input() set value(circularValue: string){
        this.circularValue = circularValue;
        this.valueChange.emit(circularValue);
        (<Observable<any[]>>this.uoptDetailService.getCircularLetters()).subscribe(circulars => {
            this.circularLetterList = this.filterArray(circularValue, circulars);
            this.checkCircularStatus(circularValue);
        })
    }
    get value(){
        return this.circularValue;
    }

    constructor(private uoptDetailService: UoptDetailService){        
    }

    filterArray(circularValue: string, array: any[]): any[] {
        return array && array.filter(circular => circular && (circular.status === 1 || circular.keyValue === circularValue));
    }


    circularValueChange(circularValue: string) {
        this.valueChange.emit(circularValue);        
        this.checkCircularStatus(circularValue);
    }

    checkCircularStatus(circularValue: string) {
        const circularLetter = this.circularLetterList.find(circularLetter => circularLetter.keyValue === circularValue);
        this.circularEnabled = (!circularLetter) ? true : circularLetter && circularLetter.status === 1; // unknown or inactive circular value --> it is an obsolete circular. Thus, circularEnabled = false             
    }

    ngOnInit(): void { 
    }
    
    ngOnDestroy(): void {
        this.subs.forEach((sub: Subscription) => sub && !sub.closed && sub.unsubscribe());
    }
    
}