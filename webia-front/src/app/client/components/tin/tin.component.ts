import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { TinModel } from "./tin.model";

@Component({
    selector: 'tin',
    templateUrl: './tin.tpl.html'
})
export class TinComponent implements OnInit {

    tin: TinModel;

    @Input() countries: any;
    @Input() mode: "readOnly"|"waiting"|"edit"|"create";
    @Output() tinModelChange: EventEmitter<TinModel> = new EventEmitter<TinModel>();
    
    @Input()
    set tinModel(input: TinModel) {
        this.tin = input;
        this.tinModelChange.emit(this.tin);
    }
    get tinModel(){
        return this.tin;
    }

    constructor() { }

    ngOnInit() { }

    handleChange($event){
        this.tinModelChange.emit(this.tin);
    }
}