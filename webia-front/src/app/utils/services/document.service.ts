import { Injectable } from '@angular/core';
import { Http } from '@angular/http';
import { HttpService, MessageService, ApiService } from '..';
import { Observable } from 'rxjs/Observable';


@Injectable()
export class DocumentService extends HttpService {
    
    constructor($http: Http, messageService: MessageService, private api: ApiService) {
        super($http, messageService);
        this.domain = 'DocumentService';  
    }

    getDocument(editingId: number): Observable<any> {
        return this.DOWNLOAD(this.api.getURL('existingDocument', editingId), 'existingDocument');
    }

    getDocumentAsBuffer(editingId: number): Observable<any> {
        return this.GET_BUFFER(this.api.getURL('existingDocument', editingId), 'existingDocument');
    }
}