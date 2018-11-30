import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Subscription';
import { MessageService } from "../utils/index";
import {AppForm} from '../_models';
import { WorkflowService } from "../_services";
import { StepService } from "../subscription/services/step.service";


@Component({
    selector: 'waiting-dispatch',
    templateUrl: 'waiting-dispatch.tpl.html'
})
export class WaitingDispatchComponent implements OnInit {  
    d: AppForm;
    usersList: any[];

    @Output() dispatchChange = new EventEmitter<any>();

    @Input() mode: "readOnly"|"waiting"|"edit"|"create";
    @Input("dispatch") set dispatch(value: AppForm) {
        this.d = value;
        this.dispatchChange.emit(this.d);
    }

    get dispatch() {
        return this.d;
    }

    constructor( private route: ActivatedRoute, private router: Router, private stepService:StepService, 
                private messageService: MessageService, private userService:WorkflowService ) { 
        this.userService.getWorkflowUsers().subscribe(res => { this.usersList = res.users; },e => {});
    }

    ngOnInit() {    }
  
}