export class ClientLinkedPerson {
    clpId: number;
    cliId:number;
    entity?:number;
    controllingPerson?:number;
    status?:number;
    type?:string;
    subType?: string;
    startDate?:Date;
    endDate?:Date;

    ///////////////////////////
    [other:string] : any;
}