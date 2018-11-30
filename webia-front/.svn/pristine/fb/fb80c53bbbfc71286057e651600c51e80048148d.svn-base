import { LoadableComponent } from '../loadable.component';
import { OnInit, Component, Output, Input, EventEmitter } from '@angular/core';
import { Observable } from 'rxjs/Observable';
import * as FileSaver from 'file-saver';

@Component({
    selector: 'simulation',
    templateUrl: 'simulation.tpl.html'
})
export class SimulationComponent extends LoadableComponent implements OnInit {
    readonly fileName: string = 'simulation.pdf';
    @Output() onClose = new EventEmitter<void>();
    @Output() onRefresh = new EventEmitter<void>();

    pdfSrc: ArrayBuffer;

    @Input() set loadObservable(observable: Observable<ArrayBuffer>) {
        if (observable) {
            this.initPdfSrc(observable);
        }
    }
    @Input() closeable = true;
    @Input() refreshable = true;
    @Input() isWrapped = true;
    @Input() downloadable = true;    

    constructor() {
        super();
    }

    private initPdfSrc(observable: Observable<ArrayBuffer>) {
        this.startLoading();
        const loadSub = observable
            .catch((error, obs) => {
                this.endLoading();
                return Observable.of(null);
            })
            .subscribe(result => this.pdfSrc = result);
        this.addSubscription(loadSub);
    }

    ngOnInit() { }
    
    close(): void {
        this.onClose.emit();
    }

    refresh(): void {
        this.onRefresh.emit();
    }

    public download(): void {
        FileSaver.saveAs(new Blob([new Uint8Array(this.pdfSrc)]), this.fileName);
    }

    private startLoading(): void {
        //Simulating an observable that will starts now and end when notified by the ng pdf api.
        const obs = new Observable((observer) => { });
        const sub = obs.subscribe(() => { });
        this.addSubscription(sub);
    }

    endLoading(): void {
        this.clearSubscriptions();
    }
}