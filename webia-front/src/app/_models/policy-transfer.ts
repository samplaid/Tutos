export class PolicyTransfer {
    fromPolicy: string;
    fromPolicyEffectDt: Date;

    constructor(fromPolicy: string, fromPolicyEffectDt: Date) {
        this.fromPolicy = fromPolicy;
        this.fromPolicyEffectDt = fromPolicyEffectDt;
    }
} 