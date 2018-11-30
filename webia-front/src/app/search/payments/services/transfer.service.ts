import { Http }                           from '@angular/http';
import { Injectable}                      from '@angular/core';
import { Observable}                      from 'rxjs/Observable';
import { HttpService, MessageService, ApiService } from '../../../utils';
import { TransferReject } from '../models/transfer-reject';
import { TransferCompta } from '../models/transfer-compta';


@Injectable()
export class TransferService extends HttpService {    
    constructor($http: Http, messageService: MessageService, private api: ApiService) {        
        super($http,  messageService);
        this.domain = 'TransferService';  
    }

    getPayments(): Observable<TransferCompta[]>{
        return this.GET(this.api.getURL('getPayments'), 'getPayments');
    }

    validate(id: number): Observable<TransferCompta[]> {
        return this.POST(this.api.getURL('validatePayment', id), {}, 'validatePayment');
    }

    refuse(id: number, transferReject: TransferReject): Observable<TransferCompta> {
        return this.POST(this.api.getURL('refusePayment', id), transferReject, 'refusePayment');
    }

    executeSepa(ids: number[]) : Observable<TransferCompta[]> {
        return this.POST(this.api.getURL('executeSepa'), {"ids" : ids}, 'executeSepa');
    }

    executeFax(ids: number[]) : Observable<TransferCompta[]> {
        return this.POST(this.api.getURL('executeFax'), {"ids" : ids}, 'executeFax');
    }

    executeCsv(ids: number[]) : Observable<TransferCompta[]> {
        return this.POST(this.api.getURL('executeCsv'), {"ids" : ids}, 'executeFax');
    }
}
