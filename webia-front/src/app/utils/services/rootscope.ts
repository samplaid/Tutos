import {Observable} from 'rxjs/Observable';
import {Observer} from 'rxjs/Observer';
import {Injectable} from '@angular/core';

@Injectable()
export class Rootscope {
  public data: any;
  dataChange: Observable<any>;
  private _observer: Observer<any>;
  
  constructor() {
    this.data = {};
    this.dataChange = new Observable( observer => this._observer = observer );
  }

  setData(data:any) {
    this.data = data;
 //   this._observer.next(this.data);
  }

  getData(property:string) {
    if (this.data.hasOwnProperty(property)){
      return this.data[property];
    }
    return null;
  }
  
  assign(property:string, value:any) {
    this.data[property] = value;
//    this._observer.next(this.data);
  }
  
}