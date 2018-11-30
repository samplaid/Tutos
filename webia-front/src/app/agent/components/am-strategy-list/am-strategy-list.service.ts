import { Injectable } from '@angular/core';
import { UoptDetailService } from '../../../_services/uopt-detail.service';
import { AgentService } from '../../agent.service';
import { Observable } from 'rxjs/Observable';
import { AssetManagerStrategy, AssetManagerStrategyStatus, CodeLabelValue } from '../../../_models/index';
import { AmStrategyListOption } from './am-strategy-list-option';
import { UoptDetails } from '../../../_models/uopt-details';
import { mapTo } from 'rxjs/operator/mapTo';

@Injectable()
export class AmStrategyListService {

    constructor(private agentService: AgentService,
        private uoptDetailService: UoptDetailService) { }

    getAssetManager(assetManagerId: string): Observable<Array<any>> {
        return this.agentService.getAssetManagerStrategies(assetManagerId).publishReplay(1).refCount();
    }

    loadStrategies(assetManagerId: string, currentRiskProfile: string, option: AmStrategyListOption): Observable<Array<UoptDetails>> { 
        return this.getAssetManager(assetManagerId)
            .map((strategies: AssetManagerStrategy[]) =>
                (strategies || []).filter(item =>
                    option && item
                    && option.riskType === item.riskType
                    && (item.status === AssetManagerStrategyStatus.ENABLED || item.riskProfile === currentRiskProfile)))
            .map(strategies => {
                let store: UoptDetails[] = [];
                strategies.forEach(strategy => store.push({ uddId: strategy.riskProfile, keyValue: strategy.riskProfile, status: +strategy.status, description: strategy.riskProfileDescription }))
                return store;
            })

    }

    loadDefaultStrategies(currentRiskProfile: string, option: AmStrategyListOption): Observable<Array<UoptDetails>> {
        let observable$ = Observable.empty();

        if (option && option.riskType === CodeLabelValue.PROFIL) {
            observable$ = this.uoptDetailService.getRiskProfiles().publishReplay(1).refCount();
        } else if (option && option.riskType === CodeLabelValue.ALTER_FUND) {
            observable$ = this.uoptDetailService.getAlternativeFunds().publishReplay(1).refCount();
        } else if (option && option.riskType === CodeLabelValue.INVEST_CAT) {
            observable$ = this.uoptDetailService.getInvestmentCategories().publishReplay(1).refCount();
        } else if (option && option.riskType === CodeLabelValue.CURRENCY) {
            observable$ = this.uoptDetailService.getRiskCurrencies().publishReplay(1).refCount();
        }

        return observable$.map((items: any[]) => (items || []).filter((item: any) => item
            && (item.status === +AssetManagerStrategyStatus.ENABLED || item.keyValue === currentRiskProfile)))
            .map(items => {
                let store: UoptDetails[] = [];
                items.forEach(item => store.push(<UoptDetails> item));
                return store;
            });
    }

    isStrategyEnabled(keyValue: string, item: UoptDetails): boolean {
        if(!keyValue || !item){
            return true;
        } else if(item.keyValue !== keyValue) {
            return true;
        }
        return item.keyValue === keyValue && item.status === 1 ;
    }

}