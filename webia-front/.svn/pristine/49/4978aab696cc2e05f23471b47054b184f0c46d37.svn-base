import { Injectable }                               from '@angular/core';
import { Http, Response }                           from '@angular/http';
import { ApiService, MessageService, HttpService }  from '../utils';
import {Observable}                                 from 'rxjs/Observable';

@Injectable()
export class ProductService extends HttpService {
    // caching data
    dataStore;

    constructor($http: Http, messageService: MessageService, private api: ApiService) { 
        super( $http,  messageService);
        this.domain = 'ProductService';
         this.initStore();
    }

    initStore(){
        this.dataStore = Object.assign({});
    }

    /**
     * Lazy loads the list of the product.
     *  @return : the collection of products
     */
    loadAllProductLight(){
        if (!this.dataStore.products) {
        	return this.GET(this.api.getURL('getProductLight'), 'loadAllProductLight').do((data:any) => this.dataStore.products = data)
        	.map((data) => this.dataStore.products);
        } else {
           return Observable.of(this.dataStore.products);
        }
    }

    getProduct(productId:string){
        return this.GET(this.api.getURL('getProduct', productId), 'getProduct');
    }
}