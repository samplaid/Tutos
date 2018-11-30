import { Observable } from 'rxjs/Observable';
import { OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs/Subscription';

export abstract class LoadableComponent implements OnDestroy {

    subs: Subscription[] = [];
    
    ngOnDestroy(): void {
        this.clearSubscriptions();
     }

     protected addSubscription(...subscription: Subscription[]) {
        this.subs = [...this.subs, ...subscription];
     }

     protected clearSubscriptions() {
        if(this.subs){
            this.subs.forEach(sub => sub && !sub.closed && sub.unsubscribe());
        }
     }
}