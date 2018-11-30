import { Injectable } from '@angular/core';

@Injectable()
export class AmsStrategyRuleService {

    constructor() { }

    setRiskProfileToUpper(riskProfile: string): string {
        return (riskProfile) ? riskProfile.toUpperCase() : riskProfile;
    }
}