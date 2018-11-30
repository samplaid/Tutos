import {FullClient} from './fullClient';

export class Beneficiary extends FullClient {
	percentageSplit?: number;
    irrevocable: boolean;
    separatePropertyRights: boolean;
    separatePropertyNoRights: boolean;
    acceptant: boolean; 
	percentagetypeNumberSplit?: number;
    usufructuary: boolean; 
    bareOwner: boolean; 
    exEqualParts?: boolean;
}