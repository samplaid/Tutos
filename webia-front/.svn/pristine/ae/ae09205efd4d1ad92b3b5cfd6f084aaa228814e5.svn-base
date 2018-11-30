import { UoptDetails } from '../../../_models';
import { Observable } from 'rxjs/Observable';

export interface AmStrategyListOption {   
    riskType?: string;
    config?: {
        treatOtherList$?: (value$: Observable<UoptDetails[]>) => Observable<UoptDetails[]>;
        treatBlueList$?: (value$: Observable<UoptDetails[]>) => Observable<UoptDetails[]>;
        treatMergedList$?: (value$: Observable<UoptDetails>) => Observable<UoptDetails[]>;
    }
}