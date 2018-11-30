import { Http, Response, Headers, RequestOptionsArgs, ResponseContentType } from '@angular/http';
import { ApiService, MessageService } from '../';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/Observable';
import 'rxjs/Rx';
import * as FileSaver from 'file-saver';
//import 'rxjs/add/operator/catch';


@Injectable()
export class HttpService {

  //protected path: string;
  protected _MSG_ERROR_SERVICE = "Erreur du service";
  protected _MSG_ERROR_SERVER = "Web Server error - check if REST server is started";

  headers_form_urlencoded: Headers;
  protected domain:string='';

      // caching data
      dataStore = {}; 

  constructor(public $http: Http, protected messageService: MessageService) {
    this.headers_form_urlencoded = new Headers();
    this.headers_form_urlencoded.append('Content-Type', 'application/x-www-form-urlencoded');
    this.dataStore = Object.assign({});
  }

  onInit() {
    
  }

  functionWithStore( storeName:string, url:string, urlName:string){    
    if (!this.dataStore[storeName] ) {
        this.dataStore[storeName] = this.GET(url, urlName).publishReplay(1).refCount();
    }
    return this.dataStore[storeName];
  }


  POST(path, postData, name: string, CBOptions?:HandleErrorOptions): Observable<any> {
    const sender = (pathIn, postDataIn, headerIn) => this.$http.post(pathIn, postDataIn, headerIn);
    return this.sendJson(sender, path, postData, name, CBOptions);
  }

  PUT(path, postData, name: string, CBOptions?:HandleErrorOptions): Observable<any> {
    const sender = (pathIn, postDataIn, headerIn) => this.$http.put(pathIn, postDataIn, headerIn);
    return this.sendJson(sender, path, postData, name, CBOptions);
  }

  private sendJson(sendFunction, path, postData, name: string, CBOptions?:HandleErrorOptions): Observable<any> {
    let header = {};
    if (typeof postData == 'string') {
      header = { headers: this.headers_form_urlencoded };
    }

    return sendFunction(path, postData, header)
      .map((res: Response) => this.extractData(path, res, CBOptions?CBOptions:new HandleErrorOptions(false)))
      .catch((error) => this.handleError(path, error, name, CBOptions?CBOptions:new HandleErrorOptions(false)));
  }

  GET(path, name: string, queryString: string = '', CBOptions?:HandleErrorOptions): Observable<any> {    
    return this.$http.get(path + queryString)
      .map((res: Response) => this.extractData(path, res))
      .catch((error) => this.handleError(path, error, name, CBOptions?CBOptions:new HandleErrorOptions(false)));
  }

  GET_BUFFER(path: string, name: string): Observable<ArrayBuffer> {
    return this.$http.get(path, {responseType: ResponseContentType.ArrayBuffer})
      .map((res: Response) => this.extractArrayBuffer(path, res))
      .catch((error) => this.handleBufferError(path, error, name, new HandleErrorOptions(false)));
  }

  DOWNLOAD(path: string, name: string): Observable<any> {
    return this.$http.get(path, {responseType: ResponseContentType.Blob})
      .map((res: Response) => FileSaver.saveAs(res.blob(), this.getFileName(res)))
      .catch((error) => this.handleBufferError(path, error, name, new HandleErrorOptions(false)));
  }

  GET_SILENT(path: string): Observable<any> {
    return this.$http.get(path).map((res: Response) => this.extractData(path, res));
  }

  getWithOptions(path, name: string, options: RequestOptionsArgs, CBOptions?:HandleErrorOptions): Observable<any> {
    return this.$http.get(path, options)
      .map((res: Response) => this.extractData(path, res))
      .catch((error) => this.handleError(path, error, name, CBOptions?CBOptions:new HandleErrorOptions(false)));
  }

  getFileName(res: Response): string {
    const contentDisposition = res.headers.get('Content-Disposition');
    return contentDisposition.split(';')[1].split('filename')[1].split('=')[1].trim();
  }

  extractData = (path: string, res: Response, options?:HandleErrorOptions) => {
    if (res.status !== 200) {
      if (res.status === 204 ) //"No Content"
        return {};
      else 
        this.messageService.error([path, res.statusText]);
    };
    
    if (res) {
      let json = res.json();
      if (json && typeof json == "object") {
        let warns = json.warns;
        if (warns instanceof Array && warns.length > 0) {
          if (options.inAlert) {
            this.messageService.addAlertWarning( warns, true, options.ref );
          }
          else {
            this.messageService.warning(warns, 'Warning');
          }
        }
      }
    }
    
    return res.json(); 
  }

  private extractArrayBuffer(path: string, res: Response): ArrayBuffer {
    if (res.status !== 200) {
      this.messageService.error([path, res.statusText]);
    }
    return res.arrayBuffer();
  }

  private handleBufferError(path: string, error: Response, name: string, options:HandleErrorOptions) {  
    const defaultMessage = this.getErrorMessage(path, name);
    const errTitle = 'File download error';
    this.messageService.error(defaultMessage, errTitle);
    return Observable.throw(error);
  }

  private getErrorMessage(path: string, name: string) {
    return this._MSG_ERROR_SERVICE + " " + path + " (" + this.domain + "." + name + ")";
  }

  protected handleError(path: string, error: Response | any, name: string, options:HandleErrorOptions) {  
    // TODO: we might use a remote logging infrastructure

    //let messageType = error.headers._headers.get("content-type")[0] ;
    let errMsg: any;
    let errTitle: string = undefined;
    let defaultMessage = this.getErrorMessage(path, name);
    
    if (error instanceof Response) {
      if (error.status) {
        
        errMsg = error.text();
        //errTitle = `${error.status} - ${error.statusText || ''}`;
        errTitle = 'Error';
      } else {
        errMsg = this._MSG_ERROR_SERVER;
      }
    } else {
      errMsg = error.message ? error.message : error.toString();
    }

    if (error.status != 400) {
        this.messageService.error( (!!errMsg ? [defaultMessage, errMsg] : defaultMessage), errTitle);
    } else {
      let json;
      try {
        json = JSON.parse(errMsg);
        if (options.inAlert) {
          if (json.errors) {
            this.messageService.addAlertError( json.errors, options.closeOther, options.ref );
          } else {
            this.messageService.addAlertError( json, options.closeOther, options.ref );
          }
          if (json.warns && json.warns.length > 0) {
            this.messageService.addAlertWarning( json.warns, false, options.ref );
          }
        }
        else {
          if(json.errors){
            this.messageService.error(json.errors, errTitle);
          } else if(json.warns){
            this.messageService.warning(json.warns, errTitle);
          } else {
            this.messageService.error(json, errTitle);
          }
        }
      } catch (e) {
        json = errMsg;
        if (options.inAlert) {
          this.messageService.addAlertError( json, options.closeOther, options.ref );
        }
        else {
          this.messageService.error(json, errTitle);
        }
      }
      //  = (messageType!='text/html' && errMsg.indexOf() ) ? errMsg : errMsg ;//'{"error": '+errMsg+'}';

    }

    return Observable.throw(error);   // return Observable.throw(errMsg);
  }

}

export class HandleErrorOptions {
  inAlert:boolean;
  closeOther:boolean ;
  ref:string;
  constructor(inAlert:boolean=false, closeOther?:boolean, ref?:string){
    this.inAlert = inAlert;
    this.closeOther = closeOther? closeOther : false;
    this.ref = ref? ref:'global';
  }
}