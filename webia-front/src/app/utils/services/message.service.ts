import {Component, Input, Output, EventEmitter} from '@angular/core';
import { BsModalService, BsModalRef } from 'ngx-bootstrap/modal';
import {Injectable} from '@angular/core';
import {Observable} from 'rxjs/Observable';
import {BehaviorSubject} from 'rxjs/BehaviorSubject';
import { Subscription } from 'rxjs/Subscription';
import { Subject } from 'rxjs/Subject';

// modal component

export interface modalOptions {
  messages?:string[];
  header?:string;
  controls?:string;
}

export class Alert {
  id: number;
  type: 'success'| 'info'| 'warning'| 'danger';  // 'success', 'info', 'warning', 'danger'
  messages: string[];
  closing:boolean;
  constructor(id:number, type:string, messages:string[]){
    Object.assign(this, {id, type, messages});
  }
}

export class Alerts {
  global: Alert[];
  [others: string]: Alert[];
  constructor(){
    this.global = [];
  }
}

type modalSize = "lg" | "sm";

@Component({
  selector: 'ngbd-modal-content',
  styles: [`
    .modal-body{ color : black;}
  `],
  template: `
    <div [className]="'modal-header modal-'+controls" >
      <button type="button" class="close" aria-label="Close" (click)="modal.hide()">
        <span aria-hidden="true">&times;</span>
      </button>
      <h4 class="modal-title">{{header}}</h4>
    </div>
    <div class="modal-body">
      <div *ngFor="let message of messages;">
        <span [innerHTML]="message"></span>
      </div>
    </div>
    <div class="modal-footer">
        <div *ngIf="controls=='confirme'">
            <button class="btn btn-primary btn-sm" type="button" (click)="confirm(true)">Confirm</button>
            <button class="btn btn-warning btn-sm" type="button" (click)="confirm(false)">Cancel</button>
        </div>
        <div *ngIf="controls!='confirme'">
            <button class="btn btn-primary btn-sm" type="button" (click)="close(true)">OK</button>
        </div>
    </div>
  `
})
export class BsModalContent {
  messages:Array<string> = [];
  header:string= '';
  controls:string= '';

  public onClose: Subject<any>;

  constructor(public modal: BsModalRef) { }

    public ngOnInit(): void {
      this.onClose = new Subject();
  }

  public close(data:any){
    this.onClose.next(data);
    this.modal.hide();
  }

  public confirm(bool:boolean): void {
      this.onClose.next(bool);
      this.modal.hide();
  }

}

@Injectable()
export class MessageService {
        INFO = 'info';
        SUCCESS = 'success';
        ERROR = 'danger';
        WARNING = 'warning';
        CONFIRME = 'confirme';
        DEFAULT_MESSAGE = " ?? Oups pas de message ?? ";
        DEFAULT_CONFIRME = "Confirmez vous l'action ?";
        DEFAULT_HEADER_INFO = "Information";
        DEFAULT_HEADER_ERROR = "Erreur !";
        DEFAULT_HEADER_SUCCESS = "Success";
        DEFAULT_HEADER_WARNING = "Attention !";
        DEFAULT_HEADER_CONFIRME = "Confirmation";

        private alerts: Alerts;
        public beAlerted: BehaviorSubject<Alerts>;
        
        constructor(private modalService: BsModalService) {
          this.alerts = new Alerts();
          this.beAlerted = <BehaviorSubject<Alerts>>new BehaviorSubject(this.alerts);
        }

  transformMessage(message){
                    if (Array.isArray(message)){
                        message = message.map(m=>{
                          if (m.startsWith("<html>")){
                            return m.split("<body>")[1];
                          } else {
                            return "&emsp;&nbsp;-&nbsp;"+m;
                          }

                        })
                        return [...message];
                    } else {
                        var json = message.match(/\{.*\}/);                        
                        if (json){
                            json = json[0];
                            //add previous text if exist
                            var text = message.split(json);
                            json = JSON.parse(json);
                            var result = (text.length>0 && text[0] !="")? [text[0]] : [];
                            //check if data has information
                            if (json.data)
                                json = json.data;
                            //check if json have only one key (error message from jboss)
                            var keys = Object.keys(json);
                            if (keys.length==1)  json = json[keys[0]];
                            //check if json have still only one key (some error message from jboss bad formed ?)
                            keys = Object.keys(json);
                            if (keys.length==1)  
                              json = json[keys[0]];
                            //check if result is a simple String or a Json/array data 
                            this.add2Messages(json, result)  
                            //add next text if exist
                            if (text.length ==2)
                                result.push(text[1]);
                            return [...result];
                        } else {
                          var result = [];
                          this.add2Messages(message, result );
                          return [...result];
                        }
                        //return [message];
                    }
   }

    add2Messages(json, result) {
          if (Array.isArray(json) || typeof json == "object" ) {
            //tranform the json to an array if needed
            if (Array.isArray(json))
              json.forEach((value, key) => this.add2Messages(value, result) );
            else
              Object.keys(json).map((key) => { result.push("<br><b>" + key + " : </b>") ; 
                                               this.add2Messages(json[key], result); 
                                             });
          } else {
            result.push("&emsp;&nbsp;-&nbsp;" + json);
          }
    }

    /**
     * Modal Management
     */

  open(message:string|Array<string>, header:string, size:modalSize, controls:string=this.INFO):BsModalRef {
    let modalSize = size ? 'modal-' + size : 'modal'
    let messages = message ? this.transformMessage(message) : null;
    let initialState = { messages: messages, header: header, controls: controls };
    const modalRef: BsModalRef = this.modalService.show(BsModalContent, { class: modalSize, initialState });
    return modalRef;
  }
  
  openContent(content, size:modalSize):BsModalRef {
    return this.modalService.show(content);
  }
  
  error(message:string|Array<string>=this.DEFAULT_MESSAGE, header=this.DEFAULT_HEADER_ERROR, size:modalSize='lg'):BsModalRef {
    return this.open(message, header,size, this.ERROR);
  }

  success(message:string|Array<string>=this.DEFAULT_MESSAGE, header=this.DEFAULT_HEADER_SUCCESS, size:modalSize='lg'):BsModalRef {
    return this.open(message, header,size, this.SUCCESS);
  }
  
  warning(message:string|Array<string>=this.DEFAULT_MESSAGE, header=this.DEFAULT_HEADER_WARNING, size:modalSize='lg'):BsModalRef {
    return this.open(message, header,size, this.WARNING);
  }  

  info(message:string|Array<string>=this.DEFAULT_MESSAGE, header=this.DEFAULT_HEADER_INFO, size:modalSize='lg'):BsModalRef {
    return this.open(message, header,size, this.INFO);
  }   
  
  confirm(message:string|Array<string>=this.DEFAULT_CONFIRME, header=this.DEFAULT_HEADER_CONFIRME, size:modalSize='lg'): Observable<boolean> {
    let modalRef: BsModalRef = this.open(message, header,size, this.CONFIRME);
    return <Observable<boolean>>modalRef.content.onClose;
  }  
  
  popupErrors(messageHeader, errors) {
        function retrieveMessageBody (errors, level) {
            var messageBody = "";

            errors.forEach(function(e) {

                if(e.description === undefined && level === 'ERROR') {
                    messageBody += "<br>&emsp;&nbsp;-&nbsp;" + e;
                }

                if(e.level === level) {
                    messageBody += "<br>&emsp;&nbsp;-&nbsp;" + e.description;
                }

            });

            return messageBody;
        }    
        
        var messageBody = retrieveMessageBody(errors, "INFO");
        if(messageBody != null && messageBody.length != 0) {
             this.info("Info(s): " + messageBody, messageHeader);        
        }

        messageBody = retrieveMessageBody(errors, "WARNING");
        if(messageBody != null && messageBody.length != 0) {
             this.warning("Warning(s): " + messageBody, messageHeader);        
        }

        messageBody = retrieveMessageBody(errors, "ERROR");
        if(messageBody != null && messageBody.length != 0) {
             this.error("Erreur(s): " + messageBody, messageHeader);        
        }
        
    }

    /**
     * Alerts Management (informs all the subscribers of 'beAlerted' observable)
     */

    addAlertError(message:string|Array<string>, clearOther?:boolean, ref?:string){
        this.addAlert(message, 'danger', (!clearOther?false:true), (ref?ref:'global'));
    }

    addAlertSuccess(message:string|Array<string>, clearOther?:boolean, ref?:string){
        this.addAlert(message,'success', (!clearOther?false:true), (ref?ref:'global'));
    }

    addAlertWarning(message:string|Array<string>, clearOther?:boolean, ref?:string){
        this.addAlert(message,'warning', (!clearOther?false:true), (ref?ref:'global'));
    }

    addAlert(message:string|Array<string>, type:string, clearOther:boolean=false,ref:string='global'){
      if (message && type){
        let messages = message ? this.transformMessage(message) : null;
        if (!this.alerts[ref])
          this.alerts[ref] = [];
        if (clearOther)
          this.alerts[ref] = [new Alert(this.alerts[ref].length,type,messages)];
        else 
          this.alerts[ref].push(new Alert(this.alerts[ref].length,type,messages));
        this.beAlerted.next(this.alerts);
      }
    }

    removeAlert(alert:Alert, ref?:string){
      if (!ref) ref = 'global';
      this.alerts[ref] = this.alerts[ref].filter( a=> a.id!=alert.id);
      this.beAlerted.next(this.alerts);
    }
  
    clearAlert(ref?:string){
      if (!ref) ref = 'global';
      this.alerts[ref] = [];
      this.beAlerted.next(this.alerts);
    }
}

@Component({
  selector: 'ngbd-alert-container',
  template: `
  <div class="ngbd-alert-container">
    <div *ngFor="let alert of alerts" class="animated bounceInDown" [ngClass]="{'bounceOutLeft': alert.closing}"> 
      <alert type="{{alert.type}}">
        <div class="pull-right"><button aria-label="Close" class="close" type="button" (click)="closeAlert(alert)"><span aria-hidden="true">×</span></button></div>
        <div *ngFor="let message of alert.messages;">
          <span [innerHtml]="message"></span>
        </div>
      </alert>
    </div>    
  </div>
  <div class="line-break-thin" *ngIf="alerts && alerts.length > 0"></div>
  `
})
export class NgbdAlertContainer {

  public alerts: Alert[];
  beAlerted: Subscription;
  alertRef:string;

  @Input() set ref(val:string){
    if (!val)
      this.alertRef = 'global';
    else
      this.alertRef = val;
  };
  get ref(){
    return this.alertRef;
  }

  @Output() onClose = new EventEmitter<Alert>();

  constructor(private messageService:MessageService) {
        this.alerts = [];
        if(this.messageService.beAlerted)
          this.beAlerted = this.messageService.beAlerted.subscribe(alerts =>this.alerts = alerts[this.alertRef]);
   }


  closeAlert(alert:Alert){
    alert.closing = true;
    setTimeout( ()=> {this.messageService.removeAlert(alert, this.alertRef); this.onClose.emit(alert);}, 300); // check with .ngbd-alert-container.animated in index.scss
  }

  ngOnDestroy() {
     if (this.beAlerted) this.beAlerted.unsubscribe();
   }

}